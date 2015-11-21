package controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.GVideo;
import model.Validator;
import services.SearchMostViewedService;


@Controller
public class SearchController {

	SearchMostViewedService service = new SearchMostViewedService();
	
//	@RequestMapping(value = "hello", method = RequestMethod.GET)
//	@ResponseBody
//	public List<GVideo> helloWorld() {
//		GVideo video = new GVideo();
//		video.setId("123");
//		video.setTitle("fight video");
//		video.setUrl("youtube.com/123");
//		GVideo video2 = new GVideo();
//		video2.setId("456");
//		video2.setTitle("animals");
//		return Arrays.asList(video,video2);
//	}
	
	@RequestMapping("/search.do")
	@ResponseBody
	public ResponseEntity<String> doSearchProcess(
			@RequestParam("criteria") String criteria,
			@RequestParam(value = "categories", required = false) List<String> categories,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam("maxResults") long maxResults) {
	
		System.out.println("Parameters: "+criteria+", "+categories.toString()+", "+keyword+", "+maxResults);
		JSONObject validationMsge = doValidation(criteria, categories, keyword, maxResults);
		
		if(!validationMsge.getBoolean("isValid")){
			return new ResponseEntity<String>(validationMsge.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String searchType = determineSearchType(criteria, categories, keyword);
		List<GVideo> response = executeSearch(searchType, categories, keyword, maxResults);
		
		System.out.println(response.toString());
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
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

	public List<GVideo> executeSearch(String searchType, List<String> categories, String keyword, long maxResults) {
		System.out.println("Executing search...");
		
		SearchMostViewedService searchViews = new SearchMostViewedService();
		List<GVideo> response = new ArrayList<GVideo>();
		if(searchType.contains("views") && searchType.contains("category")) {
			if(categories.contains("All")){
				response = searchViews.getMostViewedAll(maxResults);
			}
			else{
			}
		}
		else if(searchType.contains("views") && searchType.contains("keyword")){
		}
		
		return response;
	}
	

	
	
	
}
