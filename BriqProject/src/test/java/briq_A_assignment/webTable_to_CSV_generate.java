package briq_A_assignment;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;

public class webTable_to_CSV_generate {
	
	String application_url="http://the-internet.herokuapp.com/challenging_dom";
	String project_path = System.getProperty("user.dir");
	String cvs_file_path = project_path+"\\Home\\briq\\webtable_"+get_current_date_and_time()+".csv";
	WebDriver driver;

	@BeforeMethod
	public void launch_browser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));	
	}
	
	@Test
	public void webTable_to_CSV_generator() throws IOException, InterruptedException {
		driver.get(application_url);
		
		CSVWriter write=new CSVWriter(new FileWriter(cvs_file_path));
		
		List<String[]> allTableData=new ArrayList<String[]>();
		
		List<WebElement> allHeaders=driver.findElements(By.xpath("//table//thead//tr//th[not(contains(text(),'Action'))]"));
		String headerNames[]=new String[allHeaders.size()];
		for (int i=0;i<headerNames.length;i++) {
			headerNames[i]=allHeaders.get(i).getText();
		}
		allTableData.add(headerNames);
		
		int allRowsData=driver.findElements(By.xpath("//tbody//tr")).size();
		int totalRow=allRowsData;
		while(allRowsData>0) {
			for(int i=1;i<=totalRow;i++) {
				List<WebElement> eachRowData = driver.findElements(By.xpath("(//tbody//tr)[" + i + "]//td"));
				String eachRowValue[]=new String[eachRowData.size()-1];
				for (int j=0;j<eachRowData.size()-1;j++) {
					eachRowValue[j]=eachRowData.get(j).getText();
				}
				allTableData.add(eachRowValue);
				allRowsData--;
			}
		}
		write.writeAll(allTableData);
		write.flush();
		System.out.println("Web table data entered properly into CSV file...");		
	}


	@AfterMethod
	public void close_browser() {
		driver.quit();
	}
	
	public static String get_current_date_and_time() {
		DateFormat customFormat=new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		Date currentDate=new Date();
		return customFormat.format(currentDate);
	}      	

}
