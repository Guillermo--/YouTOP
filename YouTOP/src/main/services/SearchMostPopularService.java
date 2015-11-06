package services;

import java.io.IOException;
import java.util.List;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import model.Util;

public class SearchMostPopularService {
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static YouTube youtube;
	private static String apiKey = Util.getAPIKey();
	
	public SearchMostPopularService(){
		youtubeInit();
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
	
	private static void youtubeInit() {
		if(youtube == null) {
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
		        public void initialize(HttpRequest request) throws IOException {}
		    }).setApplicationName("YouTOP").build();
		}
	}
}
