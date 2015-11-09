package controllers;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import services.SearchMostViewedService;

@Controller
@RequestMapping("/mostViewed")
public class SearchMostViewedController {

	SearchMostViewedService service = new SearchMostViewedService();
	
	@RequestMapping("/all")
	public JSONArray getMostViewedAll(@RequestParam long maxResults) {
		JSONArray jsonArray = service.getMostViewedAll(maxResults);
		return jsonArray;
	}
	
	
}
