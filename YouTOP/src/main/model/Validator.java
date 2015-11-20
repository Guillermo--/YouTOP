package model;

import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class Validator {
	final List<String> validCriteria = Arrays.asList("likes", "views");
	final List<String> validMaxResults = Arrays.asList("10", "25", "50", "100");

	public static JSONObject validateMaxResults(long maxResults) {
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
	
	public static JSONObject validateCriteria(String criteria) {
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
	
	public static JSONObject validateCategoriesAndKeyword(List<String> categories, String keyword) {
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
	
}
