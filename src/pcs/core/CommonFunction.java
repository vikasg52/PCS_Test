package pcs.core;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Written by Vikas Garg on 10-10-2017
 * This class handles all browser related common operations
 */
public class CommonFunction {

	public static WebDriver objDriver=null;
	public static DesiredCapabilities objCapabilities =null;
	public static ITestResult objResult;
	public static ExtentReports objExtent_Report,objExtent_Log;
	public static ExtentTest objTest_Report,objTest_Log;
	public static String pageTitle=null;
	static String dest=null;
	public static String strCurrentURL=null;

	public static class BrowserClass{
		/**
		 * This method is used to open the specified browser
		 * @param browser
		 * @param objTest_Report
		 * @param objTest_Log
		 * @return
		 */
		public static WebDriver startDriver(String browser,ExtentTest objTest_Report,ExtentTest objTest_Log  ){

			try{
				switch (browser) {
				case "firefox":
					objCapabilities= DesiredCapabilities.firefox();
					objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "FIREFOX");
					System.setProperty("webdriver.gecko.driver","./jars/geckodriver.exe");
					objTest_Log.log(LogStatus.INFO,"Opening firefox ...");
					objDriver= new FirefoxDriver(objCapabilities);
					objTest_Report.log(LogStatus.PASS,"Firefox opened ...");
					objDriver.manage().window().maximize();
					//objDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
					return objDriver;

				case "chrome":
					System.setProperty("webdriver.chrome.driver","./jars/chromedriver.exe");
					ChromeOptions options = new ChromeOptions();
					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("profile.default_content_settings.popups", 0);
					options.setExperimentalOption("prefs", prefs);
					options.addArguments("disable-infobars");
					objCapabilities = DesiredCapabilities.chrome();
					objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "CHROME");
					objCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
					objTest_Log.log(LogStatus.INFO,"Opening chorme ...");
					objDriver = new ChromeDriver(objCapabilities);
					objTest_Report.log(LogStatus.PASS,"Chrome opened...");
					objDriver.manage().window().maximize();
					objDriver.manage().timeouts().implicitlyWait(14, TimeUnit.SECONDS);
					return objDriver;

				case "IE":
					objCapabilities = DesiredCapabilities.internetExplorer();
					objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "internetExplorer");
					System.setProperty("webdriver.ie.driver","./jars/IEDriverServer.exe");
					objTest_Log.log(LogStatus.INFO,"Opening InterNet Explorer ...");
					objDriver = new InternetExplorerDriver(objCapabilities);
					objTest_Report.log(LogStatus.PASS,"IE opened ...");
					objDriver.manage().window().maximize();
					objDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
					return objDriver;

