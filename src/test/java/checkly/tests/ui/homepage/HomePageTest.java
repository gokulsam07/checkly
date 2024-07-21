package checkly.tests.ui.homepage;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import checkly.tests.ui.base.LoggedInBase;
import pages.HomePage;

public class HomePageTest extends LoggedInBase {
	String[] menuList = new String[] {"Home","Heartbeats","Test sessions","Events","Alerts","Dashboards","Reporting","Invite account members"};
	@Test
	public void validateMenu() {
		assertThat(new HomePage().validatePanelIsOpenWithMenu(menuList)).isTrue();
	}
}
