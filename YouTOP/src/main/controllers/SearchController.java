package controllers;

import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import services.SearchMostPopularService;
import services.SearchMostViewedService;


@Controller
public class SearchController {

	SearchMostViewedService service = new SearchMostViewedService();
	
	@RequestMapping("/search", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> doSearchProcess(
			@RequestParam("criteria") String criteria,
			@RequestParam("categories") List<String> categories,
			@RequestParam("keyword") String keyword,
			@RequestParam("maxResults") long maxResults) {
	
		System.out.println("Parameters: "+criteria+", "+categories.toString()+", "+keyword+", "+maxResults);
		JSONObject validationMsge = doValidation(criteria, categories, keyword, maxResults);
		
		if(!validationMsge.getBoolean("isValid")){
			return new ResponseEntity<String>(validationMsge.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String searchType = determineSearchType(criteria, categories, keyword);
		JSONArray response = executeSearch(categories, keyword, searchType, maxResults);

		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

	
	
	private JSONObject doValidation(String criteria, List<String> categories, String keyword, long maxResults) {
		JSONObject validMsge = new JSONObject();
		JSONObject criteriaMsge = validateCriteria(criteria);
		JSONObject categoriesAndKeywordMsge = validateCategoriesAndKeyword(categories, keyword);
		JSONObject maxResultsMsge = validateMaxResults(maxResults);
		
		System.out.println("Validating...");
		if(!criteriaMsge.getBoolean("isValid")) {
			return criteriaMsge;
		}
		if(!categoriesAndKeywordMsge.getBoolean("isValid")) {
			return categoriesAndKeywordMsge;
		}
		if(!maxResultsMsge.getBoolean("isValid")) {
			return maxResultsMsge;
		}
		
		else {
			validMsge.put("isValid", true);
			return validMsge;
		}
	}
	
	private String determineSearchType(String criteria, List<String> categories, String keyword) {
		String searchType = "";
		System.out.println("Determining search type...");
		if(criteria.equals("likes")) {
			if(categories != null && categories.size() > 0) {
				searchType = "likes,category";
			}
			else {
				searchType = "likes,keyword";
			}
		}
		else if(criteria.equals("views")) {
			if(categories != null && categories.size() > 0) {
				searchType = "views,category";
			}
			else {
				searchType = "views,keyword";
			}		
		}
		return searchType;
	}

	private JSONArray executeSearch(List<String> categories, String keyword, String searchType, long maxResults) {
		System.out.println("Executing search...");
		
		SearchMostPopularService searchPopular = new SearchMostPopularService();
		SearchMostViewedService searchViews = new SearchMostViewedService();
		JSONArray response = new JSONArray();
		if(searchType.contains("likes") && searchType.contains("category")) {
			if(categories.contains("All")){
				
			}
		}
		else if(searchType.contains("likes") && searchType.contains("keyword")) {
			
		}
		else if(searchType.contains("views") && searchType.contains("category")) {
			if(categories.contains("All")){
				response = searchViews.getMostViewedAll(maxResults);
			}
			else
			{
				
			}
		}
		else if(searchType.contains("views") && searchType.contains("keyword")){
			
		}
		
		return response;
	}
	
	private JSONObject validateCriteria(String criteria) {
		List<String> validCriteria = Arrays.asList("likes", "views");
		JSONObject validationMessage = new JSONObject();
		if(criteria == null || criteria.isEmpty() || (!validCriteria.contains(criteria))) {
			validationMessage.put("isValid", false);
			validationMessage.put("message", "Please select a valid criteria from the dropdown.");
		}
		else{
			validationMessage.put("isValid", true);
		}
		return validationMessage;
	}
	
	private JSONObject validateCategoriesAndKeyword(List<String> categories, String keyword) {
		JSONObject validationMessage = new JSONObject();
		if(categories.size() == 0 && (keyword == null || keyword.isEmpty())){
			validationMessage.put("isValid", false);
			validationMessage.put("message", "Please select a category or enter a keyword.");
		}
		else {
			validationMessage.put("isValid", true);
		}
		return validationMessage;
	}
	
	private JSONObject validateMaxResults(long maxResults) {
		List<String> validMaxResults = Arrays.asList("10", "25", "50", "100");
		JSONObject validationMessage = new JSONObject();
		if(String.valueOf(maxResults) == null || 
				String.valueOf(maxResults).isEmpty() || 
					(!validMaxResults.contains(String.valueOf(maxResults)))) {
			validationMessage.put("isValid", false);
			validationMessage.put("message", "Please select a valid choice from the #Results dropdown.");
		}
		else {
			validationMessage.put("isValid", true);
		}
		return validationMessage;
	}
	
	
	
	
	
}
