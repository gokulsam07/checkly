package pages;

import static com.codeborne.selenide.Selenide.*;
import org.openqa.selenium.By;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.*;
import ui.core.Time;
public class LoginPage {
	private SelenideElement username = $(By.id("1-email"));
	private SelenideElement password = $(By.id("1-password"));
	private SelenideElement login = $(By.id("1-submit"));
	static {
		Configuration.timeout = 10000;
	}

	public LoginPage setUserName(String uname) {
		username.setValue(uname);
		return this;

	}

	public LoginPage setPassword(String pwd) {
		password.setValue(pwd);
		return this;
	}

	public LoginPage pressLogin() {
		login.pressEnter();
		return this;
	}
	
	public String validateErrorsForInvalidCredentails() {
		return $(".auth0-global-message-error span span").shouldBe(visible,Time.LOW).getText();
	}

}
