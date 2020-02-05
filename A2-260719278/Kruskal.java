// Name: Andrew Martins 
// Student #: 260719278
// Worked with: Elisa Branson, Boyana Djurovic
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        DisjointSets p = new DisjointSets(g.getNbNodes());
        WGraph mst = new WGraph();
        ArrayList<Edge> e = g.listOfEdgesSorted();
        for(int i = 0; i<e.size(); i++){
          if(IsSafe(p,e.get(i))){
            p.union(e.get(i).nodes[0],e.get(i).nodes[1]);
            mst.addEdge(e.get(i));
          }
        }
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
        int vertex_1 = e.nodes[0];
        int vertex_2 = e.nodes[1];
        if(p.find(vertex_1) == p.find(vertex_2)){
          return false;
        }
        else{
          return true;
        }
    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
