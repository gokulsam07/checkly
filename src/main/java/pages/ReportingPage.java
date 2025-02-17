package pages;

import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.util.List;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;

import ui.core.Time;

public class ReportingPage {

	public boolean validate() {
		return $x("//h2[normalize-space()='Reporting']").is(visible, Time.LOW);
	}

	public ReportingPage clickDownload() {
		$x("//span[normalize-space()='Download CSV']").shouldBe(visible, Time.LOW).click();
		return this;
	}

	@SneakyThrows
	public boolean validateDownloadedFile(String fileName) {
		Thread.sleep(5000);
		String path = System.getProperty("user.dir") + "\\build\\downloads";
		File[] folders = new File(path).listFiles();
		String recentDownload = folders[0] + "\\" + fileName;
		File dwnldFile = new File(recentDownload);
		dwnldFile.exists();
		boolean isPresent = dwnldFile.exists();
		if (isPresent) {
			dwnldFile.delete();
			folders[0].delete();
		}
		return isPresent;
	}

	public boolean validateAvailableListOfReport(List<String> report) {
		return report.stream().allMatch(e -> ($(".box-border span[title='" + e + "']")).is(hidden, Time.LOW));
	}

	public ReportingPage applyFilter(List<String> filters) {
		$(byText("Status")).shouldBe(visible, Time.LOW).click();
		for (String filter : filters) {
			SelenideElement ele = $(byText(filter));
			if (!ele.preceding(0).isSelected()) {
				ele.click();
			} else {
				System.out.println("Element is already selected");
			}
		}
		executeJavaScript("location.reload(true);");
		return this;
	}

	public ReportingPage applyTag(List<String> tags) {
		$(byText("Tags")).shouldBe(visible, Time.LOW).click();
		for (String tag : tags) {
			$("[role='listbox'] [title='" + tag + "']").scrollTo().click();
		}
		executeJavaScript("location.reload(true);");
		return this;
	}

	public ReportingPage clearAllFilter() {
		$x("//span[normalize-space()='Clear all filters']").shouldBe(visible, Time.LOW).click();
		return this;
	}
}
