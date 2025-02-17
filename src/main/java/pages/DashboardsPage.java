package pages;


import com.codeborne.selenide.SelenideElement;
import ui.core.Time;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class DashboardsPage {
	private SelenideElement newDBoard = $x("//span[normalize-space()='New Dashboard']");

	public boolean validate() {
		return $x("//h2[normalize-space()='Dashboard']").is(visible, Time.LOW);
	}

	public boolean validateEmptyScreen() {
		return newDBoard.is(visible, Time.LOW);
	}

	public boolean validateDashBoard(String boardName) {
		return $(byText(boardName)).is(visible,Time.MED);
	}
	
	public void clickDashboard(String board) {
		$(byText(board)).shouldBe(visible,Time.MED).click();
	}
	
	public void validateDescription(String desc) {
		$(byText(desc)).shouldBe(visible,Time.LOW);
	}
}
