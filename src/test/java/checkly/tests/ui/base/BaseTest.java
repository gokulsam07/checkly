package checkly.tests.ui.base;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.screenshot;

import java.io.File;
import java.net.MalformedURLException;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

public class BaseTest {
	@BeforeSuite
	public void cleanUpDir() throws MalformedURLException {
		String filesLoc = System.getProperty("user.dir") + File.separator + "build" + File.separator
				+ "downloads";
		try {
			FileUtils.forceDelete(new File(filesLoc));
		} catch (Exception e) {
			System.out.println("Unable to delete the folder or folder not found");
		}
	}

	@BeforeMethod (groups= {"ui","smoke"})
	public void launch() {
		Configuration.screenshots=false;
		open("");
		WebDriverRunner.getWebDriver().manage().window().maximize();
	}

	@AfterMethod(groups= {"ui","smoke"})
	public void tearDown(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (result.getStatus() == 2) {
			String relativeLoc = result.getMethod().getMethodName();
			screenshot(relativeLoc);
			String ss = System.getProperty("user.dir") + File.separator + "build" + File.separator + "reports"
					+ File.separator + "tests" + File.separator + relativeLoc + ".png";
			Reporter.log("<a href='" + ss + "'><img src='file://" + ss + "' width='822' height='404'/></a>");
		}
		closeWebDriver();
	}

}
