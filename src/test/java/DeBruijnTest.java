import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeBruijnTest {

    private List<String> actual_kmers = Arrays.asList("GGC", "GCG", "CGA", "GAT", "ATT", "TTC", "TCA", "CAT", "ATC", "TCG");
    private GraphStructure actual_graph = new GraphStructure(DeBruijn.buildEdges(actual_kmers));

    @DataProvider
    public Object[][] calcEulerPath() {
        List<EdgeStructure> actual_edges = DeBruijn.buildEdges(actual_kmers);
        return new Object[][]{
                {"GGCGATTCATCG", DeBruijn.getEulerPath(actual_edges, actual_edges.size()).toString()}
        };
    }

    @Test(dataProvider = "calcEulerPath")
    public void calc(String result, String actual) {
        assertEquals(result, actual);
    }

    @Test
    public void testBuildEdges() throws Exception {
        ArrayList<ArrayList<String>> expected_adj;
        int expected_nVertex = 8;
        expected_adj = new ArrayList<>(8);
        for (int i = 0; i < expected_nVertex; ++i) {
            expected_adj.add(new ArrayList<>());
        }
        expected_adj.get(0).add("GG");
        expected_adj.get(0).add("GC");
        expected_adj.get(1).add("GC");
        expected_adj.get(1).add("CG");
        expected_adj.get(2).add("CG");
        expected_adj.get(2).add("GA");
        expected_adj.get(3).add("GA");
        expected_adj.get(3).add("AT");
        expected_adj.get(4).add("AT");
        expected_adj.get(4).add("TT");
        expected_adj.get(4).add("TC");
        expected_adj.get(5).add("TT");
        expected_adj.get(5).add("TC");
        expected_adj.get(6).add("TC");
        expected_adj.get(6).add("CA");
        expected_adj.get(6).add("CG");
        expected_adj.get(7).add("CA");
        expected_adj.get(7).add("AT");
        assertEquals(expected_adj, actual_graph.getAdj());
    }

}