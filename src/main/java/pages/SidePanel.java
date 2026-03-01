package pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.*;
import ui.core.Time;

public class SidePanel{
	

	public void clickOnMenuItem(String menuItem) {
	
	boolean sideBarVisibility = $(byAttribute("aria-label","Close sidebar")).is(hidden);
    if (sideBarVisibility) {
    	$(byAttribute("aria-label","Toggle sidebar")).click();
    }
    	$$(byText(menuItem)).first().shouldBe(visible,Time.MED).click();
	}
	
}