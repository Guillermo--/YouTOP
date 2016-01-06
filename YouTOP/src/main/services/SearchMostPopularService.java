package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.boon.sort.Sorting.sortDesc;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import model.ConfigUtil;
import model.GVideo;
import model.JSONMapper;
import model.VideoUtil;
import model.YouTubeUtil;

public class SearchMostPopularService {
	private static YouTube youtube;
	private static String apiKey = ConfigUtil.getAPIKey();
	
	public SearchMostPopularService(){
		youtube = YouTubeUtil.youtubeInit();
	}
	
	public List<GVideo> getMostPopularAll(long maxResults) {	    
        List<GVideo> videoList = new ArrayList<GVideo>();
		try {
			YouTube.Videos.List list = youtube.videos().list("snippet");
			list.setChart("mostPopular");
			list.setMaxResults(maxResults);
			list.setKey(apiKey);
			
			VideoListResponse videoResponse = list.execute();
			List<Video> videoResponseList = videoResponse.getItems();
			
	        if(videoResponseList != null) {   	
	        	for(Video videoResult : videoResponseList) {
	        		String videoId = videoResult.getId().toString();
	        		String viewCount = VideoUtil.getVideoViewCount(videoId);
	        		String defaultThumb = videoResult.getSnippet().getThumbnails().getDefault().getUrl().toString();
	        		videoResult.put("viewCount", viewCount);
	        		videoResult.getSnippet().getThumbnails().put("defaultUrl", defaultThumb);
	        		GVideo gvideo = JSONMapper.mapJSONtoGSearch(videoResult.toString());
	        		videoList.add(gvideo);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoList;
	}
	
	public List<GVideo> getMostPopularByCategory(long maxResults, String videoCategoryId) {	    
        List<GVideo> videoList = new ArrayList<GVideo>();
		try {
			YouTube.Videos.List list = youtube.videos().list("snippet");
			list.setChart("mostPopular");
			list.setMaxResults(maxResults);
			list.setVideoCategoryId(videoCategoryId);
			list.setKey(apiKey);
			
			VideoListResponse videoResponse = list.execute();
			List<Video> videoResponseList = videoResponse.getItems();
			
	        if(videoResponseList != null) {   	
	        	for(Video videoResult : videoResponseList) {
	        		String videoId = videoResult.getId().toString();
	        		String viewCount = VideoUtil.getVideoViewCount(videoId);
	        		videoResult.put("viewCount", viewCount);
	        		String defaultThumb = videoResult.getSnippet().getThumbnails().getDefault().getUrl();
	        		videoResult.getSnippet().getThumbnails().put("defaultUrl", defaultThumb);
	        		GVideo gvideo = JSONMapper.mapJSONtoGSearch(videoResult.toString());
	        		videoList.add(gvideo);
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoList;
	}
	
	public List<GVideo> getMostPopularByMultipleCategories(long maxResults, List<String> categoryIds){
		List<GVideo> topVideos = getMostPopularFromMultipleCategoriesUnsorted(maxResults, categoryIds);
		List<GVideo> sortedTopVideosFinal = new ArrayList<GVideo>();
		sortDesc(topVideos, "viewCount");
		for(int i = 0; i<maxResults; i++) {
			sortedTopVideosFinal.add(topVideos.get(i));
		}
		return sortedTopVideosFinal;
	}
	
	private List<GVideo> getMostPopularFromMultipleCategoriesUnsorted(long maxResults, List<String> categoryIds) {
		List<GVideo> topVideos = new ArrayList<GVideo>();
		for(int i = 0; i<categoryIds.size(); i++) {
			String currentCategoryId = categoryIds.get(i);
			List<GVideo> temp = getMostPopularByCategory(maxResults, currentCategoryId);
			if(temp != null) {
				for(int j = 0; j<temp.size(); j++){
					topVideos.add(temp.get(j));
				}
			}
		}
		return topVideos;
	}
}
