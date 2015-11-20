package controllers;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import controller.SearchController;

public class SearchControllerTest {

	SearchController searchController = new SearchController();
	String criteriaLikes = "likes";
	String criteriaViews = "views";
	List<String> categoriesNotEmpty = Arrays.asList("Random1","Random2");
	List<String> categoriesEmpty = null;
	String keywordEmpty = "";
	String keywordNotEmpty = "dogs";
	String searchTypeViewsCategory = "views,category";
	String searchTypeViewsKeyword = "views,keyword";
	
	@Test
	public void testDetermineSearchType() {
		String actualSearchType1 = searchController.determineSearchType(criteriaLikes, categoriesNotEmpty, keywordEmpty);
		String expectedSearchType1 = "likes,category";
		
		String actualSearchType2 = searchController.determineSearchType(criteriaLikes, categoriesEmpty, keywordNotEmpty);
		String expectedSearchType2 = "likes,keyword";
		
		String actualSearchType3 = searchController.determineSearchType(criteriaViews, categoriesNotEmpty, keywordEmpty);
		String expectedSearchType3 = "views,category";
		
		String actualSearchType4 = searchController.determineSearchType(criteriaViews, categoriesEmpty, keywordNotEmpty);
		String expectedSearchType4 = "views,keyword";
		
		assertEquals(expectedSearchType1, actualSearchType1);
		assertEquals(expectedSearchType2, actualSearchType2);
		assertEquals(expectedSearchType3, actualSearchType3);
		assertEquals(expectedSearchType4, actualSearchType4);
	}
	
	@Test
	public void testDoValidation(){
		
	}
	
	@Test
	public void testExecuteSearch() {

	}

}
