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
public class DashboardAPITest {
	private static String path = null;
	private String dashboardId ="";
	static {
		path = System.getProperty("user.dir") + "/src/test/resources/data/api/";
	}

	@Test(groups= {"api","smoke"})
	public void tc001_createBoard() {
		String data = Stringify.JSON(path+"dashboard/create-dashboard.json");
		Response res = new RestController().post("dashboards", data.toString(), null);
		log.info(res.body().asPrettyString());
		assertThat(res.statusCode()).isEqualTo(201);
		dashboardId = res.jsonPath().getString("dashboardId");
		res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(path+"dashboard/create-response-schema.json")));
	}
	
	@Test
	public void tc002_createBoardAgain() {
		String data = Stringify.JSON(path+"dashboard/create-dashboard.json");
		Response res = new RestController().post("dashboards", data.toString(), null);
		log.info(res.body().asString());
		assertThat(res.statusCode()).isEqualTo(402);
		assertThat(res.body().asPrettyString()).contains("Payment Required");
	}
	
	@Test
	public void tc003_getDataBoardWithId() {
		HashMap<String,String> hm =  new HashMap<>();
		hm.put("dashboardId", dashboardId);
		Response res = new RestController().get("dashboards/{dashboardId}",hm);
		log.info(res.body().asPrettyString());
		res.then().statusCode(200).body("header",equalTo("Gokul's Dashboard")).body("dashboardId", equalTo(dashboardId));
	}
	@Test
	public void tc004_updateDashboard() {
		HashMap<String,String> hm =  new HashMap<>();
		hm.put("dashboardId", dashboardId);
		String data = Stringify.JSON(path+"dashboard/update-dashboard.json");
		Response res = new RestController().put("dashboards/{dashboardId}",data.toString(),hm);
		res.then().statusCode(200).body("description",equalTo("Edited the description to test"));
	}
	
	@Test(groups= {"api","smoke"})
	public void tc005_deleteDashboard() {
		HashMap<String,String> hm =  new HashMap<>();
		hm.put("dashboardId", dashboardId);
		Response res = new RestController().delete("dashboards/{dashboardId}",hm);
		res.then().statusCode(204);
	}
	@Test(enabled=false)
	public void deleteAnyExistingDashboard() {
		HashMap<String,String> hm =  new HashMap<>();
		String deleteId = new RestController().get("dashboards",null).jsonPath().getString("dashboardId").replace("[", "").replace("]", "");
		hm.put("deleteId",deleteId);
		Response res1 = new RestController().delete("dashboards/{deleteId}",hm);
		System.out.println(res1.body().asPrettyString());
		if(res1.statusCode()==204) {
			log.info("Dashboard deleted");
		}else {
			log.info("There is no dashboard to delete");
		}
	}
}
