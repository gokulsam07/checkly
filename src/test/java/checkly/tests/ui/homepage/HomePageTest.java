package checkly.tests.ui.homepage;

import org.testng.annotations.Test;
import com.codeborne.selenide.Selenide;
import static org.assertj.core.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import checkly.tests.api.checks.ChecksAPITest;
import checkly.tests.ui.base.LoggedInBaseTest;
import pages.HomePage;
import ui.core.URLClipper;

public class HomePageTest extends LoggedInBaseTest {
	private String id=null;
	String[] menuList = new String[] { "Home", "Heartbeats", "Test sessions", "Events", "Alerts", "Dashboards",
			"Reporting", "Invite account members" };

	@Test
	public void tc001_validateMenu() {
		assertThat(new HomePage().validatePanelIsOpenWithMenu(menuList)).isTrue();
	}

	@Test
	public void tc002_validateStatusOfItems() {
		List<String> passed = List.of("https://playwright.dev", "Check youtube", "Checkly", "FB API Check");
		List<String> failed = List.of("Instagram API Check", "OrangeHRM");
		boolean state1 = new HomePage().validateStatusAndItem("Passing", failed, passed);
		boolean state2 = new HomePage().validateStatusAndItem("Failing", passed, failed);
		assertThat(state1 && state2).isTrue();
	}

	 @Test (groups= {"ui","smoke"})
	public void tc003_validateFiltering() {
		String str = "check";
		List<String> filter = List.of("Passing", "Failing");
		List<String> tags = List.of("chrome", "api");
		List<String> visibleItems = List.of("Instagram API Check", "Check youtube", "Checkly","FB API Check");
		List<String> hiddenItems = List.of("https://playwright.dev", "OrangeHRM");
		assertThat(new HomePage().applyFilter(filter).applyTags(tags).search(str).validateResult(hiddenItems,
				visibleItems)).isTrue();

	}

	@Test (groups= {"ui","smoke"})
	public void tc004_createData() throws InterruptedException {
		new ChecksAPITest().tc003_createAPICheck();
		new ChecksAPITest().tc004_createBrowserCheck();
		Thread.sleep(5000);
		Selenide.refresh();
		List<String> items = List.of("Sample API Check", "Sample Browser Check");
		assertThat(new HomePage().validateResult(null, items)).isTrue();
	}

	@Test (groups= {"ui","smoke"})
	public void tc005_deleteDataIntegration() throws InterruptedException {
		List<String> items = List.of("Sample API Check");
		assertThat(new HomePage().validateResult(null, items)).isTrue();
		new HomePage().clickItem(items.get(0));
		HashMap<String, String> clippedVal = URLClipper.clip();
	    id = clippedVal.get("checks");
	    System.err.println(id);
		new ChecksAPITest().deleteCheckAPI(id);
		Thread.sleep(3000);
		Selenide.refresh();
		assertThat(new HomePage().validateResult(items, null)).isTrue();
	}

	@Test (groups= {"ui","smoke"})
	public void tc006_testMenu() {
		String menu="Delete";
		String subMenu ="Delete check";
		String item="Sample Browser Check";
		new HomePage().clickMenuForItem(menu,subMenu,item);
		assertThat(new HomePage().validateResult(List.of(item), null)).isTrue();
	}
}
