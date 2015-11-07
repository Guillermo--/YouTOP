package model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.api.services.youtube.YouTube;

public class YouTubeUtilTest {

	@Test
	public void testYoutubeInit() {
		YouTube youtube = YouTubeUtil.youtubeInit();
		assertNotNull(youtube);
	}

}