				case "safari":
					//System.setProperty("webdriver.safari.objDriver", "./lib/SafariDriver.safariextz");
					objCapabilities = DesiredCapabilities.safari();
					objCapabilities.setCapability(CapabilityType.BROWSER_NAME, "SAFARI");
					objTest_Log.log(LogStatus.INFO,"Opening Safari ...");
					objDriver = new SafariDriver(objCapabilities);
					objTest_Report.log(LogStatus.PASS,"Safari opepened...");
					objDriver.manage().window().maximize();
					objDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
					return objDriver;


				}// Switch cases ends 

			}catch(WebDriverException e){

				objTest_Report.log(LogStatus.FAIL,"browser could not be launched due to:"+ e.getCause());
				objTest_Log.log(LogStatus.FAIL,"browser could not be launched due to:"+ e.getCause());

			} return objDriver;

		}
		
		public static void clear(WebElement element,ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				objTest_Log.log(LogStatus.INFO,"Clearing Text from:"+element );
				element.clear();
				objTest_Report.log(LogStatus.PASS,"Cleared Text from:"+element );

			}catch (Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Not able clear text from"+element+"due to :"+ e.getCause());
				objTest_Log.log(LogStatus.FAIL,"Not able clear text from"+element+"due to :"+ e.getCause());
			}
		} 
		public static void switchToChildWindow(WebDriver objDriver,ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				String MainWindow=objDriver.getWindowHandle();	
				Set<String> windows=objDriver.getWindowHandles();

				Iterator<String> i1=windows.iterator();		

				while(i1.hasNext())			
				{		
					String ChildWindow=i1.next();		
					if(!MainWindow.equalsIgnoreCase(ChildWindow))			
					{  	  
						objTest_Log .log(LogStatus.INFO,"Navigating to popup window");
						objDriver.switchTo().window(ChildWindow);	
						objTest_Report.log(LogStatus.PASS,"Navigated to popup window");						
					}	
				}

							}catch(Throwable e){
					objTest_Report.log(LogStatus.FAIL,"Not Navigated to child window due to:"+e.getCause());
					objTest_Log.log(LogStatus.WARNING,"Not Navigated to child window due to:"+e.getCause());
				}

		}
	/**
	 * Switched to the Parent Window
	 */
		public static void switchToParentWindow(ExtentTest objTest_Report,ExtentTest objTest_Log){
			String MainWindow=objDriver.getWindowHandle();		
			objDriver.switchTo().window(MainWindow);
			objTest_Report.log(LogStatus.PASS,"Switched to main window");
		}
		
		public static void inputText(WebElement element, String keys,ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				WaitClass.waitforElement(element, objTest_Report, objTest_Log);
				element.sendKeys(keys);
				objTest_Report.log(LogStatus.PASS,"Input successfull to:"+element);
			}catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Could not input the text due to:"+e.getCause());
				objTest_Log.log(LogStatus.WARNING,"Could not input the text due to:"+e.getCause());
			}
		}

		/**
		 * This method checks the response returned by a URI
		 * @param urlString
		 * @return
		 * @throws MalformedURLException
		 * @throws IOException
		 */
		public static int getResponseCode(String urlString) throws MalformedURLException, IOException{
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			return con.getResponseCode();
		}

		/**
		 * This Method opens a specified URL
		 * @param URL
		 * @param objTest_Report
		 * @param objTest_Log
		 */
		public static void openURL(String URL,ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				objTest_Log.log(LogStatus.INFO,"Navigating to : " + URL );
				objDriver.get(URL);
				objTest_Report.log(LogStatus.PASS,"Sucessfully navigated to : " + URL );

			}catch (Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Not able Navigate to "+URL+" due to :"+ e.getCause());
				objTest_Log.log(LogStatus.FAIL,"Not able Navigate to "+URL+" due to :"+ e.getCause());
			}
		} 

		/**
		 * This method do the page refresh operation
		 * @param objTest_Report
		 * @param objTest_Log
		 */
		public void refreshPage(ExtentTest objTest_Report,ExtentTest objTest_Log){
			try {
				objTest_Log.log(LogStatus.INFO,"Refreshing the page");
				objDriver.navigate().refresh();
				Thread.sleep(3000);
				objTest_Report.log(LogStatus.PASS,"Sucessfully refreshed page" );
			}catch (Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Not able refresh due to :"+ e.getCause());
				objTest_Log.log(LogStatus.WARNING,"Not able refresh due to :"+ e.getCause());
			}
		}	

		/** 
		 * This method is used to return the current page title
		 * @param objTest_Report
		 * @param objTest_Log
		 * @return
		 */
		public String getPageTitle(ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				objTest_Log.log(LogStatus.INFO,"getting page title ....");
				pageTitle=	objDriver.getTitle();	
				objTest_Report.log(LogStatus.PASS,"Page Title fetched ....");
			}catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Page title could not be fetched"+e.getCause());
				objTest_Log.log(LogStatus.WARNING,"Page title could not be fetched"+e.getCause());
			}			
			return pageTitle;
		}

		/**
		 * This method is used to fetch the current page URL
		 * @param objTest_Report
		 * @param objTest_Log
		 * @return
		 */
		public String getCurrentURL(ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				objTest_Log.log(LogStatus.INFO,"Fertching the page Title...");
				strCurrentURL= objDriver.getCurrentUrl();
				objTest_Report.log(LogStatus.PASS,"Url fetched..");
			}catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Page title could not fetched due to:"+e.getCause());
				objTest_Log.log(LogStatus.WARNING,"Page title could not fetched due to:"+e.getCause());
			}
			return strCurrentURL;
		}

		/**
		 * This method is used to close the browser instance
		 * @author Vikas, Chennai, Automation
		 * @param objTest_Report
		 * @param objTest_Log
		 */
		public void closeDriver(ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				if(objDriver!=null) {
					objTest_Log.log(LogStatus.INFO,"browser is in open state hence:Closing the browser");
					objDriver.quit();
					objTest_Report.log(LogStatus.PASS,"Browser Closed.");}
			}catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Browser could not be closed due to:"+e.getCause());
				objTest_Log.log(LogStatus.WARNING,"Browser could not be closed due to:"+e.getCause());
			}		
		}
		
		

	} // Close of Browser class 

	public static class ClickClass{
		/**
		 * Click methods and elements
		 * @param element
		 * @param objTest_Report
		 * @param objTest_Log
		 */
		public static void click (WebElement element,ExtentTest objTest_Report,ExtentTest objTest_Log) {
			try {

				objTest_Log .log(LogStatus.INFO,"Clicking ..." );
				element.click();
				objTest_Report.log(LogStatus.PASS,"Clicked successfully ..." );

			}catch (Throwable e){
					}
		}	
/**
 * Clicks on elements using javascript exceutor
 * @param element
 * @param objTest_Report
 * @param objTest_Log
 */
		public static void clickByJS(WebElement element,ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				objTest_Log.log(LogStatus.INFO,"clicking the element....");
			JavascriptExecutor js = (JavascriptExecutor)objDriver;
			js.executeScript("arguments[0].click();", element);
			objTest_Report.log(LogStatus.PASS,"element clicked");
			}catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL,"clould not click on the element due to:"+e.getCause());
				objTest_Log.log(LogStatus.WARNING,"clould not click on the element due to:"+e.getCause());
				
			}
		}



	} // Close of Click Class  

	/**=======================================Closing ClickClass function=================================================*/	
	public static class WaitClass{

		// waits for a particular element to become visible on the page
		public static void waitforElement(WebElement waitElement,ExtentTest objTest_Report,ExtentTest objTest_Log){
			WebDriverWait wait = new WebDriverWait(objDriver,90);
			wait.until(ExpectedConditions.visibilityOf(waitElement));

		}

		public static void waitforElementPresence(WebDriver driver,String waitElement,ExtentTest objTest_Report,ExtentTest objTest_Log){
			WebDriverWait wait = new WebDriverWait(driver,90);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitElement)));

		}
		public void waitforElementPresent(WebElement element, int timeInSeconds,ExtentTest objTest_Report,ExtentTest objTest_Log){
			try{
				AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(element, timeInSeconds);
				objTest_Log.log(LogStatus.INFO,"Waiting for :"+element);
				PageFactory.initElements(factory, this);
				objTest_Report.log(LogStatus.PASS,"Waiting for :"+element);
			}catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL,"Element could not be found due to:"+e.getCause());
				objTest_Log.log(LogStatus.WARNING,"Element could not be found due to"+e.getCause());

			}
		}

	} 
	/**=====================================Closing WaitClass functions======================================*/

	
	/**
	 * Verify elements available on the page
	 * @param element
	 * @param objTest_Report
	 * @param objTest_Log
	 * @return
	 * @throws NoSuchElementException
	 */
	public static boolean verifyElementPresent(WebElement element,ExtentTest objTest_Report,ExtentTest objTest_Log)throws NoSuchElementException{
		objTest_Log.log(LogStatus.INFO,"Verifying the element on the page.");
		if (element.isDisplayed()) {
			objTest_Report.log(LogStatus.PASS,"Element is detected: " + element);
			return true;
		} else{
			objTest_Report.log(LogStatus.FAIL,"Could not input the text");
			objTest_Log.log(LogStatus.WARNING,"Could not input the text");
			return false;
		}
	}
	/**
	 * Capture Screenshot
	 * @param objDriver
	 * @param screenShotName
	 * @param objTest_Report
	 * @param objTest_Log
	 * @return
	 * @throws IOException
	 */
	public static String captureScreenShot(WebDriver objDriver,String screenShotName,ExtentTest objTest_Report,ExtentTest objTest_Log) throws IOException{
		try{
			TakesScreenshot ts = (TakesScreenshot)objDriver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			objTest_Log.log(LogStatus.PASS,"Capturing the screenshot");
			dest = System.getProperty("user.dir")+"/errorscreenshots/"+screenShotName+".png";
			objTest_Report.log(LogStatus.PASS,"Screenshot Captured");
			File destination = new File(dest);
			FileUtils.copyFile(source, destination);        			
		}catch(Throwable e){
			objTest_Report.log(LogStatus.FAIL,"Screenshot not captured due to:" +e.getCause());
		}
		return dest;
	}
}
