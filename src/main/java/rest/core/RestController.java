package rest.core;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;

import com.codeborne.selenide.Configuration;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import utils.Env;

public class RestController {

	private static final String baseURI = Configuration.baseUrl;
	private static final String accept = "application/json";
	private static final String content_type = "application/json";
	private static final String auth = Env.get("auth","AUTH_TOKEN");
	private static final String account_Id = Env.get("accId","ACC_ID");
	RequestSpecification req;

	public RestController() {
		req = given().baseUri(baseURI).accept(accept).contentType(content_type).header("Authorization", auth)
				.header("x-checkly-account", account_Id);
	}

	public Response get(String endpoint, Map<String, String> pathParams) {
	    RequestSpecification reqSpec = req;
	    if (pathParams != null) {
	        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
	            reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
	        }
	    }
	    Response res = reqSpec.get(endpoint);
	    System.out.println(res.asPrettyString());
	    return res;
	}

	public Response post(String endpoint, String data, Map<String, String> pathParams) {
	    RequestSpecification reqSpec = req;
	    if (pathParams != null) {
	        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
	            reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
	        }
	    }
	    Response res = reqSpec.body(data).post(endpoint);
	    System.out.println(res.asPrettyString());
	    return res;
	}

	public Response delete(String endpoint, Map<String, String> pathParams) {
	    RequestSpecification reqSpec = req;
	    if (pathParams != null) {
	        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
	            reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
	        }
	    }
	    Response res = reqSpec.delete(endpoint);
	    System.out.println(res.asPrettyString());
	    return res;
	}

	public Response put(String endpoint, String data, Map<String, String> pathParams) {
	    RequestSpecification reqSpec = req;
	    if (pathParams != null) {
	        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
	            reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
	        }
	    }
	    Response res =reqSpec.body(data).put(endpoint);
	    System.out.println(res.asPrettyString());
	    return res;
	}
	@SneakyThrows
	public void validateResponse(String actual, File expectedFile) {
	    try {
	        String expectedContent = FileUtils.readFileToString(expectedFile, "UTF-8").trim();
	        String actualContent = actual.trim();
	        Object expectedJson = new JSONTokener(expectedContent).nextValue();
	        Object actualJson = new JSONTokener(actualContent).nextValue();
	        if (expectedJson instanceof JSONArray && actualJson instanceof JSONObject) {
	            JSONArray expArray = (JSONArray) expectedJson;
	            if (expArray.length() == 1) {
	                expectedContent = expArray.get(0).toString();
	            }
	        } 
	        else if (actualJson instanceof JSONArray && expectedJson instanceof JSONObject) {
	            JSONArray actArray = (JSONArray) actualJson;
	            if (actArray.length() == 1) {
	                actualContent = actArray.get(0).toString();
	            }
	        }
	        JSONAssert.assertEquals(expectedContent, actualContent, JSONCompareMode.LENIENT);

	    } catch (AssertionError e) {
	        Assert.fail("JSON Mismatch Found:\n" + e.getMessage());
	    } catch (Exception e) {
	        Assert.fail("Validation logic crashed: " + e.getMessage());
	    }
	}
}