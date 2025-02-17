package pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.*;
import ui.core.Time;
public class LoginPage {
	private SelenideElement username = $(byAttribute("aria-label","Email address"));
	private SelenideElement password =$(byAttribute("aria-label","Password"));
	private SelenideElement login =$(byText("Log in"));
	private SelenideElement ctnu =$(byText("Continue"));
	
	static {
		Configuration.timeout = 10000;
	}

	public LoginPage setUserName(String uname) {
		username.setValue(uname);
		next();
		return this;

	}

	public LoginPage setPassword(String pwd) {
		password.setValue(pwd);
		return this;
	}

	public LoginPage pressLogin() {
		ctnu.pressEnter();
		return this;
	}
	public LoginPage next() {
		login.pressEnter();
		return this;
	}
	public void validateErrorsForInvalidCredentails() {
		$(byText("Wrong email or password")).shouldBe(visible,Time.LOW);
	}

}
