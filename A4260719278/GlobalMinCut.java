import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/****************************
 *
 * COMP251 template file
 *
 * Assignment 4
 *
 *****************************/

public class GlobalMinCut {

    static Random random;

    /**
     * One run of the contraction algorithm
     * Returns the min cut found
     *
     * @param   graph - the graph to find min cut of
     * @return  two ArrayLists of characters (placed together in another ArrayList)
     *          representing the partitions of the cut
     */
    public static ArrayList<ArrayList<Character>> global_min_cut(Graph graph) {
        ArrayList<ArrayList<Character>> cut = new ArrayList<ArrayList<Character>>();

        // For each node v, we will record
        // the list S(v) of nodes that have been contracted into v
        Map<Character, ArrayList<Character>> S = new HashMap<Character, ArrayList<Character>>();


        // TODO: Initialize S(v) = {v} for each v
        int i = 0;
        for(Character node:graph.getNodes()){
            ArrayList<Character> nodeArr = new ArrayList<Character>();
            nodeArr.add(node);
            S.put(node, nodeArr);
            i++;
        }

        while (graph.getNbNodes() > 2) {

            // select an edge randomly (DO NOT CHANGE THIS LINE)
            Edge edge = graph.getEdge(random.nextInt(graph.getNbEdges()));
            graph.contractEdge(edge);
            // TODO: fill in the rest
            for(Character node:S.get(edge.node_1)){
                S.get(edge.node_2).add(node);
            }
            S.remove(edge.node_1);
        }
        
        // TODO: assemble the output object
        for(Character node:graph.getNodes()){
            cut.add(S.remove(node));
        }
        return cut;
    }

    /**
     * repeatedly calls global_min_cut until finds min cut or exceeds the max allowed number of iterations
     *
     * @param graph         the graph to find min cut of
     * @param r             a Random object, don't worry about this, we only use it for grading so we can use seeds
     * @param maxIterations some large number of iterations, you expect the algorithm to have found the min cut
     *                      before then (with high probability), used as a sanity check / to avoid infinite loop
     * @return              two lists of nodes representing the cut
     */
    public static ArrayList<ArrayList<Character>> global_min_cut_repeated(Graph graph, Random r, int maxIterations) {
        random = (r != null) ? r : new Random();

        ArrayList<ArrayList<Character>> cut = new ArrayList<ArrayList<Character>>();
        int count = 0;
        do{
            
			// TODO: use global_min_cut()
            Graph newGraph = new Graph(graph);
            cut = global_min_cut(newGraph);
            ++ count;

            //if (get_cut_size(graph, cut) > min_cut_size)
              //  System.out.println("Cut has " + get_cut_size(graph, cut) + " edges but the min cut should have " +
                //graph.getExpectedMinCutSize() + ", restarting");

        } while (get_cut_size(graph, cut) > graph.getExpectedMinCutSize() && count < maxIterations);

        if (count >= maxIterations) System.out.println("Forced stop after " + maxIterations + " iterations... did something go wrong?");
        return cut;
        
    }

    /**
    * @param graph  the original unchanged graph
    * @param cut    the partition of graph into two lists of nodes
    * @return       the number of edges between the partitions
    */
    public static int get_cut_size(Graph graph, ArrayList<ArrayList<Character>> cut) {
        ArrayList<Edge> edges = graph.getEdges();
        int cut_size = 0;
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge = edges.get(i);
            if (cut.get(0).contains(edge.node_1) && cut.get(1).contains(edge.node_2) ||
                    cut.get(0).contains(edge.node_2) && cut.get(1).contains(edge.node_1)) {
                ++cut_size;
            }
        }
        return cut_size;
    }
}
