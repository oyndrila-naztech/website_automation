package io.naztech.redash.test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJobHarvesterOfRedash {
	NumberFormat ukFormat = NumberFormat.getNumberInstance(Locale.UK);
	private ChromeDriver driver;
	private Scanner in;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Test
	public void logIn() {
		System.setProperty("webdriver.chrome.driver",
				"webdrivers/chromedriver-76.exe");
			driver = new ChromeDriver();
			driver.get("http://uat.redash.naztech.us.com:70/login");
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(1250, 750));
			driver.findElement(By.cssSelector(".d-flex")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Email\"]")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Email\"]")).sendKeys("app.admin@naztech.us.com");
			driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).click();
			driver.findElement(By.cssSelector(".d-flex")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).click();
			driver.findElement(By.cssSelector("*[data-test=\"Password\"]")).sendKeys("n@ztech321");
			System.out.println("------------@Log in Successsful@-------------");
			driver.findElement(By.cssSelector(".btn")).click();
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			//fw.close();
		jobHarvester();
	}

	public void jobHarvester() {
			System.out.println("Clicking Job Harvester Dashboard");
			System.out.println("_________________________________________________________");
			driver.findElement(By.linkText("Job Harvester")).click();
		dailyParsedJobCount_1();	
	}
	public void graphical_dailyParsedJobCount_1() {
		driver.findElement(By.xpath(
				"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[1]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
				.click();
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dailyParsedJobCount_1() {
			try {
				
				System.out.println("1. Daily Parsed Job Count");
				System.out.println("_________________________________________________________");
				driver.findElement(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[2]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
						.click();
		        Thread.sleep(3000);
				List<WebElement> element = driver.findElements(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[2]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
				System.out.println(element.size());
				for (WebElement webElement : element) {
					String t = webElement.getText();
					System.out.println(t);	
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		    siteStatistics_2();	
	}

	public void siteStatistics_2() {
		try {
			FileWriter fw;
			fw = new FileWriter("C:\\New folder\\test2.csv");
			fw.append("Test Case");
			fw.append(',');
			fw.append("Test Data");
			fw.append(',');
			fw.append("Pass/Fail");
			fw.append('\n');
			try {
				System.out.println("2. Site Statistics");
				System.out.println("_________________________________________________________");
				driver.findElement(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[3]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
						.click();
				Thread.sleep(3000);
			    List<WebElement> siteStatistics = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[3]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
			    for (WebElement webElement : siteStatistics) {
					String Successful = webElement.findElement(By.xpath("td[2]/div")).getText();
					String Failed = webElement.findElement(By.xpath("td[3]/div")).getText();
					String In_progress = webElement.findElement(By.xpath("td[4]/div")).getText();
					String Queued = webElement.findElement(By.xpath("td[5]/div")).getText();
					String Not_Run = webElement.findElement(By.xpath("td[6]/div")).getText();
					int total_Sites = ukFormat.parse(Successful).intValue()+ukFormat.parse(Failed).intValue()+ukFormat.parse(In_progress).intValue()+ukFormat.parse(Queued).intValue()+ukFormat.parse(Not_Run).intValue();
					String total = webElement.findElement(By.xpath("td[7]/div")).getText();
					if(ukFormat.parse(total).intValue()==total_Sites) {
						fw.append("Total Sites in Live " + ',');
						fw.append(total + ',');
						fw.append("PASSED");
					}
					else {
						fw.append("Total Sites in Live " + ',');
						fw.append(total + ',');
						fw.append("FAILED");
					}
					System.out.println("Total Sites : "+total_Sites);
					fw.append('\n');
					Thread.sleep(2000);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			totalJobOpeningTillToday_3(fw);
		}catch(IOException io) {	
		}
	}

	public void totalJobOpeningTillToday_3(FileWriter fw) {
		try {
			System.out.println("3. Total Job Opening ---Till Today");
			System.out.println("_________________________________________________________");
			driver.findElement(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[5]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
					.click();
			Thread.sleep(2000);
			List<WebElement> Total_Job_Opening = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[4]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
			for (WebElement webElement : Total_Job_Opening) {
				String NewJobs = webElement.findElement(By.xpath("td[1]/div")).getText();
				String AmendJobs = webElement.findElement(By.xpath("td[2]/div")).getText();
				String UnchangedJobs = webElement.findElement(By.xpath("td[3]/div")).getText();
				String RemovedJobs = webElement.findElement(By.xpath("td[4]/div")).getText();
				String ActiveJobs = webElement.findElement(By.xpath("td[5]/div")).getText();
				String RedashTPJ = webElement.findElement(By.xpath("td[6]/div")).getText();
				System.out.println(ukFormat.parse(NewJobs).intValue() +" "+AmendJobs+" "+UnchangedJobs+" "+RemovedJobs+" "+ActiveJobs+" "+RedashTPJ );
				int AJobs = ukFormat.parse(NewJobs).intValue()+ukFormat.parse(AmendJobs).intValue()+ukFormat.parse(UnchangedJobs).intValue();
				System.out.println(AJobs);
     			int Total_Processed_Jobs = AJobs+ukFormat.parse(RemovedJobs).intValue();
				if(Total_Processed_Jobs==ukFormat.parse(RedashTPJ).intValue()) {
					fw.append("Active Jobs : " + ',');
					fw.append(ukFormat.parse(ActiveJobs).toString() + ',');
					fw.append("PASSED");
					fw.append('\n');
					fw.append(" Total Jobs Processed : " + ',');
					fw.write(ukFormat.parse(RedashTPJ).toString() + ',');
					fw.append("PASSED");
					fw.append('\n');
				}
				else {
					fw.append("Active Jobs : " + ',');
					fw.append(ukFormat.parse(ActiveJobs).toString() + ',');
					fw.append("FAILED");
					fw.append('\n');
					fw.append(" Total Jobs Processed : " + ',');
					fw.write(ukFormat.parse(RedashTPJ).toString() + ',');
					fw.append("FAILED");
					fw.append('\n');
				}	
			}	
		} catch (Exception e) {
			System.out.println(e);
		}
		//siteStatusAllSites_5(fw);
		ragSiteStatusSuccessfulSite_4(fw);
	}

	public void ragSiteStatusSuccessfulSite_4(FileWriter fw) {
		Date d1 = null;
		Date d2 = null;
		try {
			fw.append("\t\tRAG Site Status\t\t" + '\n');
			fw.append("ORG NAME" + ',');
			fw.append("EXPECTED JOB" + ',');
			fw.append("PARSED JOB" + ',');
			fw.append("STATUS" + ',');
			fw.append("LAST RUN DURATION");
			fw.append('\n');
			System.out.println("4. Rag Site Status Successful Site");
			System.out.println("________________________________________________________________");
			WebElement button = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[5]/div[1]/dashboard-widget/div/div/div[3]/a/span"));
			button.click();
			Thread.sleep(2000);	
			List<WebElement> list = driver.findElements(By.xpath("//li[@class='pagination-next']"));
			WebElement clickDisable = null;
			int j =1;
			ArrayList<Float> cExpctd = new ArrayList<Float>();
			ArrayList<Float> cPrsd = new ArrayList<Float>();
			ArrayList<Float> avgJobParsed = new ArrayList<Float>();
			int sum = 0;
			do {
				List<WebElement> table = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[5]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
				sum= sum+table.size();
				System.out.println("Showing Page : "+j++);
				System.out.println("============================");
				for (WebElement webElement : table) {
					String orgName = webElement.findElement(By.xpath("td[2]/div")).getText();
					fw.append(orgName + ',');
					System.out.print(orgName + " : ");
					String startTime = webElement.findElement(By.xpath("td[8]/div")).getText();
					String endTime = webElement.findElement(By.xpath("td[9]/div")).getText();
					try {
					    d1 = format.parse(startTime);
					    d2 = format.parse(endTime);
					} catch (ParseException e) {
					    e.printStackTrace();
					}
					float diff = d2.getTime() - d1.getTime();
					float diffMinutes = diff / (60 * 1000);  
					System.out.println(diffMinutes);
					float cExp = ukFormat.parse(webElement.findElement(By.xpath("td[4]/div")).getText()).floatValue();
					fw.append(String.valueOf(cExp) + ',');
					cExpctd.add(cExp);
					float cp = ukFormat.parse(webElement.findElement(By.xpath("td[5]/div")).getText()).floatValue();
					fw.append(String.valueOf(cp) + ',');
					cPrsd.add(cp);
					float result = ((cp/cExp)*100);
					if(result>=80) {
						fw.append("SUCCESSFUL"+',');
					}
					else {
						fw.append("FAILED"+',');
					}
					fw.append(String.valueOf(diff));
					float avgJParsed = ukFormat.parse(webElement.findElement(By.xpath("td[6]/div")).getText()).floatValue();
					avgJobParsed.add(avgJParsed);
				}
				list.get(0).click();
				Thread.sleep(3000);
				try {
				    clickDisable = driver.findElement(By.xpath("//*[@class='pagination-next disabled']"));
					System.out.println("Showing page : "+j++);
					System.out.println("=============================");
					List<WebElement> table1 = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[5]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
					sum= sum+table1.size();
					for (WebElement webElement : table1) {
						String orgName = webElement.findElement(By.xpath("td[2]/div")).getText();
						fw.append(orgName + ',');
						System.out.print(orgName + " : ");
						String startTime = webElement.findElement(By.xpath("td[8]/div")).getText();
						String endTime = webElement.findElement(By.xpath("td[9]/div")).getText();
						try {
						    d1 = format.parse(startTime);
						    d2 = format.parse(endTime);
						} catch (ParseException e) {
						    e.printStackTrace();
						}
						long diff = d2.getTime() - d1.getTime();
						long diffMinutes = diff / (60 * 1000);  
						System.out.println(diffMinutes);
						float cExp = ukFormat.parse(webElement.findElement(By.xpath("td[4]/div")).getText()).floatValue();
						fw.append(String.valueOf(cExp) + ',');
						cExpctd.add(cExp);
						float cp = ukFormat.parse(webElement.findElement(By.xpath("td[5]/div")).getText()).floatValue();
						fw.append(String.valueOf(cp) + ',');
						cPrsd.add(cp);
						float result = ((cp/cExp)*100);
						if(result>=80) {
							fw.append("SUCCESSFUL");
							fw.append('\n');
						}
						else {
							fw.append("FAILED");
							fw.append('\n');
						}
					}
				} catch (Exception e) {
				 
				}
				} while(clickDisable == null);
			System.out.println("Total Successful sites = "+sum);
			driver.findElement(By.cssSelector(".p-t-10 > .form-control")).sendKeys("UCommune");
			WebElement refresh = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[5]/div[1]/dashboard-widget/div/div/div[3]/a/span"));
			refresh.click();
			Thread.sleep(2000);	
		} catch (Exception e) {
			log.warn("Failed to parse ", e);
		}
		siteStatusAllSites_5(fw);
	}

	public void siteStatusAllSites_5(FileWriter fw) {
		ArrayList<String> allOrgList = new ArrayList<String>();
		ArrayList<Integer> parsedJobavg = new ArrayList<Integer>();
		ArrayList<Float> RunTimebavg = new ArrayList<Float>();
		int num = 1;
			try {
				System.out.println("5. Site Status All Sites");
				System.out.println("________________________________________________________________");
				in = new Scanner(System.in);
				fw.append('\n');
				fw.append("  RUN HISTORY  "+'\n'+'\n');
				fw.append("  ORG NAME  "+',');
				fw.append("  REDASH PARSED AVG "+',');
				fw.append("  ACTUAL PARSED AVG  "+',');
				fw.append("  STATUS  "+',');
				fw.append("  REDASH RUNTIME AVG "+',');
				fw.append("  ACTUAL RUNTIME AVG  "+',');
				fw.append("  STATUS  "+',');
				fw.append('\n');
				int inc = 1;
				do {
					System.out.println("Enter any Status : ");
					String input = in.nextLine();
					WebElement dropdown = driver.findElement(By.cssSelector(".ng-pristine"));
					Thread.sleep(5000);
					dropdown.findElement(By.xpath("//option[. = '" + input + "']")).click();
					driver.findElement(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[6]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
							.click();
					WebElement clickDisable = null;
					Thread.sleep(5000);
					List<WebElement> nextClick = driver.findElements(By.xpath("//li[@class='pagination-next']"));
					
					do {
						System.out.println("PAGE NO : "+num++);
						System.out.println("=======================");
						List<WebElement> AllSitesTable = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[6]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
						for (WebElement webElement : AllSitesTable) {
						    String orgName = webElement.findElement(By.xpath("td[2]/div")).getText();
						    Thread.sleep(2000);
						    System.out.println(orgName);
							allOrgList.add(orgName);
							int parsedJobAVG = ukFormat.parse(webElement.findElement(By.xpath("td[6]/div")).getText()).intValue();
							parsedJobavg.add(parsedJobAVG);
							float runtimeAVG = ukFormat.parse(webElement.findElement(By.xpath("td[10]/div")).getText()).floatValue();
							RunTimebavg.add(runtimeAVG);
							Thread.sleep(2000);
						 }
						
						nextClick.get(1).click();	
						Thread.sleep(5000);
						try {
							clickDisable = driver.findElement(By.xpath("//*[@class='pagination-next disabled']"));
							System.out.println("PAGE NO : "+num++);
							System.out.println("=======================");
							List<WebElement> AllSitesTable1 = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[6]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div[1]/table/tbody/tr"));
							for (WebElement webElement : AllSitesTable1) {
							    String orgName = webElement.findElement(By.xpath("td[2]/div")).getText();
							    System.out.println(orgName);
								allOrgList.add(orgName);
								int parsedJobAVG = ukFormat.parse(webElement.findElement(By.xpath("td[6]/div")).getText()).intValue();
								parsedJobavg.add(parsedJobAVG);
								float runtimeAVG = ukFormat.parse(webElement.findElement(By.xpath("td[10]/div")).getText()).floatValue();
								RunTimebavg.add(runtimeAVG);
								Thread.sleep(5000);
							}
						}catch(Exception e) {
//								System.out.println(e);
							}			    
					}while(clickDisable == null);
				}while(inc<1);
				System.out.println("RunTimebavg : "+RunTimebavg);
			} catch (Exception e) {
				System.out.println(e);
			}
			siteRunHistory_6(fw,allOrgList,parsedJobavg,RunTimebavg);
	}

	public void siteRunHistory_6(FileWriter fw,ArrayList<String> allOrgList,ArrayList<Integer> parsedJobavg,ArrayList<Float> RunTimebavg) {
		ArrayList<Integer> AvgParsedJobCount = new ArrayList<Integer>();
		ArrayList<Float> ACAvgRunTime = new ArrayList<Float>();
		float sum_run_time=0,avg_run_time;
		int sum =0,avg;
		try {
			
			System.out.println("6. Site Run History");
			System.out.println("________________________________________________________________");
			WebElement dropdown = driver.findElement(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[7]/div[1]/dashboard-widget/div/div/div[1]/div[2]/parameters/div/div/parameter-input/span/span/query-based-parameter/select"));
			Thread.sleep(5000);
			for (int i = 0; i < allOrgList.size(); i++) {
				try {
					System.out.println(allOrgList.get(i));
				dropdown.findElement(By.xpath("//option[. = '" + allOrgList.get(i) + "']")).click();
				driver.findElement(By.xpath(
						"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[7]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
						.click();
				Thread.sleep(5000);
				List<WebElement> SiteRunHistory = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[7]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
				ArrayList<Float> runtimes = new ArrayList<Float>();
				ArrayList<Integer> ParsedJobCount = new ArrayList<Integer>();
				for (WebElement webElement : SiteRunHistory) {
						String PJobCount = webElement.findElement(By.xpath("td[6]/div")).getText();
						ParsedJobCount.add(ukFormat.parse(PJobCount).intValue());
						String runTime = webElement.findElement(By.xpath("td[8]/div")).getText();
						runtimes.add(ukFormat.parse(runTime).floatValue());
					}
					System.out.println(ParsedJobCount);
					if(runtimes.size()<=5 && ParsedJobCount.size()<=5) {
						sum_run_time=0;	
						sum=0;
						for(int j = 1; j<ParsedJobCount.size();j++) {
							sum = sum+ParsedJobCount.get(j);
							sum_run_time = sum_run_time+runtimes.get(j);
						}
					   avg = (sum/5);
					   avg_run_time = (sum_run_time/5);
					   AvgParsedJobCount.add(avg);
					   ACAvgRunTime.add(avg_run_time);
					}
					else {
						sum_run_time=0;	
						sum=0;
						for(int p = 1; p<6;p++) {
							sum = sum+ParsedJobCount.get(p);
							sum_run_time = sum_run_time+runtimes.get(p);
						}
					   avg = (sum/5);
					   avg_run_time = (sum_run_time/5);
					   AvgParsedJobCount.add(avg);
					   ACAvgRunTime.add(avg_run_time);
					}
					Thread.sleep(5000);	
				}catch (Exception e) {
					continue;
				}
			}
			
			System.out.println(parsedJobavg);
			System.out.println(AvgParsedJobCount);
			for (int i = 0; i < parsedJobavg.size(); i++) {
				fw.append(allOrgList.get(i)+',');
				fw.append(String.valueOf(parsedJobavg.get(i))+',');
				fw.append(String.valueOf(AvgParsedJobCount.get(i))+',');
				if((parsedJobavg.get(i)).equals(AvgParsedJobCount.get(i))) {
					fw.append("PASSED"+',');
					System.out.println("PASSED");
					System.out.println(parsedJobavg.get(i) +" : "+AvgParsedJobCount.get(i));
				}
				else {
					fw.append("FAILED"+',');
					System.out.println("FAILED");
					System.out.println(parsedJobavg.get(i) +" : "+AvgParsedJobCount.get(i));
				}
				fw.append(String.valueOf(RunTimebavg.get(i))+',');
				fw.append(String.valueOf(ACAvgRunTime.get(i))+',');
				if((RunTimebavg.get(i)).equals(ACAvgRunTime.get(i))) {
					fw.append("PASSED"+'\n');
					System.out.println("PASSED");
					System.out.println(RunTimebavg.get(i) +" : "+ACAvgRunTime.get(i));
				}
				else {
					fw.append("FAILED"+'\n');
					System.out.println("FAILED");
					System.out.println(RunTimebavg.get(i) +" : "+ACAvgRunTime.get(i));
				}
			}
		} catch (Exception e) {
			log.warn("Failed to Parse : ",e);
		}
		ragStatusForParsedJob_7(fw);
	}

	public void ragStatusForParsedJob_7(FileWriter fw) {
		try {
			fw.append("  \n\n\t\tRAG STATUS FoR PARSED JOB\t\t"+'\n'+'\n');
			fw.append("Test Case");
			fw.append(',');
			fw.append("Test Data");
			fw.append(',');
			fw.append("Pass/Fail");
			fw.append('\n');
			System.out.println("7. RAG Status For Parsed Job");
			System.out.println("________________________________________________________________");
			driver.findElement(By.xpath(
					"/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[8]/div[1]/dashboard-widget/div/div/div[3]/a/span"))
					.click();
		    Thread.sleep(5000);
		    int i =1;
		    List<WebElement> ragSts4ParsedJob = driver.findElements(By.xpath("/html/body/section/app-view/div/dashboard-page/div/div[2]/div/div[8]/div[1]/dashboard-widget/div/div/div[2]/visualization-renderer/div/grid-renderer/dynamic-table/div/table/tbody/tr"));
		    for (WebElement webElement : ragSts4ParsedJob) {
				String  r_total = webElement.findElement(By.xpath("td[2]/div")).getText();
				String  r_red = webElement.findElement(By.xpath("td[3]/div")).getText();
				String  r_amber = webElement.findElement(By.xpath("td[4]/div")).getText();
				String  r_green = webElement.findElement(By.xpath("td[5]/div")).getText();
				int total_actual = ukFormat.parse(r_red).intValue()+ukFormat.parse(r_amber).intValue()+ukFormat.parse(r_green).intValue();
				i++;
				if(i<3) {
					System.out.println("1 Week : Total : "+r_total+" Red : "+r_red+" Amber : "+r_amber+" Green : "+r_green );
					fw.append("Total of 1 week "+',');
					fw.append(r_total+',');
					if(Integer.valueOf(r_total)==total_actual) {
						fw.append("PASSED"+'\n');
					}
					else {
						fw.append("FAILED"+'\n');
					}
					
				}
				else if(i>2 && i<4) {
					System.out.println("4 Week : Total : "+r_total+" Red : "+r_red+" Amber : "+r_amber+" Green : "+r_green );
					fw.append("Total of 4 week "+',');
					fw.append(r_total+',');
					if(Integer.valueOf(r_total)==total_actual) {
						fw.append("PASSED"+'\n');
					}
					else {
						fw.append("FAILED"+'\n');
					}
				}
				else {
					System.out.println("12 Week : Total : "+r_total+" Red : "+r_red+" Amber : "+r_amber+" Green : "+r_green );
					fw.append("Total of 12 week "+',');
					fw.append(r_total+',');
					if(Integer.valueOf(r_total)==total_actual) {
						fw.append("PASSED"+'\n');
					}
					else {
						fw.append("FAILED"+'\n');
					}
				}
			}
		    fw.close();
		}catch (Exception e) {
			System.out.println(e);
		}	
	}
}
