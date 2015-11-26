package services;

import static org.boon.sort.Sorting.sortDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	private String apiKey = ConfigUtil.getAPIKey();
	
	public static void main(String[] args) {
		SearchMostViewedService ss = new SearchMostViewedService();
		List<String> cats = new ArrayList<String>();
		cats.add("1");
		cats.add("10");
		ss.getMostViewedByMultipleCategories(5, cats);
		System.out.println("\n"+ss.getMostViewedAll(1).get(0).getViewCount());
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
	
	public List<GVideo> getMostViewedByCategory(long maxResults, String videoCategoryId) {
	    List<GVideo> videoList = new ArrayList<GVideo>();
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
	        		GVideo gvideo = JSONMapper.mapJSONtoGVideo(searchResult.toString());
	        		videoList.add(gvideo);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return videoList;
	}

	public List<GVideo> getMostViewedByMultipleCategories(long maxResults, List<String> categoryIds) {
		List<GVideo> topVideos = getMostViewedFromMultipleCategoriesUnsorted(maxResults, categoryIds);
		List<GVideo> sortedTopVideosFinal = new ArrayList<GVideo>();
		sortDesc(topVideos, "viewCount");
		for(int i = 0 ; i<maxResults; i++) {
			sortedTopVideosFinal.add(topVideos.get(i));
		}
		return sortedTopVideosFinal;
	}
	
	public List<GVideo> getMostViewedByKeyword(long maxResults, String keyword) {
        List<GVideo> videoList = new ArrayList<GVideo>();
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
	        		GVideo gvideo = JSONMapper.mapJSONtoGVideo(result.toString());
                	videoList.add(gvideo);
                }
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoList;
	}

	private List<GVideo> getMostViewedFromMultipleCategoriesUnsorted(long maxResults, List<String> categoryIds) {
		List<GVideo> topVideos = new ArrayList<GVideo>();
		for(int i = 0; i<categoryIds.size(); i++) {
			String currentCategoryId = categoryIds.get(i);
			List<GVideo> temp = getMostViewedByCategory(maxResults, currentCategoryId);
			if(temp != null) {
				for(int j = 0; j<temp.size(); j++) {
					topVideos.add(temp.get(j));
				}
			}
		}
		return topVideos;
	}
	

}
