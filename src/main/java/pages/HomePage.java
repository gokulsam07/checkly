package pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import java.util.Arrays;
import java.util.List;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.java.Log;
import com.codeborne.selenide.Configuration;
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
		String width = $("[aria-label='Sidebar navigation']").getCssValue("width");
		if (action.equals("open") && width.contains(width + "px")) {
			$("button.outline-none").click();
		} else if (action.equals("close") && width.contains(width + "px")) {
			$("button.outline-none").click();
		} else {
			log.info("The flex menu is already in the desired state");
		}
	}

	public boolean validatePanelIsOpenWithMenu(String[] menus) {
		if (validateLoginIsSuccessful()) {
			String width = $("[aria-label='Sidebar navigation']").getCssValue("width");
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
		$(byText("Status")).shouldBe(visible, Time.LOW).click();
		for (String filter : filters) {
			SelenideElement ele = $(byText(filter));
			if (!ele.preceding(0).isSelected()) {
				ele.click();
			} else {
				System.out.println("Element is already selected");
			}
		}
		executeJavaScript("location.reload(true);");
		return this;
	}

	public HomePage applyTags(List<String> tags) {
		$(byText("Tags")).shouldBe(visible, Time.LOW).click();
		for (String tag : tags) {
			$("[role='listbox'] [title='" + tag + "']").scrollTo().click();
		}
		executeJavaScript("location.reload(true);");
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
		$(byText(item)).should(visible, Time.MED).click();
	}
	
	public void clickMenuForItem(String menu,String subMenu,String item) {
		$(byText(item)).ancestor("a").$("[aria-label='Open check actions menu']").shouldBe(visible,Time.LOW).click();
		$(byText(menu)).shouldBe(visible,Time.LOW).click();
		if(!subMenu.isBlank()) {
			$(byText(subMenu)).shouldBe(visible,Time.LOW).click();
		}
	}
}
