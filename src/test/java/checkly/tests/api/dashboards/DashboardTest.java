package checkly.tests.api.dashboards;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import rest.core.RestController;
import test.utils.Stringify;
@Log
public class DashboardTest {
	private static String path = null;
	private String dashboardId ="";
	static {
		path = System.getProperty("user.dir") + "/src/test/resources/data/api/";
	}

	@Test
	public void _1createBoard() {
		String data = Stringify.JSON(path+"dashboard/create-dashboard.json");
		Response res = new RestController().post("dashboards", data.toString(), null);
		log.info(res.body().asPrettyString());
		assertThat(res.statusCode()).isEqualTo(201);
		dashboardId = res.jsonPath().get("dashboardId").toString();
		res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(path+"dashboard/create-response-schema.json")));
	}
	
	@Test
	public void _2createBoardAgain() {
		String data = Stringify.JSON(path+"dashboard/create-dashboard.json");
		Response res = new RestController().post("dashboards", data.toString(), null);
		log.info(res.body().asString());
		assertThat(res.statusCode()).isEqualTo(402);
		assertThat(res.body().asPrettyString()).contains("Payment Required");
	}
	
	@Test
	public void _3getDataBoardWithId() {
		HashMap<String,String> hm =  new HashMap<>();
		hm.put("dashboardId", dashboardId);
		Response res = new RestController().get("dashboards/{dashboardId}",hm);
		log.info(res.body().asPrettyString());
		res.then().statusCode(200).body("header",equalTo("Gokul's Dashboard")).body("dashboardId", equalTo(dashboardId));
	}
	@Test
	public void _4updateDashboard() {
		HashMap<String,String> hm =  new HashMap<>();
		hm.put("dashboardId", dashboardId);
		String data = Stringify.JSON(path+"dashboard/update-dashboard.json");
		Response res = new RestController().put("dashboards/{dashboardId}",data.toString(),hm);
		res.then().statusCode(200).body("description",equalTo("Edited the description to test"));
	}
	
	@Test
	public void deleteDashboard() {
		HashMap<String,String> hm =  new HashMap<>();
		hm.put("dashboardId", dashboardId);
		Response res = new RestController().delete("dashboards/{dashboardId}",hm);
		res.then().statusCode(204);
	}
}
