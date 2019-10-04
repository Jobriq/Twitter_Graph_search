/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import util.LoadConfig;
import util.LoadGraph;

/**
 * @author 
 *
 */
public class Application {

	public static void main(String[] args) {
		AdjListGraph graph = new AdjListGraph();
		LoadConfig graphConfig = new LoadConfig();
		ArrayList<Map.Entry<Integer,Double>> pr;
		String filePath = "data/facebook_ucsd.txt";
		String configInfo = new String();
		
		//Load graph from a given file path
		try {
			LoadGraph.loadGraph(graph, filePath);
		} catch (Exception e) {
			System.out.println("Erro trying to load configuration file from (" +
					filePath + ")");
		}
		System.out.println("Graph loaded\n");
		
		//Load configuration file just to show the configuration, this method
		//be invoked again by graph object.
		try {
			graphConfig.loadGraphConfig("data/config.cfg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Erro trying to load configuration file from " +
					"(data/config.cfg)");
		}
		
		configInfo  = "Configuration Info:";
		configInfo += "\nWeight Decrease: " + graphConfig.getWeightDecrease();
		configInfo += "\nList Position: " + graphConfig.getListPosition();
		configInfo += "\nVertices Analyzed: " + graphConfig.getVerticesAnalyzed() + "\n";
		System.out.println(configInfo);
	
		
		pr = graph.powerRanking();
		
		//Print a specific number of elements
		Iterator<Map.Entry<Integer, Double>> it = pr.iterator();
		Integer printCount = 30;
		String printString = printCount + " first elements in IR:\n";
		while(it.hasNext() && (printCount > 0)){
			printString += it.next().toString() + "\n";
			printCount--;
		}
		System.out.println(printString);
	}

}
