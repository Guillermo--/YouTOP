package services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import model.ConfigUtil;
import model.GVideo;
import model.JSONMapper;
import model.VideoUtil;
import model.YouTubeUtil;


public class SearchMostViewedService {

	private static YouTube youtube;
	private static String apiKey = ConfigUtil.getAPIKey();
	
	public static void main(String[] args) {
		SearchMostViewedService ss = new SearchMostViewedService();
		List<String> cats = new ArrayList<String>();
		cats.add("10");
		cats.add("1");
		System.out.println(ss.getMostViewedAll(1).get(0).getSnippet().getTitle());
	}
	
	public SearchMostViewedService(){
		youtube = YouTubeUtil.youtubeInit();
	}
	
	public List<GVideo> getMostViewedAll(long maxResults) {
		List<GVideo> videoList = new ArrayList<GVideo>();
		try {
			YouTube.Search.List search = youtube.search().list("snippet");
			search.setKey(apiKey);
			search.setOrder("viewCount");
			search.setMaxResults(maxResults);
			search.setType("video");
			
	        SearchListResponse searchResponse = search.execute();
	        List<SearchResult> searchResultList = searchResponse.getItems();
	        
	        if(searchResultList != null) {   	
	        	for(SearchResult searchResult : searchResultList) {
	        		String videoId = searchResult.getId().getVideoId().toString();
	        		String viewCount = VideoUtil.getVideoViewCount(videoId);
	        		searchResult.put("viewCount", viewCount);
	        		GVideo gvideo = JSONMapper.mapJSONtoGVideo(searchResult.toString());
	        		videoList.add(gvideo);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return videoList;
	}
	
	public JSONArray getMostViewedByCategory(long maxResults, String videoCategoryId) {
	    JSONArray jsonArray = new JSONArray();
        try {
			YouTube.Search.List search = youtube.search().list("snippet");
			search.setKey(apiKey);
			search.setOrder("viewCount");
			search.setVideoCategoryId(videoCategoryId);
			search.setMaxResults(maxResults);
			search.setType("video");
			
	        SearchListResponse searchResponse = search.execute();
	        List<SearchResult> searchResultList = searchResponse.getItems();
	        
	        if(searchResultList != null) {   	
	        	for(SearchResult searchResult : searchResultList) {
	        		String videoId = searchResult.getId().getVideoId().toString();
	        		String viewCount = VideoUtil.getVideoViewCount(videoId);
	        		searchResult.put("viewCount", viewCount);
	        		jsonArray.put(searchResult);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return jsonArray;
	}
	
	public List<SearchResult> getMostViewedByCategoryList(long maxResults, String videoCategoryId) {
		List<SearchResult> searchResultList = new ArrayList<SearchResult>();
		try {
			YouTube.Search.List search = youtube.search().list("snippet");
			search.setKey(apiKey);
			search.setOrder("viewCount");
			search.setVideoCategoryId(videoCategoryId);
			search.setMaxResults(maxResults);
			search.setType("video");
			
	        SearchListResponse searchResponse = search.execute();
	        searchResultList = searchResponse.getItems();
	        
	        if(searchResultList != null) {   	
	        	for(SearchResult searchResult : searchResultList) {
	        		String videoId = searchResult.getId().getVideoId().toString();
	        		String viewCount = VideoUtil.getVideoViewCount(videoId);
	        		searchResult.put("viewCount", viewCount);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return searchResultList;
	}

	public JSONArray getMostViewedByKeyword(long maxResults, String keyword) {
        JSONArray jsonArray = new JSONArray();
		try {
			YouTube.Search.List search = youtube.search().list("id,snippet");
			search.setKey(apiKey);
			search.setOrder("viewCount");
			search.setQ(keyword);
			search.setMaxResults(maxResults);
			search.setType("video");
			
			SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for(SearchResult result : searchResultList) {
                	String videoId = result.getId().getVideoId().toString();
                	String viewCount = VideoUtil.getVideoViewCount(videoId);
                	result.put("viewCount", viewCount);
                	jsonArray.put(result);
                }
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	public JSONArray getMostViewedByMultipleCategories(long maxResults, List<String> categoryIds) {
		List<SearchResult> topVideos = getMostViewedFromMultipleCategoriesUnsorted(maxResults, categoryIds);
		JSONArray sortedTopVideos = VideoUtil.boonSortVideosByViewCount(topVideos);
		JSONArray sortedTopVideosFinal = new JSONArray();
		for(int i = 0; i<maxResults; i++) {
			sortedTopVideosFinal.put(sortedTopVideos.get(i));
		}
		return sortedTopVideosFinal;
	}

	private List<SearchResult> getMostViewedFromMultipleCategoriesUnsorted(long maxResults, List<String> categoryIds) {
		List<SearchResult> topVideos = new ArrayList<SearchResult>();
		for(int i = 0; i<categoryIds.size(); i++) {
			String currentCategoryId = categoryIds.get(i);
			List<SearchResult> temp = getMostViewedByCategoryList(maxResults, currentCategoryId);
			if(temp != null) {
				for(int j = 0; j<temp.size(); j++) {
					topVideos.add(temp.get(j));
				}
			}
		}
		return topVideos;
	}
	

}
