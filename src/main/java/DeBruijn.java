
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DeBruijn {
    static String pref;
    static String postf;

    public static StringBuffer getEulerPath(List<EdgeStructure> list_edges, int num_edges) {
        StringBuffer result = new StringBuffer("");
        String start_vertex = list_edges.get(0).from();
        Stack<String> stack = new Stack<>();
        stack.push(start_vertex);
        String top_vertex;
        boolean flag = true; //исходящих ребер нет
        while (!stack.empty()) {
            top_vertex = stack.peek();
            if (flag) {
                outerloop:
                for (EdgeStructure oneEdge : list_edges) {
                    for (String vertex : GraphStructure.vertices) {
                        if (oneEdge.from().equals(top_vertex) && oneEdge.to().equals(vertex)) {
                            stack.push(vertex);
                            list_edges.remove(oneEdge);
                            break outerloop;
                        }
                    }
                }
            }
            if (!flag) {
                stack.pop();
                if (stack.empty()) {
                    result = result.append(top_vertex);
                } else {
                    result = result.append(top_vertex.charAt(top_vertex.length() - 1));

                }
            } else if (stack.size() == num_edges + 1) {
                flag = false;
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
        Visualiser.drawGraph(edges);
        System.out.println("genome: " + getEulerPath(edges, edges.size()));
    }
}
