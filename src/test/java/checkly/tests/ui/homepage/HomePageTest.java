package checkly.tests.ui.homepage;


import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Test;

import checkly.tests.ui.base.LoggedInBaseTest;
import pages.HomePage;

public class HomePageTest extends LoggedInBaseTest {
	String[] menuList = new String[] {"Home","Heartbeats","Test sessions","Events","Alerts","Dashboards","Reporting","Invite account members"};
	@Test
	public void validateMenu() {
		assertThat(new HomePage().validatePanelIsOpenWithMenu(menuList)).isTrue();
	}
}
