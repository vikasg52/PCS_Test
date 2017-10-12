
/**
* <h1> Utility Functions </h1>
* This is the heart of the ATM framework , it controls and talks to all other components. 
* <p>
* @author  Chaithanya & Vikas
* @version 1.0
* @since   2017-05-16
*/

package pcs.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.testng.ITestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Utilityfuntions {

	// Testsuite.xlsx declaration
	public static XLfunctions objTestSuiteXLS;

	// XMLReader.xml declaration
	public static XMLReader objXMLReader = new XMLReader(System.getProperty("user.dir") + "/config/config.xml");
	public static List<Hashtable<String, String>> config = objXMLReader.getDataAsList("Framework");

	// Logs & Report declaration
	public static ExtentReports objExtent_Report, objExtent_Log;
	public static ExtentTest objTest_Report, objTest_Log;
	public static ITestResult objResult;

	// Declarations
	public static int intCurrentTestsuite_Rowcount;
	public static int intCurrentTestcaseRowNum;
	public static String strCurrentTestCaseName;
	public static String strCurrentKeywordName;
	public static String strCurrentParameterString;
	public static String strCurrentKeyword_Exestatus;
	public static String strCurrentTestCase_Exestatus;

	public static String[] strOriginal; // for parameterSplit function
	
	
	public static String[] parameterSplit(String strParamValue) {
//		try{

//		objTest_Log.log(LogStatus.INFO, "Reached parameter split function with parameters : " + strParamValue);
		System.out.println("Reached parameter split function with parameters : " + strParamValue);
		// paramValue will have parameter list from excel and it will be split
		// by ";"
		String[] strParameters = strParamValue.split("~");

		// This array holds internal of parameters with split by ":-"
		String[] strOriginal = new String[strParameters.length];

		for (int intParamcounter = 0; intParamcounter < strParameters.length; intParamcounter++) {

			String[] strFinalParam = strParameters[intParamcounter].trim().split(":-");
			// System.out.println(strFinalParam[1]);
			strOriginal[intParamcounter] = strFinalParam[1].trim();
//			System.out.println("Successfully parameter are split and loading to array..");
			
		 }return strOriginal;
//		}catch(Throwable e){
		
	}

	
	
	public static void testcaseExecutor_Excel(String strTestCaseName, ExtentTest objTest_Report, ExtentTest objTest_Log)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {

		objTest_Report.log(LogStatus.INFO, "Starting test execution for :" + strTestCaseName);
		objTest_Log.log(LogStatus.INFO, "Starting test execution for :" + strTestCaseName);

		// Reading Test suite from respective location
//		objTestSuiteXLS = new XLfunctions(
//				System.getProperty("user.dir") + "/Keyword_Engine/KWE_Cashkaro_Smoke_Suite.xlsx");
//		intCurrentTestcaseRowNum = objTestSuiteXLS.getCellRowNum(Config.strExe_TestSuite_Sheetname,
//				Config.strTestCase_ColNam, strTestCaseName);
//
//		// Iteration in Test suite across all the rows
//		for (int intCounter = intCurrentTestcaseRowNum; intCounter <= intCurrentTestcaseRowNum + 15; intCounter++) {
//
//			// Getting Keywords Name from the Test suite
//			strCurrentKeywordName = objTestSuiteXLS.getCellData(Config.strExe_TestSuite_Sheetname, 2, intCounter)
//					.trim();
//			strCurrentParameterString = objTestSuiteXLS.getCellData(Config.strExe_TestSuite_Sheetname, 3, intCounter)
//					.trim();
//
//			if (strCurrentKeywordName.trim().equals(Config.strExe_End_Keywordtname)) {
//				break;
//			}
//
//			if (strCurrentKeywordName.trim().equals(Config.strExe_Start_Keywordtname)) {
//
//				// Getting TestCaseName & its Execution status from the Test
//				// suite
//				strCurrentTestCaseName = objTestSuiteXLS.getCellData(Config.strExe_TestSuite_Sheetname, 1, intCounter)
//						.trim();
//				strCurrentTestCase_Exestatus = objTestSuiteXLS
//						.getCellData(Config.strExe_TestSuite_Sheetname, 5, intCounter).trim();
//				objTest_Report.log(LogStatus.INFO,
//						strCurrentTestCaseName + ": Test case execution status set as " + strCurrentTestCase_Exestatus);
//				objTest_Log.log(LogStatus.INFO,
//						strCurrentTestCaseName + ": Test case execution status set as " + strCurrentTestCase_Exestatus);
//
//			} else {
//
//				// Checking for the execution status of TESTCASES
//				if (strCurrentTestCase_Exestatus.trim().equals(Config.strYes_Status)) {
//
////					strCurrentKeyword_Exestatus = objTestSuiteXLS.getCellData(Config.strExe_TestSuite_Sheetname, 4,
//							intCounter);
//					// objAppLogs.debug( " Above Test case execution status is"+
//					// strCurrentTestCase_Exestatus + " : so that we will
//					// proceed further with execution");
//
//					// Checking for the execution status of KEYWORDS
//					if (strCurrentKeyword_Exestatus.trim().equals(Config.strYes_Status)) {
//
//						objTest_Report.log(LogStatus.INFO, strCurrentKeywordName + ": Keyword Status set as "
//								+ strCurrentKeyword_Exestatus + " : so that we will proceed further with execution");
//						objTest_Log.log(LogStatus.INFO, strCurrentKeywordName + ": Keyword Status set as "
//								+ strCurrentKeyword_Exestatus + " : so that we will proceed further with execution");
//
//						// Connecting to specific class file and getting the
//						// method with parameters
//						Method objGet_ExeMethodName = Class.forName(Config.getMethods_Classpath)
//								.getMethod(strCurrentKeywordName, String.class);
//						objGet_ExeMethodName.invoke(objGet_ExeMethodName, strCurrentParameterString);
////
////						// Just Logging the info
////						objTest_Report.log(LogStatus.INFO, "Execution completed for " + strCurrentKeywordName
//								+ " with parameters " + strCurrentParameterString);
//						objTest_Log.log(LogStatus.INFO, "Execution completed for " + strCurrentKeywordName
//								+ " with parameters " + strCurrentParameterString);
//						objTest_Report.log(LogStatus.INFO,
//								"************************************************************************************************************************");
//						objTest_Log.log(LogStatus.INFO,
//								"************************************************************************************************************************");
//
//					} else { // If KEYWORD execution status set as NO
//
//						// Just Logging the info
//						objTest_Report.log(LogStatus.INFO, strCurrentKeywordName
//								+ ": Keyword execution status set as NO, so skipping this and proceeding further");
//						objTest_Log.log(LogStatus.INFO, strCurrentKeywordName
//								+ ": Keyword execution status set as NO, so skipping this and proceeding further");
//						objTest_Report.log(LogStatus.INFO,
//								"************************************************************************************************************************");
//						objTest_Log.log(LogStatus.INFO,
//								"************************************************************************************************************************");
//
//					} // execution status of KEYWORDS closing
//
//				} else { // If TESTCASES execution status set as NO
//
//					// Just Logging the info
//					objTest_Report.log(LogStatus.INFO,
//							strCurrentTestCaseName
//									+ " Test case execution status set as NO ,so skipping this for keyword : "
//									+ strCurrentKeywordName);
					objTest_Log.log(LogStatus.INFO,
							strCurrentTestCaseName
									+ " Test case execution status set as NO ,so skipping this for keyword : "
									+ strCurrentKeywordName);
					objTest_Report.log(LogStatus.INFO,
							"************************************************************************************************************************");
					objTest_Log.log(LogStatus.INFO,
							"************************************************************************************************************************");

//				} // execution status of TESTCASES closing
//
//			} // TC Start if closing
//
//		} // for loop closing

	}// testcaseExecutor_Excel method closing

	
	
	public static void datstampFolderCreation(ExtentTest objTest_Report, ExtentTest objTest_Log) {

		File newfile = null;
		
		//  created date folder with time stamp in results folder 
		Date objNewDateFolder = new Date();
		SimpleDateFormat dateFormat_Folder = new SimpleDateFormat("yyyy_MM_dd");
		File file = new File(System.getProperty("user.dir") + "/results/" + dateFormat_Folder.format(objNewDateFolder));
		file.mkdir();
		String strDatenow = dateFormat_Folder.format(objNewDateFolder);
		objTest_Log.log(LogStatus.INFO, "Successfully created date folder with name : " + strDatenow);

	    //  created result file with time stamp in date folder at results folder 
		Date objNewTimeFile = new Date();
		SimpleDateFormat dateFormat_File = new SimpleDateFormat("yyyy_MM_dd HH_MM_SS");
		newfile = new File(System.getProperty("user.dir") + "/results/" + strDatenow + "\\" + dateFormat_File.format(objNewTimeFile) + ".html");
		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(newfile))) {

			objTest_Log.log(LogStatus.INFO, "Successfully created result in result folder with name : " + dateFormat_File.format(objNewTimeFile));
			objTest_Report.log(LogStatus.PASS,  "Successfully created result in result folder with name : " + dateFormat_File.format(objNewTimeFile));

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	
	public static String getDataFromExcel(String strTestCaseName) {
	
//		objTest_Log.log(LogStatus.INFO, "Getting data from excel for the test caser :" + strTestCaseName);
		System.out.println("Getting data from excel for the test case :" + strTestCaseName);
		// Reading Test suite from respective location
		objTestSuiteXLS = new XLfunctions(System.getProperty("user.dir") + "/inputs/Input.xlsx");
		intCurrentTestcaseRowNum  = objTestSuiteXLS.getCellRowNum(config.get(0).get("strExe_TestSuite_Sheetname").trim(),config.get(0).get("strTestCase_ColName").trim(), strTestCaseName); // find the row of tesst case
		strCurrentParameterString = objTestSuiteXLS.getCellData(config.get(0).get("strExe_TestSuite_Sheetname").trim(), 2, intCurrentTestcaseRowNum).trim(); // retrive the values of test cases from excel
		
//		objTest_Report.log(LogStatus.PASS, "Successfully fetched parameters from excel for the test case :"+ strCurrentParameterString +" as : " + strCurrentParameterString);
//		objTest_Log.log(LogStatus.INFO, "Successfully fetched parameters from excel for the test case :"+ strCurrentParameterString +" as : " + strCurrentParameterString);
		System.out.println("Successfully fetched parameters from excel for the test case :"+ strCurrentParameterString +" as : " + strCurrentParameterString);
		//System.out.println("Successfully fetched parameters from excel for the test case :"+ strCurrentParameterString +" as : " + strCurrentParameterString);
		
		return strCurrentParameterString;

	}// getDataFromExcel method closing

	
	
	/**********************************************************************************************Functions are ending here ***********************************************************************************************************/
		
	
	// public static void main (String arg[]) throws
	// ParserConfigurationException, SAXException, IOException{
	//
	////// datstampFolderCreation();
	//
	//// System.out.println(data);
	// System.out.println(config.get(0).get("salary"));
	//
	// }
	

}// Utility functions closing

