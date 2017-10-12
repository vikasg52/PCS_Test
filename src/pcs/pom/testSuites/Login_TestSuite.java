package pcs.pom.testSuites;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pcs.core.CommonFunction;
import pcs.core.PCS_DataProviders;
import pcs.pom.gui.Login_POM;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Login_TestSuite {
	
   /****************************************************************Common object and variable declaration section*************************************************************************************/
	 static WebDriver objDriver=null;
	  static ExtentReports objExtent_Report,objExtent_Log;
      static ITestResult objResult;
      static ExtentTest objTest_Report=null,objTest_Log=null;
	  AjaxElementLocatorFactory objPageFactoryWait;
	  CommonFunction objCL=null;
	  Login_POM login=null;
	  //String browser; 
	  String URL,userName,password;

	 /**************************************************************** Extent Reports and common object creation section*************************************************************************************/
		 
	@BeforeTest
	@Parameters({"browser","URL"})
	public void setUp(String browser, String URL) {	
		Date objNewDateFolder = new Date();
		SimpleDateFormat dateFormat_Folder = new SimpleDateFormat("yyyy_MM_dd");
		File file = new File(System.getProperty("user.dir") + "/reports/" + dateFormat_Folder.format(objNewDateFolder));
		file.mkdir();
		String strDatenow = dateFormat_Folder.format(objNewDateFolder);
		// created result file with time stamp in date folder at results folder
		Date objNewTimeFile = new Date();
		SimpleDateFormat dateFormat_File = new SimpleDateFormat("yyyy_MM_dd @ HH_mm_SS");
		objExtent_Report = new ExtentReports(System.getProperty("user.dir") + "/reports/guitest/" + strDatenow + "/"+ dateFormat_File.format(objNewTimeFile) + ".html");
		objExtent_Log = new ExtentReports(System.getProperty("user.dir") + "/logs/UI_logs/UI_AutomationTestLogs.html");
		objTest_Report = objExtent_Report.startTest("setUp");
	    objTest_Log = objExtent_Log.startTest("setUp");
	    objExtent_Report.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
		objExtent_Log.loadConfig(new File(System.getProperty("user.dir") + "/extent-config_logs.xml"));
		// Object creation of helper classes
		objDriver = CommonFunction.BrowserClass.startDriver(browser, objTest_Report, objTest_Log);
		login= new Login_POM(objDriver);
		CommonFunction.BrowserClass.openURL(URL, objTest_Report, objTest_Log);
		PageFactory.initElements(objPageFactoryWait, this);
	}
	
	/** All common activities which are to be done after all tests are executed******/
	
	@AfterTest
	public void closeDriver(){
		objTest_Report = objExtent_Report.startTest("closeDriver");
		objTest_Log = objExtent_Log.startTest("closeDriver");
		objTest_Log.log(LogStatus.INFO, "Closing test..");
	
		objExtent_Log.endTest(objTest_Log);
        objTest_Log.log(LogStatus.INFO, "Ending logging..");
        objExtent_Log.flush();
        objExtent_Report.flush();
        objDriver.quit();
        objTest_Report.log(LogStatus.INFO, "Closed test..");
    	
	}
	
	/********** All common activities which are to be done after each test method****/
	@AfterMethod
	public static void getResult(ITestResult objResult) throws IOException{

		if (objResult.getStatus() == ITestResult.FAILURE){
			objTest_Report.log(LogStatus.FAIL, objResult.getThrowable());
			objTest_Report.log(LogStatus.FAIL, "screenshot below : "+ objTest_Report);
			objTest_Log.log(LogStatus.INFO, "Error Screenshot Captured..");
		}
		if (objResult.getStatus() == ITestResult.SKIP){
			objTest_Log.log(LogStatus.WARNING, "Warning thrown..");
			objTest_Log.log(LogStatus.SKIP, "objTest_Report is skipped");
			objTest_Report.log(LogStatus.WARNING, "Warning thrown..");
			objTest_Report.log(LogStatus.SKIP, "objTest_Report is skipped");
		}
		objExtent_Report.endTest(objTest_Report);
	}

/**Extent Reports Ends here*/

		@Test(dataProviderClass = PCS_DataProviders.class, dataProvider = "loginVerification")
		public void Login_Test(String userName,String password, String Expected) throws Throwable{
			try{
				objTest_Report = objExtent_Report.startTest("Login_Test");
				objTest_Log = objExtent_Log.startTest("Login_Test");
				objTest_Log.log(LogStatus.INFO, "Login Test Started..");
				String Actual=login.LogIn_Test(userName, password,objTest_Report, objTest_Log);
				SoftAssert as = new SoftAssert();
				as.assertEquals(Actual, Expected, "Failed");
				objTest_Report.log(LogStatus.PASS, "Login is working");
				as.assertAll();
				
			} catch(Throwable e){
				objTest_Report.log(LogStatus.FAIL, "Login Failed Due to:"+e.getMessage());
				
				objTest_Log.log(LogStatus.FAIL, "Login Failed Due to:"+e.getCause());
				throw(e);
		
			}
	} // closing LoginVerification_HomePage

}