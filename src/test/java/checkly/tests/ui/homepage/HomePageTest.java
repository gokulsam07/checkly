package checkly.tests.ui.homepage;

import org.testng.ITestContext;
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
	public void validateMenu() {
		assertThat(new HomePage().validatePanelIsOpenWithMenu(menuList)).isTrue();
	}

	 @Test
	public void validateStatusOfItems() {
		List<String> passed = List.of("https://playwright.dev", "Check youtube", "Checkly", "FB API Check");
		List<String> failed = List.of("Instagram API Check", "OrangeHRM");
		boolean state1 = new HomePage().validateStatusAndItem("Passing", failed, passed);
		boolean state2 = new HomePage().validateStatusAndItem("Failing", passed, failed);
		assertThat(state1 && state2).isTrue();
	}

	 @Test
	public void validateFiltering() {
		String str = "check";
		List<String> filter = List.of("Passing", "Failing");
		List<String> tags = List.of("chrome", "api");
		List<String> visibleItems = List.of("Instagram API Check", "Check youtube", "Checkly", "FB API Check");
		List<String> hiddenItems = List.of("https://playwright.dev", "OrangeHRM");
		assertThat(new HomePage().applyFilter(filter).applyTags(tags).search(str).validateResult(hiddenItems,
				visibleItems)).isTrue();

	}

	 @Test
	public void createData() throws InterruptedException {
		new ChecksAPITest()._3createAPICheck();
		new ChecksAPITest()._4createBrowserCheck();
		Thread.sleep(5000);
		Selenide.refresh();
		List<String> items = List.of("Sample API Check", "Sample Browser Check");
		assertThat(new HomePage().validateResult(null, items)).isTrue();
	}

	@Test
	public void deleteDataIntegration() throws InterruptedException {
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

	@Test
	public void testMenu() {
		String menu="Delete";
		String item="Sample Browser Check";
		new HomePage().clickMenuForItem(menu,menu,item);
		assertThat(new HomePage().validateResult(List.of(item), null)).isTrue();
	}
}
