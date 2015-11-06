package model;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class GVideo {

	private static YouTube youtube;
	private static String apiKey = Util.getAPIKey();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	public static String getVideoViewCount(String videoId) {
		youtubeInit();
	    String viewCount = "";
		try {
			YouTube.Videos.List search = youtube.videos().list("statistics").setId(videoId).setKey(apiKey);
			VideoListResponse searchResponse = search.execute();
			if(searchResponse != null) {
				Video video = searchResponse.getItems().get(0);
				viewCount = video.getStatistics().getViewCount().toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return viewCount;
	}
	
	private static void youtubeInit() {
		if(youtube == null) {
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
		        public void initialize(HttpRequest request) throws IOException {}
		    }).setApplicationName("YouTOP").build();
		}
	}
}
