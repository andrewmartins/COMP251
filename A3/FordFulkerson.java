import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		Stack<Integer> s = new Stack<>();
		Stack<Integer> temp = new Stack<>();
		ArrayList<LinkedList<Integer>> adjList = makeAdjList(graph);
		boolean[] visited = new boolean[graph.getNbNodes()];
		visited[source] = true;
		int i = 0;
		//finds source node in adjList
		while(i<adjList.size()){
			if(i == source){
				break;
			}
			i++;
		}
		s.push(source);
		while(i != destination){
			ListIterator<Integer> listIt = adjList.get(i).listIterator();
			while(listIt.hasNext()){
				int nextNode;
				if(adjList.get(i).contains(destination)){
					if(graph.getEdge(i,destination).weight != 0){
						nextNode = destination;
					}
					else{
						nextNode = listIt.next();
					}
				}
				else{
					nextNode = listIt.next();
				}
				if(!visited[nextNode] && graph.getEdge(i,nextNode).weight != 0){
					s.push(nextNode);
					i = nextNode;
					visited[nextNode] = true;
					if(nextNode == destination){
						break;
					}
					listIt = adjList.get(nextNode).listIterator();
				}
				else if(!listIt.hasNext()){
					visited[s.peek()] = true;
					s.pop();
					if(s.size() == 0){
						ArrayList<Integer> empty = new ArrayList<Integer>();
						return empty;
					}
					i = s.peek();
					listIt = adjList.get(s.peek()).listIterator();
				}
			}
			while(s.size() != 0){
				temp.push(s.pop());
			}
			while(temp.size() != 0){
				Stack.add(temp.pop());
			}
		}
		if(!Stack.contains(destination)){
			ArrayList<Integer> empty = new ArrayList<Integer>();
			return empty;
		}
		return Stack;
	}
	public static ArrayList<LinkedList<Integer>> makeAdjList(WGraph graph){
		ArrayList<Edge> edges = graph.getEdges();
		ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
		for(int i = 0; i<graph.getNbNodes(); i++){
			LinkedList<Integer> adjVerts = new LinkedList<Integer>();
			for(int j = 0; j < edges.size(); j++){
				if(edges.get(j).nodes[0] == i){
					adjVerts.add(edges.get(j).nodes[1]);
				}
			}
			adjList.add(adjVerts);
		}
		return adjList;
	}
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260719278"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		WGraph resGraph = new WGraph(graph);
		// setting back edges in residual graph with weights equalling to 0
		for(Edge e:graph.getEdges()){
			resGraph.addEdge(new Edge(e.nodes[1], e.nodes[0], 0));
		}
		ArrayList<Integer> path = pathDFS(source, destination, graph);
		while(!path.isEmpty()){
			// find the edges of found path
			ArrayList<Edge> pathEdges = new ArrayList<Edge>();
			for(int i = 0; i<path.size()-1;i++){
				pathEdges.add(graph.getEdge(path.get(i), path.get(i+1)));
			}
			// finding the bottle neck value
			int bottleneck = Integer.MAX_VALUE;
			for(int i = 0; i<pathEdges.size();i++){
				bottleneck = Math.min(bottleneck, pathEdges.get(i).weight);
			}
			// updating residual graph
			for(Edge e:pathEdges){
				//updating forward edges
				int forwardWeight = resGraph.getEdge(e.nodes[0],e.nodes[1]).weight;
				int newFW = forwardWeight - bottleneck;
				resGraph.setEdge(e.nodes[0], e.nodes[1], newFW);
				//updating backward Edges
				resGraph.setEdge(e.nodes[1],e.nodes[0], bottleneck);
			}
			//incrementing the bottle neck value
			maxFlow += bottleneck;
			//call pathDFS to find new path
			path = pathDFS(source, destination, resGraph);
		}
		//updating original graph
		for(int j = 0; j<graph.getEdges().size()-1;j++){
			Edge e = resGraph.getEdges().get(j);
			int weight = resGraph.getEdge(e.nodes[0], e.nodes[1]).weight;
			graph.setEdge(e.nodes[0], e.nodes[1], graph.getEdges().get(j).weight - weight);
		}
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 //String file = args[0];
		 String file = "ff2.txt";
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
