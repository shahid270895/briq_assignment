package briq_B_assignment;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class upload_attachment_B_2 {

	String application_url="https://the-internet.herokuapp.com/upload";
	static String project_path = System.getProperty("user.dir");
	static String upload_file_path = project_path + "\\download";
	WebDriver driver;

	@BeforeMethod
	public void launch_browser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));	
	}


	@Test
	public void upload_attachment_from_the_downloaded_non_png_file() throws InterruptedException {
		driver.get(application_url);
		WebElement choose_file_option = driver.findElement(By.id("file-upload"));
		choose_file_option.sendKeys(pick_a_file_from_downloaded_folder());
		WebElement upload_button = driver.findElement(By.id("file-submit"));
		upload_button.click();
		WebElement uploaded_header = driver.findElement(By.xpath("//h3"));
		if(uploaded_header.isDisplayed()) {
			String actual_text=driver.findElement(By.id("uploaded-files")).getText().trim();
			String exp_text[]= pick_a_file_from_downloaded_folder().split("download");
			String expected_text=exp_text[1].substring(1);
			Assert.assertEquals(actual_text, expected_text);
		}
		else
		{
			System.out.println("Not uploaded...");
			Assert.assertTrue(false);
		}
	}


	@AfterMethod
	public void close_browser() {
		driver.quit();
	}

	public static String pick_a_file_from_downloaded_folder() {
		String dp = upload_file_path ;
		File folder = new File(dp);
		File[] allFiles = new File(folder.getPath()).listFiles();
		String fileToUpload = dp + "\\"+ allFiles[0].getName();
		return fileToUpload;
	}

}
