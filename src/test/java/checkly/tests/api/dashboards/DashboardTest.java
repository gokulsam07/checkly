package checkly.tests.api.dashboards;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import rest.core.RestController;
import test.utils.Stringify;
@Log
public class DashboardTest {
	private static String path = null;
	private String dashboardId ="";
	static {
		path = System.getProperty("user.dir") + "/src/test/resources/data";
	}

	@Test
	public void _1createBoard() {
		String data = Stringify.JSON(path + "/api/create-dashboard.json");
		Response res = new RestController().post("dashboards", data.toString(), null);
		log.info(res.body().asPrettyString());
	}
	
	@Test
	public void _2createBoardAgain() {
		String data = Stringify.JSON(path + "/api/create-dashboard.json");
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
	}
	
	

}
