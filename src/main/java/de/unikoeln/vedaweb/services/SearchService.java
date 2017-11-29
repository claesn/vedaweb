package de.unikoeln.vedaweb.services;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.unikoeln.vedaweb.search.SearchRequest;
import de.unikoeln.vedaweb.search.SearchResult;
import de.unikoeln.vedaweb.search.SearchResults;
import de.unikoeln.vedaweb.search.TargetToken;


@Service
public class SearchService {
	
	private static final int MAX_SEARCH_HITS = 1000000;
	
	@Autowired
	private IndexService indexService;
	
	
	
	public SearchResults search(Map<String, String> params){
		return search(generateSearchRequest(params));
	}
	
	
	public SearchResults search(SearchRequest sr){
		SearchResults results = new SearchResults();
		
		for (TargetToken targetToken : sr.getTargetTokens()){
			try {
				search(targetToken, results);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return results;
	}
	
	
	private void search(TargetToken targetToken, SearchResults results) throws ParseException{
		BooleanQuery.Builder query = new BooleanQuery.Builder();
		
		for (String field : targetToken.getAttributeFields()){
			Object v = targetToken.getAttribute(field);
			if (v instanceof String){
				query.add(new QueryParser(field, indexService.getAnalyzer()).parse((String)v),
						BooleanClause.Occur.MUST);
			} else if (v instanceof Integer){
				System.out.println("INT QUERY");
				IntPoint.newExactQuery(field, (Integer)v);
			}
		}
		
		TopDocs topDocs = null;
		
		try {
			topDocs = indexService.getSearcher().search(query.build(), MAX_SEARCH_HITS, Sort.RELEVANCE, true, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        System.out.println("[INFO] Found " + topDocs.totalHits + " documents for '" + targetToken + "'.");
        
        //contruct search results
        SearchResults newResults = new SearchResults();
        for (ScoreDoc sd : topDocs.scoreDocs){
        	try {
        		Document doc = indexService.getSearcher().doc(sd.doc);
        		newResults.add(new SearchResult(
        						sd.score,
        						doc.get("location_id"),
        						targetToken,
        						doc));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        results.retainCommon(newResults);
	}
	
	
	private SearchRequest generateSearchRequest(Map<String, String> params) {
		
		SearchRequest sr = new SearchRequest(
			(params.get("book").length() == 0 ? -1 : Integer.parseInt(params.get("book"))),
			(params.get("hymn").length() == 0 ? -1 : Integer.parseInt(params.get("hymn")))
		);
		
		for (String key : params.keySet()){
			char blockNr = key.charAt(key.length() - 1);
			if (Character.isDigit(blockNr)){
				
			}
		}
		
		return sr;
	}


}
