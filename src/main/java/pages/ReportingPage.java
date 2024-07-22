package pages;

import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;

import static com.codeborne.selenide.Condition.*;
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

	@SneakyThrows
	public ReportingPage applyFilter(List<String> filterName) {
		$x("//span[normalize-space()='Filters']").shouldBe(visible, Time.LOW).click();
		for (String filter : filterName) {
			SelenideElement ele = $x("//label[contains(text(),'" + filter + "')]");
			if (!ele.$("input").isSelected()) {
				ele.click();
			} else {
				System.out.println("Element is already selected");
			}
		}
		Selenide.executeJavaScript("location.reload(true);");
		return this;
	}

	@SneakyThrows
	public ReportingPage applyTag(List<String> tags) {
		$x("//span[normalize-space()='Tags']").shouldBe(visible, Time.LOW).click();
		for (String tag : tags) {
			$("div[role='listbox'] article[title='" + tag + "']").click();
		}
		Selenide.executeJavaScript("location.reload(true);");
		return this;
	}

	public ReportingPage clearAllFilter() {
		$x("//span[normalize-space()='Clear all filters']").shouldBe(visible, Time.LOW).click();
		return this;
	}
}
