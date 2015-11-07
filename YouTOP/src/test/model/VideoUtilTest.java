package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VideoUtilTest {

	
	@Test
	public void testGetVideoViewCount() {
		String actualViewCount = VideoUtil.getVideoViewCount("o4_1hS1aIC8");
		assertNotNull(actualViewCount);
	}
}
