package services;

import java.io.IOException;
import java.util.List;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import model.ConfigUtil;
import model.YouTubeUtil;

public class SearchMostPopularService {
	private static YouTube youtube;
	private static String apiKey = ConfigUtil.getAPIKey();
	
	public SearchMostPopularService(){
		youtube = YouTubeUtil.youtubeInit();
	}
	
	public void getMostPopularByCategory(long maxResults, String videoCategoryId) {	    
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
	        		System.out.println(videoResult.toPrettyString());
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
