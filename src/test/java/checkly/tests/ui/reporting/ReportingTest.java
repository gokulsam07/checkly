package checkly.tests.ui.reporting;


import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Test;

import checkly.tests.ui.base.LoggedInBaseTest;
import pages.HomePage;
import pages.ReportingPage;
@Test(groups = {"ui"})
public class ReportingTest extends LoggedInBaseTest {
	
	@Test
	public void validateReportingPageTest() {
		new HomePage().clickSidePanelMenu("Reporting");
		assertThat(new ReportingPage().validate()).as("Reporting page is not as expected").isTrue();
	}
}
