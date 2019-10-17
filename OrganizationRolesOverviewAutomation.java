package io.naztech.redash.test;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OrganizationRolesOverviewAutomation {

	private ChromeDriver driver;
	@Test
	public void test() throws InterruptedException {
		ArrayList<String> JobKey_list = new ArrayList<String>();
		try {
			System.setProperty("webdriver.chrome.driver",
				"webdrivers/chromedriver-76.exe");
			driver = new ChromeDriver();
			driver.get("http://uat.redash.naztech.us.com:70/login");
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(1300, 720));
			driver.findElement(By.cssSelector(".d-flex")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Email\"]")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Email\"]")).sendKeys("app.admin@naztech.us.com");
			driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).click();
			driver.findElement(By.cssSelector(".d-flex")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).sendKeys("n@ztech321");
			System.out.println("------------@Log in Successsful@-------------");
			driver.findElement(By.cssSelector(".btn")).click();
			Thread.sleep(5000);
			driver.findElement(By.linkText("Organization's Role Overview")).click();
			Thread.sleep(5000);
			WebElement drpdwn = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[1]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div/parameter-input/span/span/query-based-parameter/select"));
			drpdwn.findElement(By.xpath("//option[. = 'Nextdoor']")).click();
			driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[1]/div[1]/dashboard-widget/div/div/div[3]/a/span")).click();
			Thread.sleep(2000);
			List<WebElement> next = driver.findElements(By.xpath("//*[@class='pagination-next']"));
			WebElement clickDisable =null;
			int pageNo =1;
			do {
				System.out.println("Page : "+pageNo++);
				List<WebElement> table = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[1]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
				for (WebElement webElement : table) {
					String jobkey = webElement.findElement(By.xpath("td[1]/div")).getText();
					JobKey_list.add(jobkey);
					Thread.sleep(2000);
				}
				next.get(0).click();
				Thread.sleep(5000);
				try {
				    clickDisable =driver.findElement(By.xpath("//*[@class='pagination-next disabled']"));
				    System.out.println("Page : "+pageNo);
				    List<WebElement> table1 = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[1]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
					for (WebElement webElement : table1) {
						String jobkey = webElement.findElement(By.xpath("td[1]/div")).getText();
						JobKey_list.add(jobkey);
						Thread.sleep(3000);
					}
				}catch(Exception e) {
					continue;
				}
			}while(clickDisable==null);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		Search_with_Job_Spec_Id(JobKey_list);
		}
		public void Search_with_Job_Spec_Id(ArrayList<String> JobKey_list) throws InterruptedException {
			WebElement inputField = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[2]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div/parameter-input/span/input"));
			int sum =0;
			Thread.sleep(5000);
			for (int i = 0; i < JobKey_list.size(); i++) {
				try {
					
					inputField.sendKeys(JobKey_list.get(i));
					driver.findElement(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[2]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
						.click();
					List<WebElement> table  = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[2]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
					for (WebElement webElement : table) {
						String skill_name = webElement.findElement(By.xpath("td[1]/div")).getText();
						int skill_count = Integer.parseInt(webElement.findElement(By.xpath("td[2]/div")).getText());
						System.out.println(skill_name +" : "+skill_count);
						sum = sum+skill_count;
						
					}
					System.out.println("For jobkey "+JobKey_list.get(i)+" total skill count is : "+sum);
					sum=0;
					Thread.sleep(5000);
					inputField.clear();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}

}
