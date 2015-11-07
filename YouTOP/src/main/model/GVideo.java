package model;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class GVideo {

	private static YouTube youtube;
	private static String apiKey = Util.getAPIKey();

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
	
	public static JSONArray sortVideosByViewCount(List<SearchResult> topVideos) {
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
}
