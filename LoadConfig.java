package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 */
public class LoadConfig {
	
	private Double weightDecrease;
	private String listPosition;
	private Double verticesAnalyzed;
	
	public LoadConfig(){
		
	}
	
	public void loadGraphConfig(String filePath) throws Exception{
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject;
		
		try{
			jsonObject = (JSONObject)jsonParser.parse(new FileReader(filePath));
			
			weightDecrease = (Double)jsonObject.get("Weight Decrease");
			listPosition = (String)jsonObject.get("List Position");
			verticesAnalyzed = (Double)jsonObject.get("Vertices Analyzed");
		}
		catch(Exception e){
			throw new FileNotFoundException(); 
		}
	}
	
	/**
	 * 
	 * @return Level Weight config
	 */
	public Double getWeightDecrease(){
		return weightDecrease;
	}
	
	/**
	 * Return List Position config to calculate the Power Ranking - PR
	 * could be "top", "middle" or "bottom"
	 * 
	 * @return List Position
	 */
	public String getListPosition(){
		return listPosition;
	}
	
	/**
	 * Return the percentage of vertices that will be analyzed in the Power
	 * Ranking calculation.
	 * 
	 * @return Percentage of vertices to be analyzed
	 */
	public Double getVerticesAnalyzed() {
		return verticesAnalyzed;
	}
}	
