package io.naztech.redash.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class TestOntology {

	public WebDriverWait wait;
	public WebDriver driver;
	public String table_xpath = "/html/body/section/app-view/div/dashboard-page/div/div[2]/div/";
	NumberFormat ukFormat = NumberFormat.getNumberInstance(Locale.UK);
	List<String> state = new ArrayList<String>();

	@Test

	public void Login() throws InterruptedException, IOException, ParseException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\oyndrila.chowdhury\\git\\New folder\\job-harvestar\\webdrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.get("http://uat.redash.naztech.us.com:70/login");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.findElement(By.cssSelector(".d-flex")).click();

		driver.findElement(By.cssSelector("*[data-test=\"Email\"]")).click();

		driver.findElement(By.cssSelector("*[data-test=\"Email\"]")).sendKeys("app.admin@naztech.us.com");

		driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).click();

		driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).sendKeys("n@ztech321");

		driver.findElement(By.cssSelector(".btn")).click();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.linkText("Ontology Statistics")).click();
		Skill_Taging_Status();

	}

	public void Skill_Taging_Status() throws InterruptedException, IOException, ParseException {
		Thread.sleep(5000);
		driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[3]/div[1]/dashboard-widget/div/div/div[3]/a"))
				.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter path_select = new FileWriter("C:\\Users\\oyndrila.chowdhury\\Desktop\\ontology_test.csv");
		path_select.append("Test Case");
		path_select.append(',');
		path_select.append("Test Data");
		path_select.append(',');
		path_select.append("Pass/Fail");
		path_select.append('\n');
		try {
			Thread.sleep(5000);
			WebElement skill_taging_status = driver.findElement(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[3]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
			String skill_tagged = skill_taging_status.findElement(By.xpath("td[1]")).getText();
			String skill_untagged = skill_taging_status.findElement(By.xpath("td[2]")).getText();
			String redash_total_job = skill_taging_status.findElement(By.xpath("td[3]")).getText();
			int redash_total_job_check = ukFormat.parse(redash_total_job).intValue();
			int total_job = ukFormat.parse(skill_tagged).intValue() + ukFormat.parse(skill_untagged).intValue();
			System.out.println(total_job);

			path_select.append("Skill Tagging Status : ");
			path_select.append(',');
			path_select.append(String.valueOf(total_job));
			path_select.append(',');
			if (redash_total_job_check == total_job) {

				path_select.append("Pass");
				path_select.append('\n');
			} else {
				path_select.append("Fail");
				path_select.append('\n');
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Location_Tagging(path_select);
		OrganizationDropdownOfSkillByOrganization();

	}

	public void Location_Tagging(FileWriter path_select) throws ParseException, IOException, InterruptedException {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[4]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement location_tagging = driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[4]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
		String untagged_job = location_tagging.findElement(By.xpath("td[3]")).getText();
		String matched_geo_hierarchy = location_tagging.findElement(By.xpath("td[4]")).getText();
		String unmatched_geo_hierarchy = location_tagging.findElement(By.xpath("td[5]")).getText();
		int tagged_job = ukFormat.parse(matched_geo_hierarchy).intValue()
				+ ukFormat.parse(unmatched_geo_hierarchy).intValue();
		System.out.println(tagged_job);
		int total_job = tagged_job + ukFormat.parse(untagged_job).intValue();
		System.out.println(total_job);
//		OrganizationDropdownOfSkillByOrganization();
		HierarchySearchByCountry();

	}

	public List<String> getExcelToList() throws IOException {
		File src = new File("C:\\Users\\oyndrila.chowdhury\\Desktop\\read_excel.xlsx");
		List<String> jobskill = new ArrayList<String>();
		FileInputStream fls = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fls);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		for (int i = 1; i < sheet1.getLastRowNum(); i++) {
			jobskill.add(sheet1.getRow(i).getCell(0).getStringCellValue());

		}
		wb.close();
		return jobskill;
	}

	public void OrganizationDropdownOfSkillByOrganization() throws InterruptedException, IOException {
		FileWriter path_select = new FileWriter("C:\\Users\\oyndrila.chowdhury\\Desktop\\ontology_test.csv");
		path_select.append("Org name");
		path_select.append(',');
		path_select.append("Skill");
		path_select.append('\n');
		List<String> org_name = new ArrayList<String>();
		Select objselect = new Select(driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div[1]/parameter-input/span/span/query-based-parameter/select")));
		List<WebElement> list = objselect.getOptions();
		for (WebElement opt : list) {
			org_name.add(opt.getText());
		}
		WebElement dropdown = driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div[1]/parameter-input/span/span/query-based-parameter/select"));
		dropdown.click();

		for (int i = 0; i <org_name.size(); i++) {
			dropdown.findElement(By.xpath("//option[. = '" + org_name.get(i) + "']")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div[2]/parameter-input/span/input"))
					.clear();
			driver.findElement(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div[2]/parameter-input/span/input"))
					.sendKeys("442");
			System.out.println("****** "+org_name.get(i)+" ******");
			path_select.append(org_name.get(i));
			path_select.append(',');
			SkillByOrganization(org_name,path_select);
		}
		 path_select.close();
	}

	public void SkillByOrganization(List<String> org_name , FileWriter path_select) throws InterruptedException, IOException {
		
		List<String> redashjobskill = new ArrayList<String>();
		WebElement clickdisable = null;

		Thread.sleep(5000);
		
		driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[3]/a"))
				.click();
		Thread.sleep(5000);
		do {
			try {
			List<WebElement> table = driver.findElements(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
			for (WebElement tb : table) {
				redashjobskill.add(tb.findElement(By.xpath("td[1]")).getText());
			}
			
			List<WebElement> click = driver.findElements(By.xpath("//li[@class='pagination-next']"));
			click.get(2).click();}
			catch(Exception e)
			{
				
			}
			Thread.sleep(5000);
			try {

				clickdisable = driver.findElement(By.xpath("//*[@class='pagination-next disabled']"));
				List<WebElement> table1 = driver.findElements(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[9]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
				for (WebElement tb : table1) {
					redashjobskill.add(tb.findElement(By.xpath("td[1]")).getText());
				}
			} catch (Exception e) {
			}

//			Collections.sort(redashjobskill, String.CASE_INSENSITIVE_ORDER);
			System.out.println(redashjobskill);
			

		} while (clickdisable == null);

		for (int i = 0; i < redashjobskill.size(); i++) {
			List<String> jobskill = getExcelToList();
			for (int j = 0; j < jobskill.size(); j++) {
				if (jobskill.get(j).equals(redashjobskill.get(i))) {
					break;
				}

				if (j == jobskill.size() - 1 && jobskill.get(j) != redashjobskill.get(i)) {
					System.out.println("This is not in Excel " + redashjobskill.get(i));
					
					path_select.append(redashjobskill.get(i));
					path_select.append('\n');
				}
				

			}

		}
           System.out.println("******** END TEST ********" );
          
	}
	
	public void HierarchySearchByCountry() throws InterruptedException
	{
		List<String> country_name = new ArrayList<String>();
		Select objselect = new Select(driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div/parameter-input/span/span/query-based-parameter/select")));
		List<WebElement> list = objselect.getOptions();
		for (WebElement opt : list) {
			country_name.add(opt.getText());
		}
		WebElement dropdown = driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div/parameter-input/span/span/query-based-parameter/select"));
		dropdown.click();

		for (int i = 0; i < country_name.size(); i++) {
			dropdown.findElement(By.xpath("//option[. = '" + country_name.get(i) + "']")).click();
			Thread.sleep(4000);
//			System.out.println("****** "+org_name.get(i)+" ******");
			CheckUniqueState();

		}
	}
		
	
  public void CheckUniqueState() throws InterruptedException
  {
	  driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[3]/a"))
				.click();
		Thread.sleep(5000);
        WebElement clickdisable = null;
		
		do {
			
				List<WebElement> table = driver.findElements(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
			
				for (WebElement tb : table) {
					state.add(tb.findElement(By.xpath("td[2]")).getText());
				}
				
				System.out.println(state);
				try {
				WebElement clickxpath = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[2]/ul/li[@class='pagination-next']"));

			clickxpath.click();
			List<WebElement> table2 = driver.findElements(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
			for (WebElement tb : table2) {
				state.add(tb.findElement(By.xpath("td[2]")).getText());
			}
			System.out.println(state);
				clickdisable = driver.findElement(By.xpath("//*[@class='pagination-next disabled']"));
				List<WebElement> table1 = driver.findElements(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
				for (WebElement tb : table1) {
					state.add(tb.findElement(By.xpath("td[2]")).getText());
				}
				System.out.println(state);
			} catch (Exception e) {
				clickdisable =  driver.findElement(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[12]/div[1]/dashboard-widget/div/div/div[3]/a")) ;
			}

			System.out.println(state);

		} while (clickdisable == null);
  }
	  
   
}
