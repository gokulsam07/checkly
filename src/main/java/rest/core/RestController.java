package rest.core;

import org.json.JSONObject;
import static io.restassured.RestAssured.*;

import java.util.Map;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

	public Response get(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
		RequestSpecification reqSpec = req;
		if (pathParams != null) {
			for (Map.Entry<String, String> entry : pathParams.entrySet()) {
				reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
			}
		}
		if (queryParams != null) {
			reqSpec = reqSpec.queryParams(queryParams);
		}
		return reqSpec.get(endpoint);
	}

	public Response post(String endpoint, JSONObject data, Map<String, String> pathParams,
			Map<String, String> queryParams) {
		RequestSpecification reqSpec = req;
		if (pathParams != null) {
			for (Map.Entry<String, String> entry : pathParams.entrySet()) {
				reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
			}
		}
		if (queryParams != null) {
			reqSpec = reqSpec.queryParams(queryParams);
		}
		return reqSpec.body(data).post(endpoint);
	}

	public Response delete(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
		RequestSpecification reqSpec = req;
		if (pathParams != null) {
			for (Map.Entry<String, String> entry : pathParams.entrySet()) {
				reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
			}
		}
		if (queryParams != null) {
			reqSpec = reqSpec.queryParams(queryParams);
		}
		return reqSpec.delete(endpoint);
	}

	public Response put(String endpoint, JSONObject data, Map<String, String> pathParams,
			Map<String, String> queryParams) {
		RequestSpecification reqSpec = req;
		if (pathParams != null) {
			for (Map.Entry<String, String> entry : pathParams.entrySet()) {
				reqSpec = reqSpec.pathParam(entry.getKey(), entry.getValue());
			}
		}
		if (queryParams != null) {
			reqSpec = reqSpec.queryParams(queryParams);
		}
		return reqSpec.body(data).put(endpoint);
	}
}