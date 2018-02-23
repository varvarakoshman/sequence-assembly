import java.util.*;

public class GraphStructure {
    private int nVertex;

    private static HashMap<String, ArrayList<String>> adj;
    static LinkedHashSet<String> vertices;

    public GraphStructure(List<EdgeStructure> edges) {
        ArrayList<EdgeStructure> edges_unique = new ArrayList<>(new LinkedHashSet<>(edges));
        vertices = new LinkedHashSet<>(); //список вершин без повторений

        for (EdgeStructure oneEdge : edges_unique) {
            vertices.add(oneEdge.from());//++++++++++++++++++++++++++++++++++++++++++++
            vertices.add(oneEdge.to());
        }
        nVertex = vertices.size();
        adj = new HashMap<>(nVertex);

        for (EdgeStructure oneEdge : edges) {
            ArrayList<String> temp = adj.get(oneEdge.from());
            if (temp == null) {
                temp = new ArrayList<>();
            }
            temp.add(temp.size(), oneEdge.to());
            adj.put(oneEdge.from(), temp);
        }
    }

    public static HashMap<String, ArrayList<String>> getAdj() {
        return adj;
    }
}