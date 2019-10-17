package io.naztech.redash.test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJobDetailOrganization {

	
	public class JobDetailTest {

		public WebDriverWait wait;
		public WebDriver driver;
		protected List<String> jobtitle = new ArrayList<String>();
		protected List<String> linktext = new ArrayList<String>();
		protected List<String> org_name = new ArrayList<String>();
		@Test

		public void Login() throws InterruptedException, IOException {

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
			ClickDropDown();

		}

		public void ClickDropDown() throws InterruptedException, IOException {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			driver.findElement(By.linkText("Job Details")).click();

			driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).click();
			{
				WebElement dropdown = driver.findElement(By.cssSelector(".ng-pristine"));
				dropdown.findElement(By.xpath("//option[. = 'NEW']")).click();

			}

			Select objselect = new Select(driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")));
			List<WebElement> list = objselect.getOptions();
			for(WebElement opt : list)
			{
//				System.out.println(opt.getText());
			org_name.add(opt.getText());
			}
			WebElement dropdown = driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control"));
			dropdown.click();
			
			for(int i = 0 ; i<org_name.size(); i++)
			{
			   dropdown.findElement(By.xpath("//option[. = '" + org_name.get(i) + "']")).click();
			   Thread.sleep(4000);
			   driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).clear();
				driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("11");
				Pagination(org_name.get(i));
			}	
				
		}

		public void Pagination(String org_name) throws InterruptedException, IOException {
			int row = 0;

			FileWriter path_select = new FileWriter("C:\\Users\\oyndrila.chowdhury\\Desktop\\test.csv");

			path_select.append("Test Case");
			path_select.append(',');
			path_select.append("Test Data");
			path_select.append(',');
			path_select.append("Pass/Fail");
			path_select.append('\n');

			boolean present = false;
			int i = 0;
//			while (!present) {
				try {
					driver.findElement(By.xpath(
							"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[3]/a"))
							.click();
					Thread.sleep(4000);
					wait = new WebDriverWait(driver, 600);
			          driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//					wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(
//							"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr[1]/td[2]"),
//							org_name));
					WebElement orgname = driver.findElement(By.xpath(
							"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr[1]/td[2]"));
					WebElement jobstatus = driver.findElement(By.xpath("//td[@column='columns[5]']"));
					System.out.println(jobstatus);
//					if (orgname.getText().equals(org_name) && jobstatus.getText().equals("NEW")) {
//						path_select.append("Is organization name correct ? " + ',');
//						path_select.append(org_name + ',');
//						path_select.append("pass");
//						path_select.append('\n');
//						path_select.append("Is job status correct ? " + ',');
//						path_select.append("NEW" + ',');
//						path_select.append("pass");
//						path_select.append('\n');
//						System.out.println(orgname.getText());
//						System.out.println(jobstatus.getText());
//						present = true;
//					} else {
//						present = false;
//					}

				} catch (Exception e) {
					
					System.out.println("No job in this site ");
				}
//				System.out.println(i);
//				i++;
//			

			WebElement clickDisable = null;
		    try {
			    do {

				wait = new WebDriverWait(driver, 10);
				List<WebElement> element = wait.until(presenceOfAllElementsLocatedBy(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr")));
				System.out.println("****" + element.size());
				for (WebElement w : element) {
					String st = w.findElement(By.xpath("td[1]")).getText();
					System.out.println("****" + st);
					jobtitle.add(st);
					String st1 = w.findElement(By.xpath("td[8]/div/a")).getText();
					linktext.add(st1);
					System.out.println("total jobs " + row + "\n");
//					String elemText = w.getText();
//					System.out.println(elemText);

//					path_select.write(elemText);
					row++;
				}
				WebElement click = driver.findElement(By.xpath("//*[@class='pagination-next']"));
				click.click();
				
				try {
					clickDisable = driver.findElement(By.xpath("//*[@class='pagination-next disabled']"));
					List<WebElement> element1 = wait.until(presenceOfAllElementsLocatedBy(By.xpath(
							"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr")));

					for (WebElement w : element1) {
						String st = w.findElement(By.xpath("td[1]")).getText();
						System.out.println("****" + st);
						jobtitle.add(w.findElement(By.xpath("td[1]")).getText());
						linktext.add(w.findElement(By.xpath("td[8]/div/a")).getText());
						System.out.println("total jobs " + row + "\n");
//						CheckJobTitle(jobtitle,linktext,path_select);
//						path_select.write(elemText);
						row++;
					}
					path_select.append("No. of job : " + ',');
					path_select.write(String.valueOf(row));
					path_select.append("\n");
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					log.warn("Failed to parse ", e);
					
				}
			} while (clickDisable == null);
	Thread.sleep(4000);
			}catch(Exception e)
			{
//				log.warn("Failed to parse ", e);
			}
//			path_select.close();
//			driver.close();\
//			getJobTitle(linktext, jobtitle, path_select);

		}

		public void getJobTitle(List<String> linktext, List<String> jobtitle, FileWriter path_select)
				throws InterruptedException, IOException {
			List<String> s1 = new ArrayList<>();
			for (String link : linktext) {
				if (link.contains("http")) {
					driver.get(link);
					Thread.sleep(8000);
					String title = driver.getTitle();
					System.out.println(title);
					s1.add(title);
				} else {
					String s = "http://uat.redash.naztech.us.com:70/" + link;
					System.out.println(s);
					driver.get(s);
					Thread.sleep(8000);
					String title = driver.getTitle();
					System.out.println(title);
					s1.add(title);

				}
			}
			System.out.println(jobtitle.size());
			for (int i = 0; i < jobtitle.size(); i++) {
				System.out.println(s1.get(i));
				System.out.println(jobtitle.get(i));
				if (s1.get(i).contains(jobtitle.get(i))) {

					System.out.println(s1.get(i));
					path_select.append("Is job title correct ? : " + ',');
					path_select.append(jobtitle.get(i) + ',');
					path_select.append("pass");
					path_select.append('\n');
				} else {
					path_select.append("Is job title correct ? : " + ',');
					path_select.append(jobtitle.get(i) + ',');
					path_select.append("Fail");
					path_select.append('\n');
				}
			}
			path_select.close();
		}

	}
}
