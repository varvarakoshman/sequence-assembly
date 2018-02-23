import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class GraphStructure {
    private int nVertex;

    private ArrayList<ArrayList<String>> adj;
    static LinkedHashSet<String> vertices;

    public GraphStructure(List<EdgeStructure> edges) {
        ArrayList<EdgeStructure> edges_unique = new ArrayList<>(new LinkedHashSet<>(edges));
        vertices = new LinkedHashSet<>(); //список вершин без повторений
        for (EdgeStructure oneEdge : edges_unique) {
            vertices.add(oneEdge.from());
            vertices.add(oneEdge.to());
        }
        nVertex = vertices.size();
        adj = new ArrayList<>(nVertex);
        for (int i = 0; i < nVertex; i++) {
            adj.add(new ArrayList<>());
        }
        Iterator<String> iterator = vertices.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            adj.get(index).add(iterator.next()); //добавляем каждую вершину в собственный список
            ++index;
        }
        index = 0;
        //находим в adj вершину, из которой выходит текущее ребро,
        //и добавляем вершину, куда оно ведет, к списку, соотв. первой вершине
        for (EdgeStructure oneEdge : edges) {
            for (ArrayList<String> list : adj) {
                if (list.get(0).equals(oneEdge.from())) {
                    index = adj.indexOf(list);
                    break;
                }
            }
            adj.get(index).add(oneEdge.to());
        }
    }

    public ArrayList<ArrayList<String>> getAdj() {
        return adj;
    }
}