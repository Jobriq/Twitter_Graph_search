/**
 * 
 */
package util;

import static org.junit.Assert.*;
import org.junit.Test;

import main.AdjListGraph;

/**
 * @author
 * *
 */
public class GraphTest {
	
	public AdjListGraph graph = new AdjListGraph();
	
	@Test
	public void testLoadGraph(){
		try{
			LoadGraph.loadGraph(graph, "...");
			fail("Not throws exception for invalid file path.");
		} catch(Exception e) {}
		
		try{
			LoadGraph.loadGraph(graph, "data/simple_test.txt");
		} catch(Exception e) {
			fail("Could not find the file to load");
		}
		
		assertEquals("Vertices Number: test after load the graph.",6,graph.getNumVertices());
		
		assertEquals("Edges Number: test after load the graph.",14,graph.getNumEdges());
	}
	
	@Test
	public void testAddVertex() {
		graph.addVertex(0);
		assertEquals("Add: test the size after add a vertex.",1,graph.getNumVertices());
		graph.addVertex(1);
		assertEquals("Add: test the size after add a vertex.",2,graph.getNumVertices());
		
		graph.addVertex(1);
		assertEquals("Add: test the size after add a duplicated vertex.",2,graph.getNumVertices());
	}
	
	@Test
	public void testAddEdge() {
		try{
			graph.addEdge(0, 1);
			fail("Add egdes between two non existent vertices");
		}
		catch(IllegalArgumentException e){
  
		}
		
		graph.addVertex(0);
		graph.addVertex(1);
		try{
			graph.addEdge(2, 3);
			fail("Add a edge to a non existent vertex");
		}
		catch(IllegalArgumentException e){
			
		}
		
		graph.addEdge(0, 1);
		assertEquals("Add: test the size after add a edge.",1,graph.getNumEdges());
		
		graph.addEdge(0, 1);
		assertEquals("Add: test the size after add a duplicated edge.",2,graph.getNumEdges());
	}
	
	@Test
	public void testGetNeighbors(){
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		
		assertEquals("GetNeighbors: test the number of neighbors before add edges "
				+ "to vertex 0.",0,graph.getNeighbors(0).size());
		assertEquals("GetNeighbors: test the number of neighbors before add edges "
				+ "to vertex 1.",0,graph.getNeighbors(1).size());
		assertEquals("GetNeighbors: test the number of neighbors before add edges "
				+ "to vertex 2.",0,graph.getNeighbors(2).size());
		
		graph.addEdge(0, 1);
		assertEquals("Getneighbors: test the number os neighbors after add edges "
				+ "between vertices 0 and 1.",1,graph.getNeighbors(0).size());
		
		assertEquals("GetNighbors: test the neighbors returned after add edge "
				+ "between vertices 0 and 1.",(Integer)1,(Integer)graph.getNeighbors(0).iterator().next());
		
		graph.addEdge(0, 2);
		assertEquals("Getneighbors: test the number os neighbors after add edges "
				+ "between vertices 0 and 2.",2,graph.getNeighbors(0).size());
	}
	
	@Test
	public void testDegreeSequence(){
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		
		assertEquals("DegreeSequence: test the degree before add edges.",(Integer)3,
				(Integer)graph.degreeSequence().size());
		
		assertEquals("DegreeSequence: test the degree before add edges.",(Integer)0,
				(Integer)graph.degreeSequence().iterator().next().getValue());
		
		graph.addEdge(0,1);
		assertEquals("DegreeSequence: test the degree after add edges.",(Integer)1,
				(Integer)graph.degreeSequence().iterator().next().getValue());
		
		graph.addEdge(0,2);
		assertEquals("DegreeSequence: test the degree after add edges.",(Integer)2,
				(Integer)graph.degreeSequence().iterator().next().getValue());
	}
	
	@Test
	public void testLoadConfig(){
		LoadConfig lc = new LoadConfig();
		
		try{
			lc.loadGraphConfig("...");
			fail("Not throws exception for invalid file path.");
		} catch (Exception e) {}
		
		try{
			lc.loadGraphConfig("data/config.cfg");
		}catch(Exception e){
			fail("Could not find the file");
		}
		
		 assertTrue("Load Config: load the right value of Level Weight, between 0.0 and 1.0.", 
				 ((lc.getWeightDecrease()<=1.0) || (lc.getWeightDecrease()>=0.0)));
		 
		 assertTrue("Load Config: load the right value of List Position, top, middle or bottom.", 
				 ((lc.getListPosition().equals("top")) || (lc.getListPosition().equals("middle"))
						 || (lc.getListPosition().equals("bottom"))));
		 
		 assertTrue("Load Config: load the right value of Vertices Analyzed, between 0.0 and 1.0.", 
				 ((lc.getVerticesAnalyzed()<=1.0) || (lc.getVerticesAnalyzed()>=0.0)));
	}
	

}
