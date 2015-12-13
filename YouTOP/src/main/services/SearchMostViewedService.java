package services;

import static org.boon.sort.Sorting.sortDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import model.ConfigUtil;
import model.GSearch;
import model.JSONMapper;
import model.VideoUtil;
import model.YouTubeUtil;


public class SearchMostViewedService {

	private static YouTube youtube;
	private String apiKey = ConfigUtil.getAPIKey();
	
	public SearchMostViewedService(){
		youtube = YouTubeUtil.youtubeInit();
	}
	
	public List<GSearch> getMostViewedAll(long maxResults) {
		List<GSearch> videoList = new ArrayList<GSearch>();
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
	        		String defaultThumb = searchResult.getSnippet().getThumbnails().getDefault().getUrl().toString();
	        		searchResult.put("viewCount", viewCount);
	        		searchResult.getSnippet().getThumbnails().put("defaultUrl", defaultThumb);
	        		GSearch gvideo = JSONMapper.mapJSONtoGVideo(searchResult.toString());
	        		videoList.add(gvideo);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return videoList;
	}
	
	public List<GSearch> getMostViewedByCategory(long maxResults, String videoCategoryId) {
	    List<GSearch> videoList = new ArrayList<GSearch>();
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
	        		String defaultThumb = searchResult.getSnippet().getThumbnails().getDefault().getUrl().toString();
	        		searchResult.getSnippet().getThumbnails().put("defaultUrl", defaultThumb);
	        		GSearch gvideo = JSONMapper.mapJSONtoGVideo(searchResult.toString());
	        		videoList.add(gvideo);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return videoList;
	}

	public List<GSearch> getMostViewedByMultipleCategories(long maxResults, List<String> categoryIds) {
		List<GSearch> topVideos = getMostViewedFromMultipleCategoriesUnsorted(maxResults, categoryIds);
		List<GSearch> sortedTopVideosFinal = new ArrayList<GSearch>();
		sortDesc(topVideos, "viewCount");
		for(int i = 0 ; i<maxResults; i++) {
			sortedTopVideosFinal.add(topVideos.get(i));
		}
		return sortedTopVideosFinal;
	}
	
	public List<GSearch> getMostViewedByKeyword(long maxResults, String keyword) {
        List<GSearch> videoList = new ArrayList<GSearch>();
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
	        		String defaultThumb = result.getSnippet().getThumbnails().getDefault().getUrl().toString();
	        		result.getSnippet().getThumbnails().put("defaultUrl", defaultThumb);
	        		GSearch gvideo = JSONMapper.mapJSONtoGVideo(result.toString());
                	videoList.add(gvideo);
                }
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoList;
	}

	private List<GSearch> getMostViewedFromMultipleCategoriesUnsorted(long maxResults, List<String> categoryIds) {
		List<GSearch> topVideos = new ArrayList<GSearch>();
		for(int i = 0; i<categoryIds.size(); i++) {
			String currentCategoryId = categoryIds.get(i);
			List<GSearch> temp = getMostViewedByCategory(maxResults, currentCategoryId);
			if(temp != null) {
				for(int j = 0; j<temp.size(); j++) {
					topVideos.add(temp.get(j));
				}
			}
		}
		return topVideos;
	}
	

}
