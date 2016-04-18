package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FirefoxPath {

	public static String firefoxPath() {
		Properties prop = new Properties();
		InputStream input = null;
		String path;
		
			try {
				input = FirefoxPath.class.getClassLoader().getResourceAsStream("firefox.properties");
				prop.load(input);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		path = prop.getProperty("firefox.path");
		return path;
	}
}
