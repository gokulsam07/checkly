package rest.core;

import static io.restassured.RestAssured.given;

import java.io.FileReader;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

public class RestController {

	private static final String baseURI = "https://api.checklyhq.com/v1/";
	private static final String accept = "application/json";
	private static final String content_type = "application/json";
	private static final String auth = "Bearer cu_caae20a7e30e48948dc62381e2936a0d";
	private static final String account_Id = "1bb0c953-2795-49a4-80f9-ef77b75960a6";
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
	    return res;
	}
	@SneakyThrows //not useful
	public boolean validateResponse(String actual,String xpectedResPath) {
		JSONObject actualResponse = new JSONObject(actual);
		JSONObject expectedResponse = new JSONObject(new JSONTokener(new FileReader(xpectedResPath)));
		return (expectedResponse.similar(actualResponse))?true:false;
	}
}