package checkly.tests.ui.base;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import pages.LoginPage;
import test.utils.Prop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.codeborne.selenide.WebDriverRunner;

public class LoggedInBase {
	String uname = Prop.get("login.properties", "username");
	String pwd = Prop.get("login.properties", "password");
	@BeforeEach
	public void launch() {
		open("");
		WebDriverRunner.getWebDriver().manage().window().maximize();
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
	}
	
	@AfterEach
	public void tearDown() {
		closeWebDriver();
	}
}
