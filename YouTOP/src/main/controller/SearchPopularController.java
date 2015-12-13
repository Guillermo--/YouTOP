package controller;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import model.GVideo;
import model.Validator;
import services.SearchMostPopularService;

@Controller
public class SearchPopularController {

	SearchMostPopularService service = new SearchMostPopularService();
	
	@RequestMapping(value = "/searchPopular.do", method = RequestMethod.GET) 
	@ResponseBody
	public List<GVideo> doSearchProcess (
			@RequestParam("criteria") String criteria,
			@RequestParam(value = "categories", required = false) List<String> categories,
			@RequestParam("maxResults") long maxResults){
		
		if(categories != null) {
			System.out.println("\nParameters: "+criteria+", "+categories.toString()+", "+maxResults);
		}
		else {
			System.out.println("\nParameters: "+criteria+", "+null+", "+maxResults);
		}
		
		JSONObject validationMsge = doValidation(criteria, categories, maxResults);
		
		if(!validationMsge.getBoolean("isValid")){
			return null;
		}
		
		List<GVideo> response = executeSearch(categories, maxResults);
	
		return response;
	}
	
	private JSONObject doValidation(String criteria, List<String> categories, long maxResults) {
		JSONObject validMsge = new JSONObject();
		JSONObject criteriaMsge = Validator.validateCriteria(criteria);
		JSONObject categoriesMsge = Validator.validateCategories(categories);
		JSONObject maxResultsMsge = Validator.validateMaxResults(maxResults);
		
		System.out.println("Validating...");
		if(!criteriaMsge.getBoolean("isValid")) {
			return criteriaMsge;
		}
		if(!categoriesMsge.getBoolean("isValid")) {
			return categoriesMsge;
		}
		if(!maxResultsMsge.getBoolean("isValid")) {
			return maxResultsMsge;
		}
		else {
			validMsge.put("isValid", true);
			return validMsge;
		}
	}
	
	public List<GVideo> executeSearch(List<String> categories, long maxResults) {
		System.out.println("Executing search...");
		List<GVideo> response = new ArrayList<GVideo>();
		if(categories.contains("All")){
			response = service.getMostPopularAll(maxResults);
		}
		else{
			if(categories.size() == 1) {
				response = service.getMostPopularByCategory(maxResults, categories.get(0));
			}
			else {
				response = service.getMostPopularByMultipleCategories(maxResults, categories);
			}
		}
		return response;
	}

}
