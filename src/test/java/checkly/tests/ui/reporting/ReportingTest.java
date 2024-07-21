package checkly.tests.ui.reporting;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import checkly.tests.ui.base.LoggedInBase;
import pages.HomePage;
import pages.ReportingPage;

public class ReportingTest extends LoggedInBase {
	
	@Test
	public void validateReportingPageTest() {
		new HomePage().clickSidePanelMenu("Reporting");
		assertThat(new ReportingPage().validate()).as("Reporting page is not as expected").isTrue();
	}
}
