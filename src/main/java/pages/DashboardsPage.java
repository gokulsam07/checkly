package pages;
import org.openqa.selenium.By;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.java.Log;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import ui.core.Time;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
public class DashboardsPage {
	public boolean validate() {
		return $x("//h2[normalize-space()='Dashboard']").is(visible, Time.LOW);
	}
}
