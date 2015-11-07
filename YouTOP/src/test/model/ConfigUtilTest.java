package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigUtilTest {
	
	@Test
	public void testGetApiKey() {
		String apiKey = ConfigUtil.getAPIKey();
		assertNotNull(apiKey);
		assertFalse(apiKey.isEmpty());
	}

}
