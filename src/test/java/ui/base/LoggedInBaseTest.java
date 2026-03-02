package ui.base;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;

import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import pages.LoginPage;
import utils.Env;

public class LoggedInBaseTest {
	String uname = Env.get("username", "USER_GOKUL");
	String pwd = Env.get("password", "GOKUL_PASSWORD");
	
	@BeforeSuite(groups= {"ui","smoke"})
    public void setupAllure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true));
    }

	@BeforeMethod(groups= {"ui","smoke"})
	public void launch() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-gpu");
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
		options.addArguments("--disable-blink-features=AutomationControlled");
	    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);
		Configuration.browserCapabilities = options;
		open("");
		Map<String,Object> params = new HashMap<>();
		params.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
		((ChromeDriver) WebDriverRunner.getWebDriver()).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);
		WebDriverRunner.getWebDriver().manage().window().maximize();
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
	}
	
	@AfterMethod(groups= {"ui","smoke"})
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
	        try {
	        	File screenshot = Screenshots.getLastScreenshot();
	            if (screenshot != null) {
	                Allure.addAttachment("Failure Screenshot", new FileInputStream(screenshot));
	            }
	        } catch (FileNotFoundException e) {
	            System.err.println("Could not find screenshot to attach to Allure");
	        }
	    }
		closeWebDriver();
	}
}
