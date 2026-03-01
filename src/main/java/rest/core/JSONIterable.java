package rest.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

public class JSONIterable {
	
	public static JSONArray convert(File filePath) throws IOException {
		String jsonString = FileUtils.readFileToString(filePath, StandardCharsets.UTF_8);
		return new JSONArray(jsonString);
	}

}
