package pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.java.Log;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.*;
import ui.core.Time;

@Log
public class HomePage {
	private SelenideElement validateLogin = $("a[title='Go to account dashboard']");
	static {
		Configuration.timeout = 10000;
	}

	public boolean validateLoginIsSuccessful() {
		return validateLogin.is(visible, Time.LOW);
	}

	public void clickSidePanelMenu(String menu) {
		$(".relative.flex[aria-label*='" + menu + "']").scrollIntoView(true).shouldBe(visible, Time.LOW).click();
	}

	public void flexMenu(String action) {
		String width = Selenide.executeJavaScript(
				"return document.querySelector(\"aside[aria-label='Sidebar navigation']\").style.width");
		if (action.equals("open") && width.equals(width + "px")) {
			$("button.outline-none").click();
		} else if (action.equals("close") && width.equals(width + "px")) {
			$("button.outline-none").click();
		} else {
			log.info("The flex menu is already in the desired state");
		}
	}

	public boolean validatePanelIsOpenWithMenu(String[] menus) {
		if (validateLoginIsSuccessful()) {
			String width = Selenide.executeJavaScript(
					"return document.querySelector(\"aside[aria-label='Sidebar navigation']\").style.width");
			if (width.contains("60px")) {
				$("button.outline-none").click();
			}
			return Arrays.stream(menus)
					.allMatch(menu -> $(".relative.flex[aria-label*='" + menu + "']").scrollIntoView(true).is(visible, Time.LOW));
		}
		return false;
	}
}
