package model;

import java.io.IOException;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class VideoUtil {

	private static YouTube youtube;
	private static String apiKey = ConfigUtil.getAPIKey();

	public static String getVideoViewCount(String videoId) {
		youtube = YouTubeUtil.youtubeInit();
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
		
}
