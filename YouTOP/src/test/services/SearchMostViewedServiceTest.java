package services;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import com.google.api.services.youtube.model.SearchResult;

public class SearchMostViewedServiceTest {

	SearchMostViewedService search = new SearchMostViewedService();
	
	@Test
	public void testGetMostViewedAll() {
		long maxResults = 2;
		JSONArray actualResult = search.getMostViewedAll(maxResults);
		long viewCountFirst = Long.parseLong(actualResult.getJSONObject(0).get("viewCount").toString());
		long viewCountSecond = Long.parseLong(actualResult.getJSONObject(1).get("viewCount").toString());
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.length());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByCategory() {
		long maxResults = 2;
		JSONArray actualResult = search.getMostViewedByCategory(maxResults, "10");
		long viewCountFirst = Long.parseLong(actualResult.getJSONObject(0).get("viewCount").toString());
		long viewCountSecond = Long.parseLong(actualResult.getJSONObject(1).get("viewCount").toString());
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.length());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByCategoryList() {
		long maxResults = 2;
		List<SearchResult> actualResult = search.getMostViewedByCategoryList(maxResults, "10");
		long viewCountFirst = Long.parseLong(actualResult.get(0).get("viewCount").toString());
		long viewCountSecond = Long.parseLong(actualResult.get(1).get("viewCount").toString());
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.size());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByKeyword() {
		long maxResults = 2;
		JSONArray actualResult = search.getMostViewedByKeyword(maxResults, "dog");
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.length());
	}
	
	@Test
	public void testGetMostViewedByMultipleCategories() {
		long maxResults = 2;
		JSONArray actualResult = search.getMostViewedByMultipleCategories(2, Arrays.asList("10", "1"));
		long viewCountFirst = Long.parseLong(((JSONObject) actualResult.get(0)).get("viewCount").toString());
		long viewCountSecond = Long.parseLong(((JSONObject) actualResult.get(1)).get("viewCount").toString());
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.length());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	

}
