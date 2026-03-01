package ui;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

import pages.EnvironmentVarPage;
import pages.SidePanel;
import ui.base.LoggedInBaseTest;

public class EnvVarTest extends LoggedInBaseTest {
	
	@Test
	public void tc001_createEnvVar() {
		new SidePanel().clickOnMenuItem("Environment variables");
		new EnvironmentVarPage().createEnvVar("TEST_KEY", "TEST_VALUE", Optional.of(true));
		Assert.assertTrue(new EnvironmentVarPage().validateEnvVar("TEST_KEY", "TEST_VALUE", Optional.of(true)),
				"Environment variable not created successfully");
	}
	@Test
	public void tc002_updateEnvVar() {
		new SidePanel().clickOnMenuItem("Environment variables");
		new EnvironmentVarPage().updateEnvVar("TEST_KEY", "UPDATED_VALUE", Optional.empty());
		Assert.assertTrue(new EnvironmentVarPage().validateEnvVar("TEST_KEY", "UPDATED_VALUE", Optional.empty()),
				"Environment variable not updated successfully");
	}
	@Test
	public void tc003_deleteEnvVar() {
		new SidePanel().clickOnMenuItem("Environment variables"); 
		new EnvironmentVarPage().deleteEnvVar("TEST_KEY");
		Assert.assertFalse(new EnvironmentVarPage().validateEnvVar("TEST_KEY", "UPDATED_VALUE", Optional.empty()),
				"Environment variable not deleted successfully");
}

}
