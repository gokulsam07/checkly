package ui;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import pages.LoginPage;
import ui.base.BaseTest;
import utils.Env;

public class LoginTest extends BaseTest {
	String uname = Env.get("username", "USER_GOKUL");
	String pwd = Env.get("password", "GOKUL_PASSWORD");
	String error = "Wrong email or password.";
	@Test
	public void tc001_loginTest() {
		new LoginPage().setUserName(uname).setPassword(pwd).pressLogin();
		assertThat(new LoginPage().validateLoginIsSuccessful()).as("Login is not successful").isTrue();
	}
	
	@Test
	public void tc002_loginTestFail() {
		new LoginPage().setUserName(uname).setPassword(pwd+"x").pressLogin();
		new LoginPage().validateErrorsForInvalidCredentails();
	}

}
