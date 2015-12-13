package services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.GVideo;

public class SearchMostPopularServiceTest {

	SearchMostPopularService service = new SearchMostPopularService();
	
	@Test
	public void getMostPopularAllTest() {
		List<GVideo> list = service.getMostPopularAll(5);
		assertNotNull(list);
		assertEquals(5, list.size());
	}
	
	@Test
	public void getMostPopularByCategoryTest() {
		List<GVideo> list = service.getMostPopularByCategory(5, "10");
		assertNotNull(list);
		assertEquals(5, list.size());
		assertEquals(10, list.get(0).getSnippet().getCategoryId());
		
	}
	
	@Test
	public void getMostPopularFromMultipleCategoriesTest() {
		List<String> categoryIds = Arrays.asList("10", "1");
		List<GVideo> list = service.getMostPopularByMultipleCategories(5, categoryIds);
		List<Integer> idsFromResults = new ArrayList<Integer>();
		
		for(GVideo video : list) {
			idsFromResults.add(video.getSnippet().getCategoryId());
		}
		
		assertNotNull(list);
		assertEquals(5, list.size());
		assertTrue(idsFromResults.contains(1) || idsFromResults.contains(10));
	}

}
