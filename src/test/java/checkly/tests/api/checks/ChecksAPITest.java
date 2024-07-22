package checkly.tests.api.checks;

import java.io.File;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import com.jayway.jsonpath.JsonPath;
import java.util.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import rest.core.RestController;
import test.utils.Stringify;

public class ChecksAPITest {

	private static String path = null;
	private List<Map<String, String>> checkIds = null;
	private String checkAPIId;
	private String checkBrowserId;
	static {
		path = System.getProperty("user.dir") + "/src/test/resources/data/api/";
	}

	@Test
	public void _1getAllChecks() {
		Response res = new RestController().get("checks", null);
		res.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File(path + "checks/all-checks-schema.json")));
		checkIds = JsonPath.read(res.asPrettyString(), "$.[*].['id', 'name']");
		System.err.println(checkIds);
	}

	@Test
	public void _2getCheckUsingId() {
		_1getAllChecks();
		HashMap<String, String> hm = new HashMap<>();
		Map<String, String> tmpVal = checkIds.get(0);
		String id = tmpVal.get("id");
		String name = tmpVal.get("name");
		hm.put("checkId", id);
		Response res = new RestController().get("checks/{checkId}", hm);
		res.then().assertThat().body("name", equalTo(name));
	}

	@Test
	public void _3createAPICheck() {
		String testName = "Sample API Check";
		String data = Stringify.JSON(path + "checks/create-api-check.json");
		Response res = new RestController().post("checks/api", data, null);
		res.then().assertThat().statusCode(201);
		res.then().assertThat().body("name", equalTo(testName));
		res.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File(path + "checks/creation-response-schema.json")));
		checkAPIId = res.jsonPath().getString("id");
		System.err.println(checkAPIId);
	}

	@Test
	public void _4createBrowserCheck() {
		String testName = "Sample Browser Check";
		String data = Stringify.JSON(path + "checks/create-browser-check.json");
		Response res = new RestController().post("checks/browser", data, null);
		res.then().assertThat().statusCode(201);
		res.then().assertThat().body("name", equalTo(testName));
		res.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File(path + "checks/create-browser-schema.json")));
		checkBrowserId = res.jsonPath().getString("id");
		System.err.println(checkBrowserId);
	}

	@Test
	public void _5updateAPICheck() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("id", checkAPIId);
		String testName = "Edited API Check";
		String data = Stringify.JSON(path + "checks/update-api.json");
		Response res = new RestController().put("checks/api/{id}", data, hm);
		res.then().assertThat().statusCode(200);
		res.then().assertThat().body("name", equalTo(testName));
	}

	@Test
	public void _6UpdateBrowserCheck() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("id", checkBrowserId);
		String testName = "Edited Browser Check";
		String data = Stringify.JSON(path + "checks/update-browser.json");
		Response res = new RestController().put("checks/browser/{id}", data, hm);
		res.then().assertThat().statusCode(200);
		res.then().assertThat().body("name", equalTo(testName));
	}

	@Test
	public void _7deleteAPICheck() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("id", checkAPIId);
		Response res = new RestController().delete("checks/{id}", hm);
		res.then().statusCode(204);
	}

	@Test
	public void _8deleteBrowserCheck() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("id", checkBrowserId);
		Response res = new RestController().delete("checks/{id}", hm);
		res.then().statusCode(204);
	}

	@Test(enabled = false)
	public void deleteCheckAPI(String id) {
		checkAPIId = id;
		_7deleteAPICheck();

	}

}
