
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DeBruijn {
    static String pref;
    static String postf;


    public static boolean checkIfEulerian(GraphStructure graph) {
        int count = 0;
        HashMap<String, Integer> degr = graph.getDegrees();
        int value;
        for (String key : degr.keySet()) {
            value = degr.get(key);
            if (value < -1 || value > 1) {
                return false;
            }
            if (value != 0) {
                count++;
                if (value == -1) {
                    graph.setStartKey(key);
                }
            }
        }
        if (count > 2) {
            return false;
        }
        return true;
    }


    public static StringBuffer getEulerPath(GraphStructure graph) {
        StringBuffer result = new StringBuffer("");
        Stack<String> stack = new Stack<>();
        stack.push(graph.getStartKey());
        String top_vertex;
        HashMap<String, ArrayList<String>> adj = graph.getAdj();
        while (!stack.empty()) {
            top_vertex = stack.peek();
            if (adj.get(top_vertex) == null || adj.get(top_vertex).size() == 0) {
                stack.pop();
                if (stack.empty()) {
                    result = result.append(new StringBuffer(top_vertex).reverse());
                } else {
                    result = result.append(top_vertex.charAt(top_vertex.length() - 1));

                }
            } else {
                ArrayList<String> temp = adj.get(top_vertex);
                stack.push(temp.get(0));
                temp.remove(0);
                adj.put(top_vertex, temp);
            }
        }

        return result.reverse();
    }

    /**
     * в каждом к-мере берутся его префикс и постфикс(начальная и конечная вершина для ребра)
     *
     * @param kmers список данных к-меров
     * @return список ребер
     */
    public static List<EdgeStructure> buildEdges(List<String> kmers) {
        ArrayList<EdgeStructure> edges = new ArrayList<>();
        for (int i = 0; i < kmers.size(); ++i) {
            String kmer = kmers.get(i);
            pref = "";
            postf = "";
            for (int j = 0; j < kmer.length() - 1; ++j) {
                pref = pref + kmer.charAt(j);
            }
            for (int j = 1; j < kmer.length(); ++j) {
                postf = postf + kmer.charAt(j);
            }
            edges.add(new EdgeStructure(pref, postf));
        }
        return edges;
    }

    public static void main(String[] args) {
        List<String> kmers = new ArrayList<>();
        try {
            kmers = Files.readAllLines(Paths.get("k-mers.txt"), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<EdgeStructure> edges = buildEdges(kmers);
        GraphStructure debr = new GraphStructure(edges);
        debr.setStartKey(edges.get(0).from());
        if (checkIfEulerian(debr)) {
            StringBuffer genome = getEulerPath(debr);
            for (int i = 0; i < genome.length(); i++) {
                if (genome.charAt(i) == '_') {
                    System.out.print(" ");
                } else if (genome.charAt(i) == '#') {
                    System.out.println();
                } else {
                    System.out.print(genome.charAt(i));
                }
            }
            Visualiser.drawGraph(edges);
        } else {
            System.out.println("GRAPH IS NOT (SEMI-)EULERIAN!!!");
            Visualiser.drawGraph(edges);
        }
    }
}
