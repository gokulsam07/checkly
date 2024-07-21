package checkly.tests.ui.dashboards;

import org.testng.annotations.Test;

import checkly.tests.ui.base.LoggedInBase;
import lombok.SneakyThrows;

import static org.assertj.core.api.Assertions.*;
import pages.DashboardsPage;
import pages.HomePage;
import ui.core.Page;

public class DashboardTest extends LoggedInBase {

	@Test @SneakyThrows
	public void createAndValidateDashboard() {
		new HomePage().clickSidePanelMenu("Dashboards");
		assertThat(new DashboardsPage().validateEmptyScreen()).isTrue();
		new checkly.tests.api.dashboards.DashboardTest().deleteAnyExistingDashboard();
		Thread.sleep(1000);
		Page.refresh();
		new checkly.tests.api.dashboards.DashboardTest()._1createBoard();
		Thread.sleep(1000);
		Page.refresh();
		assertThat(new DashboardsPage().validateDashBoard("Gokul's Dashboard")).isTrue();
	}
}
