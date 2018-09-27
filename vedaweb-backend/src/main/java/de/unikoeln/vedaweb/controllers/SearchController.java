package de.unikoeln.vedaweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.unikoeln.vedaweb.search.SearchData;
import de.unikoeln.vedaweb.services.ElasticSearchService;



@RestController
@RequestMapping("api")
public class SearchController {
	
	@Autowired
	private ElasticSearchService search;
	
	
	@PostMapping(value = "/search", produces = {"application/json"})
    public String searchView(@RequestBody SearchData searchData) {
		System.out.println("QUERY BLOCKS: " + searchData.getBlocks());
    	return search.search(searchData).toString();
    }
	
}
