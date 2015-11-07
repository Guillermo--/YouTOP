package services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import model.Util;
import model.GVideo;


public class SearchMostViewedService {

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static YouTube youtube;
	private static String apiKey = Util.getAPIKey();
	
	public static void main(String[] args) {
		SearchMostViewedService ss = new SearchMostViewedService();
		List<String> cats = new ArrayList<String>();
		cats.add("10");
		cats.add("1");
		System.out.println(ss.getMostViewedByMultipleCategories(2, cats).toString(5));

	}
	
	public SearchMostViewedService(){
		youtubeInit();
	}
	
	public JSONArray getMostViewedAll(long maxResults) {
		JSONArray jsonArray = new JSONArray();
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
	        		String viewCount = GVideo.getVideoViewCount(videoId);
	        		searchResult.put("viewCount", viewCount);
	        		jsonArray.put(searchResult);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return jsonArray;
		
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
	        		String viewCount = GVideo.getVideoViewCount(videoId);
	        		searchResult.put("viewCount", viewCount);
	        		jsonArray.put(searchResult);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return jsonArray;
	}
	
	public List<SearchResult> getMostViewedByCategorySearchList(long maxResults, String videoCategoryId) {
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
	        		String viewCount = GVideo.getVideoViewCount(videoId);
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
                	String viewCount = GVideo.getVideoViewCount(videoId);
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
		JSONArray sortedTopVideos = sortVideosByViewCount(topVideos);
		return sortedTopVideos;
	}

	private JSONArray sortVideosByViewCount(List<SearchResult> topVideos) {
		JSONArray sortedTopVideos = new JSONArray();
		for(int i = 0; i<topVideos.size(); i++) {
			SearchResult currentObject = topVideos.get(i);
			if(sortedTopVideos.length() < 1) {
				sortedTopVideos.put(currentObject);
			}
			else {
				long currentViewCount = Long.parseLong(currentObject.get("viewCount").toString());
				for(int j = 0; j<sortedTopVideos.length(); j++) {
					JSONObject iteratingSearchResult = (JSONObject) sortedTopVideos.get(j);
					long iteratingViewCount = Long.parseLong((iteratingSearchResult.getString("viewCount")));
					if(currentViewCount > iteratingViewCount) {
						sortedTopVideos.put(j, currentObject);
					}
					else if (j+1 == sortedTopVideos.length()) {
						sortedTopVideos.put(currentObject);
						break;
					}
				}
			}
		}
		return sortedTopVideos;
	}

	private List<SearchResult> getMostViewedFromMultipleCategoriesUnsorted(long maxResults, List<String> categoryIds) {
		List<SearchResult> topVideos = new ArrayList<SearchResult>();
		for(int i = 0; i<categoryIds.size(); i++) {
			String currentCategoryId = categoryIds.get(i);
			List<SearchResult> temp = getMostViewedByCategorySearchList(maxResults, currentCategoryId);
			if(temp != null) {
				for(int j = 0; j<temp.size(); j++) {
					topVideos.add(temp.get(j));
				}
			}
		}
		return topVideos;
	}
	
	private static void youtubeInit() {
		if(youtube == null) {
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
		        public void initialize(HttpRequest request) throws IOException {}
		    }).setApplicationName("YouTOP").build();
		}
	}

	
}
