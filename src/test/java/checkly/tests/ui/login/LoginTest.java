package checkly.tests.ui.login;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;
import checkly.tests.ui.base.BaseTest;
import pages.HomePage;
import pages.LoginPage;
import test.utils.Prop;

public class LoginTest extends BaseTest {
	String uname = Prop.get("login.properties", "username");
	String pwd = Prop.get("login.properties", "password");
	String error = "Wrong email or password.";
	@Test
	public void tc001_loginTest() {
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
		assertThat(new HomePage().validateLoginIsSuccessful()).as("Login is not successful").isTrue();
	}
	
	@Test
	public void tc002_loginTestFail() {
		new LoginPage().setUserName(uname).setPassword(pwd+"x").pressLogin();
		new LoginPage().validateErrorsForInvalidCredentails();
	}

}
