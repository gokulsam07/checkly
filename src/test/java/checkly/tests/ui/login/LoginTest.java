package checkly.tests.ui.login;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.Test;

import checkly.tests.ui.base.BaseTest;
import pages.HomePage;
import pages.LoginPage;
import test.utils.Prop;
@Test(groups = {"ui"})
public class LoginTest extends BaseTest {
	String uname = Prop.get("login.properties", "username");
	String pwd = Prop.get("login.properties", "password");
	String error = "Wrong email or password.";
	@Test
	public void loginTest() {
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
		assertThat(new HomePage().validateLoginIsSuccessful()).as("Login is not successful").isTrue();
	}
	
	@Test
	public void loginTestFail() {
		new LoginPage().setUserName(uname).setPassword(pwd+"x").pressLogin();
		assertThat(new LoginPage().validateErrorsForInvalidCredentails()).as("Error message is not displayed as expected").containsIgnoringCase(error);
	}

}
