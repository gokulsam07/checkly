package api;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import rest.core.JSONIterable;
import rest.core.RestController;

public class EnvVarAPITest {
	private RestController api = new RestController();
	
	@BeforeClass
	public void prugeData() throws IOException {
		Response res = api.get("variables", null);
		List<Map<String, String>> variables = res.jsonPath().getList("$");
		if (variables != null && !variables.isEmpty()) {
		    variables.stream()
		        .map(var -> var.get("key"))        
		        .filter(key -> key != null)        
		        .forEach(key -> {
		            System.out.println("Deleting key: " + key);
		            api.delete("variables/{varId}", Map.of("varId", key)).then().statusCode(204);
		        });
		} else {
		    System.out.println("No variables found. Skipping deletion.");
		}
	}

	@BeforeMethod
	public void setup() {
		api = new RestController();
	}

	@Test
	public void tc001_createEnvVar() throws IOException {
		File data = new File("src/test/resources/data/api/create-env-var.json");
		for (Object payload : JSONIterable.convert(data)) {
			Response res = api.post("variables", payload.toString(), null);
			res.then().statusCode(201);
			
		}
	}

	@Test
	public void tc002_updateEnvVar() throws IOException {
		File data = new File("src/test/resources/data/api/create-env-var.json");
		Object payload = JSONIterable.convert(data).getJSONObject(0).put("secret", true).put("locked", true);
		String key = JSONIterable.convert(data).getJSONObject(0).get("key").toString();
		Response res =api.put("variables/{varId}", payload.toString(), Map.of("varId", key));
		res.then().statusCode(200);
	}

	@Test
	public void tc003_getEnvVarbyKey() throws IOException {
		File data = new File("src/test/resources/data/api/updated-env-var.json");
		String key = JSONIterable.convert(data).getJSONObject(0).get("key").toString();
		Response res = api.get("variables/{varId}", Map.of("varId", key));
		api.validateResponse(res.asPrettyString(), data);
	}

	@Test
	public void tc004_getAllEnvVar() throws IOException {
		File data = new File("src/test/resources/data/api/updated-full-var.json");
		Response res = api.get("variables", null);
		res.then().statusCode(200);
		api.validateResponse(res.asPrettyString(), data);
	}

	@Test
	public void tc005_deleteEnvVar() throws IOException {
		File data = new File("src/test/resources/data/api/create-env-var.json");
		JSONArray json = JSONIterable.convert(data);
		for (int i = 0; i < json.length(); i++) {
			String key = json.getJSONObject(i).get("key").toString();
			Response res = api.delete("variables/{varId}", Map.of("varId", key));
			res.then().statusCode(204);
		}
	}
}