package utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class Screenshot {
	public static String screenshot(WebDriver driver, String screenshotname) {

		String path= "../AutomationEcommerceWebsite/images/"+screenshotname+".png";
        TakesScreenshot s =(TakesScreenshot)driver;

        File Src = s.getScreenshotAs(OutputType.FILE);
        
        File Dest=new File(path);

        try {
			FileUtils.copyFile(Src, Dest);
			Reporter.log("Able to capture Screenshot",true);
		} catch (IOException e) {
			Reporter.log("Unble to capture Screenshot",true);
			//e.printStackTrace();
		}
        return path;
	}
}
