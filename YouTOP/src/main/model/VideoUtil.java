package model;

import static org.boon.sort.Sorting.sort;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
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
		
	public static JSONArray boonSortVideosByViewCount(List<SearchResult> videos) {
		sort(videos, "viewCount");
		JSONArray topVideosJSON = new JSONArray(videos);
		return topVideosJSON;
	}
}
