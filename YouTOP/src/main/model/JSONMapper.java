package model;

import com.google.gson.Gson;

public class JSONMapper {
		
	public static GVideo mapJSONtoGVideo(String json) {
		return new Gson().fromJson(json, GVideo.class);
	}
}
