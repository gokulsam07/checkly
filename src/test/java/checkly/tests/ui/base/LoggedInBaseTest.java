package checkly.tests.ui.base;

import org.testng.annotations.AfterMethod;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import pages.LoginPage;
import test.utils.Prop;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

public class LoggedInBaseTest {
	String uname = Prop.get("login.properties", "username");
	String pwd = Prop.get("login.properties", "password");
	@BeforeMethod(groups= {"ui","smoke"})
	public void launch() {
		open("");
		WebDriverRunner.getWebDriver().manage().window().maximize();
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
	}
	
	@AfterMethod(groups= {"ui","smoke"})
	public void tearDown() {
		closeWebDriver();
	}
}
