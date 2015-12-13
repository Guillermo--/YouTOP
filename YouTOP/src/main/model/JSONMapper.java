package model;

import com.google.gson.Gson;

public class JSONMapper {
		
	public static GSearch mapJSONtoGVideo(String json) {
		return new Gson().fromJson(json, GSearch.class);
	}
	
	public static GVideo mapJSONtoGSearch(String json) {
		return new Gson().fromJson(json, GVideo.class);
	}
}
