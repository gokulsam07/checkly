package checkly.tests.ui.reporting;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
import checkly.tests.ui.base.LoggedInBaseTest;
import pages.HomePage;
import pages.ReportingPage;

public class ReportingTest extends LoggedInBaseTest {

	//@Test
	public void tc001_validateReportingPageTest() {
		new HomePage().clickSidePanelMenu("Reporting");
		assertThat(new ReportingPage().validate()).as("Reporting page is not as expected").isTrue();
	}

	//@Test (groups= {"ui","smoke","sample"})
	public void tc002_downloadReportAsCSVandValidateDownload() {
		String fileName = "checkly_export.csv";
		new HomePage().clickSidePanelMenu("Reporting");
		assertThat(new ReportingPage().clickDownload().validateDownloadedFile(fileName)).isTrue();
	}

	@Test
	public void tc003_validateFilterAndTags() {
		List<String> filter = List.of("Browser");
		List<String> tags = List.of("chrome");
		List<String> reports = List.of("FB API Check", "Instagram API Check", "https://playwright.dev");
		new HomePage().clickSidePanelMenu("Reporting");
		boolean isTrue = new ReportingPage().applyFilter(filter).applyTag(tags).validateAvailableListOfReport(reports);
		boolean isTruthy = new ReportingPage().clearAllFilter().validateAvailableListOfReport(reports);
		assertThat(isTrue && isTruthy).isTrue();
	}
}
