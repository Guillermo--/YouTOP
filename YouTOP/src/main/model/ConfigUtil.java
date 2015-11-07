package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	
	private static String propertiesFile = "config.properties";

	public static String getAPIKey(){
		String apiKey = "";
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(propertiesFile);
			prop.load(input);
			apiKey = prop.getProperty("apiKey");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return apiKey;
	}
}
