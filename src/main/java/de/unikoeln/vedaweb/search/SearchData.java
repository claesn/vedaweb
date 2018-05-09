package de.unikoeln.vedaweb.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SearchData {
	
	@JsonProperty("mode")
	private String mode;
	
	@JsonProperty("input")
	private String input;
	
	@JsonProperty("scopes")
	private List<SearchScope> scopes;
	
	@JsonProperty("blocks")
	private List<Map<String, Object>> blocks;

	
	public SearchData(){
		blocks = new ArrayList<Map<String, Object>>();
	}
	
	
	public String getMode() {
		return mode;
	}

	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	public String getInput() {
		return input;
	}


	public void setInput(String input) {
		this.input = input;
	}


	public List<SearchScope> getScopes() {
		return scopes;
	}


	public void setScopes(List<SearchScope> scopes) {
		this.scopes = scopes;
	}


	public List<Map<String, Object>> getBlocks() {
		return blocks;
	}


	public void setBlocks(List<Map<String, Object>> blocks) {
		this.blocks = blocks;
	}


	public void addBlock(Map<String, Object> block) {
		this.blocks.add(block);
	}
	
	@Override
	public String toString() {
		return "mode:" + mode + (input != null ? " input:" + input : "");
	}
	
//	public void cleanAndFormatFields(){
//		//clean search blocks
//		for (Map<String, Object> block : blocks){
//			block.values().removeAll(Collections.singleton(""));
//			for (String field : block.keySet()){
//				if (block.get(field) instanceof String
//						&& ((String)block.get(field)).matches("-?\\d+")){
//					block.put(field, Integer.parseInt(((String)block.get(field))));
//				}
//			}
//		}
//		//clean search scope
//		book = book > 0 ? book : -1;
//		hymn = hymn > 0 ? hymn : -1;
//	}
	
}