package controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.GSearch;
import model.Validator;
import services.SearchMostViewedService;


@Controller
public class SearchViewsController {

	SearchMostViewedService service = new SearchMostViewedService();
	
	@RequestMapping(value = "/searchViews.do", method = RequestMethod.GET)
	@ResponseBody
	public List<GSearch> doSearchProcess(
			@RequestParam("criteria") String criteria,
			@RequestParam(value = "categories", required = false) List<String> categories,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam("maxResults") long maxResults) {
	
		if(categories != null) {
			System.out.println("\nParameters: "+criteria+", "+categories.toString()+", "+keyword+", "+maxResults);
		}
		else {
			System.out.println("\nParameters: "+criteria+", "+null+", "+keyword+", "+maxResults);
		}
		
		JSONObject validationMsge = doValidation(criteria, categories, keyword, maxResults);
		
		if(!validationMsge.getBoolean("isValid")){
			return null;
		}
		
		String searchType = determineSearchType(criteria, categories, keyword);
		List<GSearch> response = executeSearch(searchType, categories, keyword, maxResults);
	
		
		return response;
	}
	
	private JSONObject doValidation(String criteria, List<String> categories, String keyword, long maxResults) {
		JSONObject validMsge = new JSONObject();
		JSONObject criteriaMsge = Validator.validateCriteria(criteria);
		JSONObject categoriesAndKeywordMsge = Validator.validateCategoriesAndKeyword(categories, keyword);
		JSONObject maxResultsMsge = Validator.validateMaxResults(maxResults);
		
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
	
	public String determineSearchType(String criteria, List<String> categories, String keyword) {
		String searchType = "";

		if(criteria.equals("views")) {
			if(categories != null && categories.size() > 0) {
				searchType = "views,category";
			}
			else {
				searchType = "views,keyword";
			}		
		}
		System.out.println("Search type: "+searchType);
		return searchType;
	}

	public List<GSearch> executeSearch(String searchType, List<String> categories, String keyword, long maxResults) {
		System.out.println("Executing search...");
		List<GSearch> response = new ArrayList<GSearch>();
		if(searchType.contains("views") && searchType.contains("category")) {
			
			if(categories.contains("All")){
				response = service.getMostViewedAll(maxResults);
			}
			else{
				if(categories.size() == 1) {
					response = service.getMostViewedByCategory(maxResults, categories.get(0));
				}
				else {
					response = service.getMostViewedByMultipleCategories(maxResults, categories);
				}
			}
		}
		else if(searchType.contains("views") && searchType.contains("keyword")){
			response = service.getMostViewedByKeyword(maxResults, keyword);
		}
		
		return response;
	}
	

	
	
	
}
