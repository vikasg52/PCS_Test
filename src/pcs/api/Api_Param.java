package pcs.api;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pcs.core.PCS_DataProviders;


public class Api_Param {

	/****************************************************************Common object and variable declaration section*************************************************************************************/
	 static ExtentReports objExtent_Report,objExtent_Log;
     static ITestResult objResult;
     static ExtentTest objTest_Report=null,objTest_Log=null;
	 
	 /******* Extent Reports and common object creation section*********/
		 
	@BeforeTest
	public void Api_setUp() {	
		Date objNewDateFolder = new Date();
		SimpleDateFormat dateFormat_Folder = new SimpleDateFormat("yyyy_MM_dd");
		File file = new File(System.getProperty("user.dir") + "/reports/" + dateFormat_Folder.format(objNewDateFolder));
		file.mkdir();
		String strDatenow = dateFormat_Folder.format(objNewDateFolder);
		// created result file with time stamp in date folder at results folder
		Date objNewTimeFile = new Date();
		SimpleDateFormat dateFormat_File = new SimpleDateFormat("yyyy_MM_dd @ HH_mm_SS");
		objExtent_Report = new ExtentReports(System.getProperty("user.dir") + "/reports/apitest/" + strDatenow + "/"+ dateFormat_File.format(objNewTimeFile) + ".html");
		objExtent_Log = new ExtentReports(System.getProperty("user.dir") + "/logs/api_logs/Api_TestLogs.html");
		objTest_Report = objExtent_Report.startTest("Api_setUp");
	    objTest_Log = objExtent_Log.startTest("Api_setUp");
	    objExtent_Report.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
		objExtent_Log.loadConfig(new File(System.getProperty("user.dir") + "/extent-config_logs.xml"));
		// Object creation of helper classes
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
@Test(priority=1)
	public void GetMethod_No_Params(){
		objTest_Report = objExtent_Report.startTest("GetMethod_No_Params");
		objTest_Log = objExtent_Log.startTest("GetMethod_No_Params");
		objTest_Report.log(LogStatus.INFO, "API Get without parm started.");
		Response response=RestAssured.get("http://restapi.demoqa.com/utilities/weather/city/Delhi");
	    System.out.println(response.asString());
		System.out.println(response.getStatusCode());
		try{
		Assert.assertEquals(response.getStatusCode(), 200);
		objTest_Report.log(LogStatus.PASS, "PASSED..");
		} catch (Throwable e){
			objTest_Report.log(LogStatus.FAIL, "FAILED..");
		}
		
		objExtent_Log.endTest(objTest_Log);
	       objTest_Log.log(LogStatus.INFO, "Ending logging..");
	       objExtent_Log.flush();
	       objExtent_Report.flush();
	       objTest_Report.log(LogStatus.INFO, "Closed test..");
	
	}
	
	/**
	 * Signature is: "http://http://www.omdbapi.com/?t=Spiderman&y=&plot=short&r=json"
	 */
		@Test(priority=2,dataProviderClass = PCS_DataProviders.class, dataProvider = "apitestdata")
		public void Get_With_Param(String key1, String Value1, String key2, String Value2, String key3, String Value3, String key4, String Value4, int Response){
			objTest_Report = objExtent_Report.startTest("Get_With_Param");
			objTest_Log = objExtent_Log.startTest("Get_With_Param");
			objTest_Log.log(LogStatus.INFO, "API Get with parm started.");	
			RestAssured.given().param(key1, Value1)
			                   .param(key2,Value2)
			                   .param(key3, Value3)
			                   .param(key4, Value4);
			RestAssured.when().get("http://www.omdbapi.com/")
			           .then().statusCode(Response);
	           
			   objExtent_Log.endTest(objTest_Log);
		       objTest_Log.log(LogStatus.INFO, "Ending logging..");
		       objExtent_Log.flush();
		       objExtent_Report.flush();
		       objTest_Report.log(LogStatus.INFO, "Closed test..");	                   
		}
		
	}
