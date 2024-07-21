package checkly.tests.ui.base;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.codeborne.selenide.WebDriverRunner;

public class BaseTest {

	@BeforeEach
	public void launch() {
		open("");
		WebDriverRunner.getWebDriver().manage().window().maximize();
	}
	
	@AfterEach
	public void tearDown() {
		closeWebDriver();
	}

}
