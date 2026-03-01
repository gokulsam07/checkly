package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import ui.core.Time;

public class LoginPage {
	private SelenideElement username = $("#username");
	private SelenideElement password =$("#password");
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
	
	public boolean validateLoginIsSuccessful() {
		WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("account"));
		return WebDriverRunner.getWebDriver().getCurrentUrl().contains("account");
	}

}
