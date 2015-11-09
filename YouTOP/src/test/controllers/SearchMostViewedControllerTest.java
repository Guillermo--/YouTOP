package controllers;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.junit.Test;

public class SearchMostViewedControllerTest {

	SearchMostViewedController controller = new SearchMostViewedController();
	
	@Test
	public void testGetMostViewedAll() {
		long maxResults = 2;
		JSONArray actualResult = controller.getMostViewedAll(2);
		long viewCountFirst = Long.parseLong(actualResult.getJSONObject(0).get("viewCount").toString());
		long viewCountSecond = Long.parseLong(actualResult.getJSONObject(1).get("viewCount").toString());
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.length());
		assertTrue(viewCountFirst > viewCountSecond);
	}

}
