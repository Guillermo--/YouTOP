package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import model.GVideo;

public class SearchMostViewedServiceTest {

	SearchMostViewedService search = new SearchMostViewedService();
	
	@Test
	public void testGetMostViewedAll() {
		long maxResults = 2;
		List<GVideo> actualResult = search.getMostViewedAll(maxResults);
		long viewCountFirst = actualResult.get(0).getViewCount();
		long viewCountSecond = actualResult.get(1).getViewCount();
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.size());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByCategory() {
		long maxResults = 2;
		List<GVideo> actualResult = search.getMostViewedByCategory(maxResults, "10");
		long viewCountFirst = actualResult.get(0).getViewCount();
		long viewCountSecond = actualResult.get(1).getViewCount();
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.size());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByCategoryList() {
		long maxResults = 2;
		List<GVideo> actualResult = search.getMostViewedByCategory(maxResults, "10");
		long viewCountFirst = actualResult.get(0).getViewCount();
		long viewCountSecond = actualResult.get(1).getViewCount();
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.size());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByKeyword() {
		long maxResults = 2;
		List<GVideo> actualResult = search.getMostViewedByKeyword(maxResults, "dog");
		long viewCountFirst = actualResult.get(0).getViewCount();
		long viewCountSecond = actualResult.get(1).getViewCount();
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.size());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	@Test
	public void testGetMostViewedByMultipleCategories() {
		long maxResults = 2;
		List<GVideo> actualResult = search.getMostViewedByMultipleCategories(maxResults, Arrays.asList("10", "1"));
		long viewCountFirst = actualResult.get(0).getViewCount();
		long viewCountSecond = actualResult.get(1).getViewCount();
		
		assertNotNull(actualResult);
		assertEquals(maxResults, actualResult.size());
		assertTrue(viewCountFirst > viewCountSecond);
	}
	
	

}
