package checkly.tests.ui.base;

import org.testng.annotations.AfterMethod;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import com.codeborne.selenide.WebDriverRunner;

public class BaseTest {

	@BeforeMethod
	public void launch() {
		open("");
		WebDriverRunner.getWebDriver().manage().window().maximize();
	}
	
	@AfterMethod
	public void tearDown() {
		closeWebDriver();
	}

}
