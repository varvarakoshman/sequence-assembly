import java.util.*;

public class GraphStructure {
    private int nVertex;
    private static String startKey;
    private static HashMap<String, ArrayList<String>> adj;
    private static HashMap<String, Integer> degrees;
    private static LinkedHashSet<String> vertices;

    public GraphStructure(List<EdgeStructure> edges) {
        ArrayList<EdgeStructure> edges_unique = new ArrayList<>(new LinkedHashSet<>(edges));
        vertices = new LinkedHashSet<>(); //список вершин без повторений

        for (EdgeStructure oneEdge : edges_unique) {
            vertices.add(oneEdge.from());
            vertices.add(oneEdge.to());
        }
        nVertex = vertices.size();
        adj = new HashMap<>(nVertex);
        degrees = new HashMap<>(nVertex);
        Iterator<String> iterator = vertices.iterator();
        while (iterator.hasNext()) {
            degrees.put(iterator.next(),0);
        }

        for (EdgeStructure oneEdge : edges) {
            ArrayList<String> tempFrom = adj.get(oneEdge.from());
            if (tempFrom == null) {
                tempFrom = new ArrayList<>();
            }
            tempFrom.add(tempFrom.size(), oneEdge.to());
            adj.put(oneEdge.from(), tempFrom);
            degrees.put(oneEdge.from(), degrees.get(oneEdge.from()) - 1);
            degrees.put(oneEdge.to(), degrees.get(oneEdge.to()) + 1);
        }
    }

    public static LinkedHashSet<String> getVertices() {
        return vertices;
    }

    public static HashMap<String, ArrayList<String>> getAdj() {
        return adj;
    }

    public static HashMap<String, Integer> getDegrees() {
        return degrees;
    }

    public static String getStartKey() {
        return startKey;
    }

    public static void setStartKey(String key) {
        startKey = key;
    }

}