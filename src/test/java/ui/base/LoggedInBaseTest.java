package ui.base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;

import pages.LoginPage;
import test.utils.Prop;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

public class LoggedInBaseTest {
	String uname = Prop.get("login.properties", "username");
	String pwd = Prop.get("login.properties", "password");
	@BeforeSuite(groups= {"ui","smoke"})
	public void cleanUpDir() throws MalformedURLException {
		String filesLoc = System.getProperty("user.dir") + File.separator + "build" + File.separator
				+ "downloads";
		try {
			FileUtils.forceDelete(new File(filesLoc));
		} catch (Exception e) {
			System.out.println("Unable to delete the folder or folder not found");
		}
	}
	@BeforeMethod(groups= {"ui","smoke"})
	public void launch() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-gpu");
		options.addArguments("--headless=new");
		options.addArguments("--disable-blink-features=AutomationControlled");
	    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);
		Configuration.browserCapabilities = options;
		open("");
		WebDriverRunner.getWebDriver().manage().window().maximize();
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
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
