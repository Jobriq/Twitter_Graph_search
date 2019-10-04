package main;

import java.util.*;
import java.util.Map.Entry;

import util.LoadConfig;

/**
 */
public class AdjListGraph implements Graph {

	private int numVertices;
	private int numEdges;
	private HashMap<Integer, HashSet<Integer>> adjList;
	private LoadConfig config;
	
	/**
	 * AdjListGraph constructor
	 */
	public AdjListGraph(){
		numVertices = 0;
		numEdges = 0;
		adjList = new HashMap<Integer, HashSet<Integer>>();
		config = new LoadConfig();
	}
	
	/**
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return numVertices;
	}

	/**
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return numEdges;
	}

	/**
	 * Add new vertex with integer ID to the graph 
	 * @param id of the vertex
	 */
	public void addVertex(int id) {
		if(adjList.containsKey(id))
			return;
		
		HashSet<Integer> neighbors = new HashSet<Integer>();
		adjList.put(id,  neighbors);
		numVertices++;
	}

	/**
	 * Add an edge to the graph between two given vertices
	 * 
	 * @param id1 Index of the start point of the edge to be added.
	 * @param id2 Index of the end point of the edge to be added.
	 */
	public void addEdge(int id1, int id2) {
		//if there are no vertices for the given IDs throw an exception
		if(!adjList.containsKey(id1) || !adjList.containsKey(id2))
			throw new IllegalArgumentException("Invalid vertex ID");
		
		(adjList.get(id1)).add(id2);
		numEdges++;
	}

	/**
	 * Get  neighbors of a vertex.
	 * 
	 * @param id Index of vertex in question.
	 * @return List of indices of all vertices that are adjacent to v.
	 */
	
	public Set<Integer> getNeighbors(int id) {
		//if there is no vertex in the graph, throw an exception
		if(!adjList.containsKey(id))
			throw new IllegalArgumentException("Invalid ID");
		
		return (Set<Integer>)adjList.get(id).clone();
	}

	/**
	 * The degree sequence of a graph is a sorted HashMap<ID,Degree>
	 * organized from largest to smallest with repetitions.
	 * 
	 * @return The degree sequence of this graph as a HashMap<ID,Degree>.
	 */
	public ArrayList<Map.Entry<Integer, Integer>> degreeSequence() {
		HashMap<Integer, Integer> dseq = new HashMap<Integer, Integer>();
		
		for(Integer id: adjList.keySet()){
			dseq.put(id, adjList.get(id).size());
		}
		
		return orderMapByValueInt(dseq, false);
	}

	/**
	 * 
	 * @return A string representation of the graph
	 */
	public String toString() {
		String s = "Adjacency List:\n";
		s += "Vertices: " + getNumVertices() + "\n" + "Edges: " + getNumEdges() + "\n" + adjList.toString() + "\n";
		
		return s;
	}
	
	/**
	 * Calculate the power ranking - pr of a graph representing a social
	 * network relationship.
	 * 
	 * @return HashMap<ID,Influence Ranking Index> ID of a person and PR index 
	 * of this person. Ordered in decreasing order of PE, so the first ID corresponds
	 * to the most "powerful" person in the network.
	 */
	public ArrayList<Map.Entry<Integer,Double>> powerRanking(){
		HashMap<Integer,Double> pr = new HashMap<Integer,Double>();
		ArrayList<Map.Entry<Integer,Integer>> dseq;
		
		dseq = degreeSequence();
		
		try {
			config.loadGraphConfig("data/config.cfg");
		} catch (Exception e) {
			System.out.println("Error loading configuration file from " +
					"(data/config.cfg)");
		}
		
		Double vertA = config.getVerticesAnalyzed();
		vertA *= dseq.size();
		
		//Report message every 150 elements processed
		for(int i=0; i<vertA;i++){
			Integer id = dseq.get(i).getKey();
			if((i%150) == 0)
				System.out.println("Report: " + i + " elements processed");
			pr.put(id, individualPR(id));
		}
		System.out.println("");
		
		return orderMapByValueDbl(pr, false);
	}
	
	public Double individualPR(Integer id){
		Double indPR = 0.0;
		
		// setup for BFS
		Queue<Map.Entry<Integer, Double>> toExplore = new LinkedList<Map.Entry<Integer,Double>>();
		HashSet<Integer> visited = new HashSet<Integer>();
		Map.Entry<Integer, Double> current = null;
		Double nextWeight;
		Double weightDecrease = config.getWeightDecrease();
		
		toExplore.add(new AbstractMap.SimpleEntry<Integer,Double>(id, 1.0));
		visited.add(id);

		while (!toExplore.isEmpty()) {
			current = toExplore.remove();
			Set<Integer> neighbors = getNeighbors(current.getKey());
			nextWeight = current.getValue() * weightDecrease;
			
			for (Integer neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					indPR += current.getValue();
					toExplore.add(new AbstractMap.SimpleEntry<Integer,Double>(neighbor, nextWeight));
				}
			}
		}
		
		return indPR;
	}
	
	
	/**
	 * Order a map by increasing or decreasing value
	 * 
	 * @param map The map to be ordered
	 * @param upward true to order upward, false otherwise
	 */
	private <M> ArrayList<Map.Entry<M, Integer>> orderMapByValueInt(Map<M,Integer> map, final boolean upward){
		
		final Comparator<Map.Entry<M, Integer>> comp = 
				new Comparator<Map.Entry<M, Integer>>() {

			public int compare(Map.Entry<M, Integer> a1, Map.Entry<M, Integer> a2) {
				if(upward)
					return (a1.getValue()).compareTo( a2.getValue() );
				else
					return (a2.getValue()).compareTo( a1.getValue() );
			}
		};
		
		ArrayList<Map.Entry<M, Integer>> list = 
				new ArrayList<Map.Entry<M, Integer>>(map.entrySet());
		Collections.sort(list, comp);
		
		return list;
	}
	
}
