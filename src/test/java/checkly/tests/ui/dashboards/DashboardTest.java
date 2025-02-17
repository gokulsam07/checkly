package checkly.tests.ui.dashboards;

import org.testng.annotations.Test;

import checkly.tests.ui.base.LoggedInBaseTest;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import static org.assertj.core.api.Assertions.*;
import pages.DashboardsPage;
import pages.HomePage;
import ui.core.Page;
@Log

public class DashboardTest extends LoggedInBaseTest {

	@Test(groups= {"ui","smoke"})
	@SneakyThrows
	public void tc001_createAndValidateDashboard() {
		new HomePage().clickSidePanelMenu("Dashboards");
		new checkly.tests.api.dashboards.DashboardAPITest().deleteAnyExistingDashboard();
		Thread.sleep(1000);
		Page.refresh();
		boolean isEmpty = new DashboardsPage().validateEmptyScreen();
		if(isEmpty) {
			new checkly.tests.api.dashboards.DashboardAPITest().tc001_createBoard();
			Thread.sleep(1000);
			Page.refresh();
			assertThat(new DashboardsPage().validateDashBoard("Gokul's Dashboard")).isTrue();
		}else {
			log.info("The test did not execute as expected, so failing it");
			assertThat(true).isFalse();
		}
		
	}
	
	@Test
	public void tc002_validateUpdatedValue() {
		String desc= "Sample dashboard created for automation check";
		new HomePage().clickSidePanelMenu("Dashboards");
		new DashboardsPage().clickDashboard("Gokul's Dashboard");
		new DashboardsPage().validateDescription(desc);
	}
	
	@Test (groups= {"ui","smoke"})
	@SneakyThrows 
	public void tc003_validateDeletion() {
		new HomePage().clickSidePanelMenu("Dashboards");
		boolean isVisible = new DashboardsPage().validateDashBoard("Gokul's Dashboard");
		if(isVisible) {
			new checkly.tests.api.dashboards.DashboardAPITest().deleteAnyExistingDashboard();
			Page.refresh();
			Thread.sleep(1000);
			assertThat(new DashboardsPage().validateEmptyScreen()).isTrue();
		}else {
			log.info("The test did not execute as expected, so failing it");
			assertThat(true).isFalse();
		}
	}
}
