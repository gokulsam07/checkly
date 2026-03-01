package pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Optional;

import static com.codeborne.selenide.Selectors.*;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import ui.core.Time;


public class EnvironmentVarPage {

    public void createEnvVar(String key, String val, Optional<Boolean> lock) {
        SelenideElement keyName = $(byAttribute("aria-label","Add a new environment variable")).$("#envVarKey");
        keyName.setValue(key);
        SelenideElement valueName = $(byAttribute("aria-label","Add a new environment variable")).$(byAttribute("aria-label","Environment variable value"));
        valueName.setValue(val);
        if (lock.isPresent()){
            $("[data-octicon-name='unlock']").click();
        }
        $(byAttribute("aria-label","Add a new environment variable")).$(byText("Add")).shouldBe(visible,Time.MED).click();
    }
    
    public void updateEnvVar(String key, String val, Optional<Boolean> lock) {
    	$(byText(key)).closest("[data-testid=environment-variable-row]").find(byAttribute("aria-label", "Edit environment variable")).click();
        SelenideElement keyName = $(byAttribute("data-testid", "environment-variable-row")).find(byAttribute("aria-label", "Environment variable key"));
        keyName.setValue(key);
        if (lock.isPresent()) {
           $("[data-octicon-name='unlock']").click();
        }else{
        	 $("[data-octicon-name='lock']").click();
        }
        SelenideElement valueName = $(byAttribute("data-testid", "environment-variable-row")).find(byAttribute("aria-label", "Environment variable value"));
        valueName.setValue(val);
        $(byAttribute("aria-label","Save changes")).click();
    }


    public boolean validateEnvVar(String key, String val, Optional<Boolean> lock) {
        SelenideElement row = $(byText(key)).closest("[data-testid=environment-variable-row]"); //for closest this is how is should be attr val shouldnt be enclosed in quotes
        String expectedText = lock.isPresent() ? "••••••••••••••••" : val;
        System.out.println(row.is(text(expectedText)));
        return  row.is(text(expectedText));
    }

    public void deleteEnvVar(String key) {
    	$(byText(key)).closest("[data-testid=environment-variable-row]").find(byAttribute("aria-label", "Open actions menu")).click();
        $(byText("Delete")).click();
        $(byText("Delete environment variable")).click();
    }


}