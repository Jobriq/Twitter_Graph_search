package main;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author
 * 
 */
public interface Graph {
	
	/**
	 * Report size of vertex set
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices();
	
	/**
	 * Report size of edge set
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges();
	
	/**
	 * Add new vertex to the graph passing a integer as ID of the vertex 
	 * 
	 * @param ID of the vertex
	 */
	public void addVertex(int ID);
    
	/**
	 * Add new edge to the graph between given vertices,
	 * 
	 * @param id1 Index of the start point of the edge to be added.
	 * @param id2 Index of the end point of the edge to be added.
	 */
	public void addEdge(int id1, int id2);
	
	/**
	 * Get all neighbors of a given vertex.
	 * 
	 * @param id Index of vertex in question.
	 * @return List of indices of all vertices that are adjacent to v.
	 */
	public Set<Integer> getNeighbors(int id);
	
	/**
	 * The degree sequence of a graph is a sorted TreeMap<ID,Degree>
	 * (organized in numerical order from largest to smallest degree, 
	 * possibly with repetitions) list of the degrees of the vertices
	 * in the graph.
	 * 
	 * @return The degree sequence of this graph as a TreeMap<ID,Degree>.
	 */
	public ArrayList<Entry<Integer, Integer>> degreeSequence();
} 
