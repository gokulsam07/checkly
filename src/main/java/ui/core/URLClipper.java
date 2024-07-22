package ui.core;

import java.util.HashMap;

import com.codeborne.selenide.WebDriverRunner;

import lombok.SneakyThrows;

public class URLClipper {
	@SneakyThrows
	public static HashMap<String, String> clip() {
		HashMap<String, String> hm = new HashMap<>();
		Thread.sleep(3000);
		String url = WebDriverRunner.getWebDriver().getCurrentUrl();
		String[] clippedVal = url.split("/");
		for (int i = 0; i < clippedVal.length; i++) {
			if (clippedVal[i].equals("checks")) {
				hm.put(clippedVal[i], clippedVal[i+1]);
				break;
			}
		}
		return hm;
	}

}
