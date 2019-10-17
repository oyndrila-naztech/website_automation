package io.naztech.redash.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SiteRunHistory {

	File file = new File("C:\\Users\\oyndrila.chowdhury\\Desktop\\New folder (3)\\file.csv");

	public WebDriver driver;
	
	public SiteRunHistory() throws IOException
	{
		FileWriter path_select = new FileWriter(file);
	}
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
		driver.findElement(By.linkText("3. Job Details")).click();
		
		getParsedjobs();
	}
	
	
     public List<String> Getclassname() throws InterruptedException{
    	 Thread.sleep(5000);
    	 Select classname = new Select(driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div[2]/parameter-input/span/span/query-based-parameter/select")));
    	 List<WebElement> list = classname.getOptions();
    	 List<String> classnamelist = new ArrayList<String>();
    	 for(WebElement element : list)
    	 {
    		 System.out.println(element.getText());
    		 classnamelist.add(element.getText());
    		 
    		 
    	 }
    	 
    	 return classnamelist;
    	 
     }
     public void getParsedjobs() throws InterruptedException, IOException {
    	 List<String>parsedjob = Getclassname();
    	 FileWriter path_select = new FileWriter("C:\\Users\\oyndrila.chowdhury\\Desktop\\New folder (3)\\file.csv");
    	 
    	 path_select.append("File Name");
  		path_select.append('\n');
  WebElement xpath = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div[2]/parameter-input/span/span/query-based-parameter/select"));
    	 xpath.click();
    	 for(int i = 0 ; i<parsedjob.size(); i++)
    	 {
    		 System.out.println(parsedjob.get(i));
    		 try {
    		 xpath.findElement(By.xpath("//option[. = '" + parsedjob.get(i) + "']")).click();
    		 }
    		 catch(Exception e)
    		 {
    			 
    		 }
    		 
    	 driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[7]/div[1]/dashboard-widget/div/div/div[3]/a")).click();
    	 Thread.sleep(5000);
    	 SiteParsedJob(parsedjob.get(i),path_select);
    	 
    	 }
    	 path_select.close();

    	
     }
     
//     public FileWriter CreateFile() throws IOException {
//    	 FileWriter path_select = new FileWriter("C:\\Users\\oyndrila.chowdhury\\Desktop\\New folder (3)\\file.csv");
//
// 		path_select.append("File Name");
// 		path_select.append('\n');
// 		return path_select;
//     }
    public void SiteParsedJob(String org , FileWriter path_select) throws IOException
    {
 		
    	int count = 0;
    	List<Integer> zerojob = new ArrayList<Integer>();
    	List<WebElement> parsedxpath = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[7]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
    	parsedxpath.size();
    	for(WebElement xpath : parsedxpath)
    	{
    		zerojob.add(Integer.parseInt(xpath.findElement(By.xpath("td[6]")).getText().replace(",", "")));
    		
    	}
    	System.out.println(zerojob);
    	for(int p = 0;p<zerojob.size();p++)
    	{
//    		System.out.println(zerojob.get(p));
    		
    		if(zerojob.get(p)==0)
    		{
    			count++;
    		}
//    		System.out.println(count);
    		
    	}
    	System.out.println(count + " " + parsedxpath.size());
    	System.out.println(org);
    	
    	if(count==parsedxpath.size())
    	{
    		System.out.println(org);
    		path_select.append(org);
    		path_select.append('\n');
   
    	}
    	    	
    }
    
    

}
