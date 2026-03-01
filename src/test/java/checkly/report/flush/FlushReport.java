package checkly.report.flush;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.commons.io.FileUtils;
import lombok.SneakyThrows;

public class FlushReport {
	
	@SneakyThrows
	public void generateReport() {
		File generatedXML = new File(System.getProperty("user.dir") + "//target//surefire-reports//testng-results.xml");
		File copyPath =  new File(System.getProperty("user.dir") + "//Reports//testng-results.xml");
		File newResultsAlone =  new File(System.getProperty("user.dir") + "//Reports//testng-new-results.xml");
		if (!waitForFile(generatedXML, 30)) {
	        throw new FileNotFoundException("TestNG results file not created or empty after timeout");
		}
	    
		if(copyPath.exists()) {
			FileUtils.copyFile(generatedXML, newResultsAlone);
		}else {
			FileUtils.copyFile(generatedXML, copyPath);
		}
		
	}

	private boolean waitForFile(File file, int timeoutSeconds) {
	    int waited = 0;
	    while (!file.exists() || file.length() == 0) {
	        if (waited >= timeoutSeconds) {
	            return false;
	        }
	        try {
	            Thread.sleep(1000);
	            waited++;
	            System.out.println("Waiting for file: " + file.getName() + "... " + waited + " seconds");
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            return false;
	        }
	    }
	    return true;
	}
}
