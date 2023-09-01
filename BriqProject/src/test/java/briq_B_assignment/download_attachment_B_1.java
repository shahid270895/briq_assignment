package briq_B_assignment;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class download_attachment_B_1 {
	
	
	String application_url="https://the-internet.herokuapp.com/download";
	String project_path = System.getProperty("user.dir");
	WebDriver driver;
	
	@BeforeMethod
	public void launch_browser() {
		
		ChromeOptions options = new ChromeOptions();
		String download_file_path = project_path + "\\download\\";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", download_file_path);
		options.setExperimentalOption("prefs", chromePrefs);
		
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
	}
	
	
	@Test
	public void download_all_non_png_attachments() throws InterruptedException {
		driver.get(application_url);
		List<WebElement> non_png_items = driver.findElements(By.xpath("//a[contains(@href,'download') and not(contains(@href,'.png')) and not(contains(@href,'.PNG'))]"));
		for(WebElement item : non_png_items) {
			item.click();	
		}
	}
	
	
	
	@AfterMethod
	public void close_browser() {
		driver.quit();
	}
	
	

}
