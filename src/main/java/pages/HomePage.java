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
			return Arrays.stream(menus).allMatch(
					menu -> $(".relative.flex[aria-label*='" + menu + "']").scrollIntoView(true).is(visible, Time.LOW));
		}
		return false;
	}

	public boolean validateStatusAndItem(String status, List<String> itemsHidden, List<String> itemsVisible) {
		$("[role='listbox'] [aria-label='" + status.toLowerCase() + "']").shouldBe(visible, Time.MED).click();
		return itemsHidden.stream()
				.allMatch(e -> $x("//span[contains(@title, '" + e + "')]").is(hidden))
				&& itemsVisible.stream()
						.allMatch(e -> $x("//span[contains(@title, '" + e + "')]").scrollIntoView(true).is(visible));
	}

	public HomePage applyFilter(List<String> filters) {
		$x("//span[normalize-space()='Filters']").shouldBe(visible, Time.LOW).click();
		for (String filter : filters) {
			SelenideElement ele = $x("//label[contains(text(),'" + filter + "')]");
			if (!ele.$("input").isSelected()) {
				ele.click();
			} else {
				System.out.println("Element is already selected");
			}
		}
		Selenide.executeJavaScript("location.reload(true);");
		return this;
	}

	public HomePage applyTags(List<String> tags) {
		$x("//span[normalize-space()='Tags']").shouldBe(visible, Time.LOW).click();
		for (String tag : tags) {
			$("div[role='listbox'] article[title='" + tag + "']").click();
		}
		Selenide.executeJavaScript("location.reload(true);");
		return this;
	}

	public HomePage search(String str) {
		$("input[placeholder='Search by name, request url…']").shouldBe(visible, Time.LOW).val(str);
		return this;
	}

	public boolean validateResult(List<String> itemsHidden, List<String> itemsVisible) {
		if (itemsHidden == null) {
			return itemsVisible.stream().allMatch(
					e -> $x("//span[contains(@title, '" + e + "')]").scrollIntoView(true).is(visible, Time.LOW));
		} else if(itemsVisible==null){
			return itemsHidden.stream().allMatch(
					e -> $x("//span[contains(@title, '" + e + "')]").is(hidden, Time.LOW));
		}else {
			return itemsHidden.stream().allMatch(e -> $x("//span[contains(@title, '" + e + "')]").is(hidden, Time.LOW))
					&& itemsVisible.stream().allMatch(e -> $x("//span[contains(@title, '" + e + "')]")
							.scrollIntoView(true).is(visible, Time.LOW));
		}
	}

	public void clickItem(String item) {
		$x("//span[contains(@title, '" + item + "')]").should(visible, Time.MED).click();
	}
	
	public void clickMenuForItem(String menu,String subMenu,String item) {
		$x("//span[contains(@title, '"+item+"')]/ancestor::a//button").shouldBe(visible,Time.LOW).click();
		$x("//div[@role='listbox']//span[contains(text(),'"+menu+"')]").shouldBe(visible,Time.LOW).click();
		if(!subMenu.isBlank()) {
			$x("//span[normalize-space()='"+subMenu+"']").shouldBe(visible,Time.LOW).click();
		}
	}
}
