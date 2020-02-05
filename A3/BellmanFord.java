import java.nio.file.Path;

public class BellmanFord{

	
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
        this.source = g.getSource();
        int V = g.getNbNodes();
        int E = g.getEdges().size();
        distances = new int[g.getNbNodes()];
        predecessors = new int[g.getNbNodes()];
        for(int i  = 0; i<V; i++){
            distances[i] = Integer.MAX_VALUE;
        }
        distances[source] = 0;
        for(int i = 1; i<V; ++i){
            for(int k = 0; k<E; ++k){
                int u  = g.getEdges().get(k).nodes[0];
                int v = g.getEdges().get(k).nodes[1];
                int weight = g.getEdges().get(k).weight;
                if(distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]){
                    distances[v] = distances[u] + weight;
                    predecessors[v] = g.getEdges().get(k).nodes[0];
                }
            }
        }
        for(int j = 0; j<E; j++){
            int u = g.getEdges().get(j).nodes[0];
            int v = g.getEdges().get(j).nodes[1];
            int weight = g.getEdges().get(j).weight;
            if(distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]){
                throw new NegativeWeightException("Negative weight cycle!");
            }
        }
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
        int[] revPath = new int[distances.length];
        boolean[] check = new boolean[distances.length];
        int k = 0;
        for(int i = destination; i!= source; i = predecessors[i]){
            if(distances[i] == Integer.MAX_VALUE){
                throw new PathDoesNotExistException();
            }
            revPath[k] = i;
            check[k] = true;
            k++;
        }
        check[k] = true;
        revPath[k] = source;
        //reverse the path
        int[] path = new int[distances.length];
        int j = 0;
        for(int i = revPath.length-1; i >= 0; i--){
            if(check[i]){
                path[j] = revPath[i];
                j++;
            }
        } 
        int[] newPath = new int[j];
        for(int i = 0; i<newPath.length; i++){
            newPath[i] = path[i];
        }

        return newPath;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
