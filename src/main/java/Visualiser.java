import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Iterator;
import java.util.List;

public class Visualiser {

    public static void drawGraph(List<EdgeStructure> builtEdges) {
        Graph graph = new MultiGraph("Visualiser");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.addAttribute("ui.stylesheet", "url('file:style.css')");
        int ids = 0; //уникальное id для каждого ребра
        Iterator<String> iterator = GraphStructure.getVertices().iterator();
        while (iterator.hasNext()) {
            graph.addNode(iterator.next());
        }
        for (EdgeStructure oneEdge : builtEdges) {
            graph.addEdge(Integer.toString(ids), oneEdge.from(), oneEdge.to(), true);
            ++ids;
        }
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        graph.display();
    }
}