package pages;
import static com.codeborne.selenide.Selenide.*;
import org.openqa.selenium.By;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.java.Log;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.*;
import ui.core.Time;

public class ReportingPage {
	
	public boolean validate() {
		return $x("//h2[normalize-space()='Reporting']").is(visible, Time.LOW);
	}

}
