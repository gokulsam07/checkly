package test.utils;

import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Stringify {
	public static String JSON(String filePath) {
		JSONObject jsonData = null;
		try {
			jsonData = new JSONObject(new JSONTokener(new FileReader(filePath)));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return jsonData.toString();
	}
}
