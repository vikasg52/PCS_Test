package pcs.core;
import org.testng.annotations.DataProvider;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
public class PCS_DataProviders {
	
	// Logs & Report declaration
	public static ExtentReports objExtent_Report, objExtent_Log;
	public static ExtentTest objTest_Report, objTest_Log;

	@DataProvider(name = "loginVerification")
	public static Object[][] credentials() {
	   // The number of times data is repeated, test will be executed the same no. of times
         // Here it will execute 7 times
         return new Object[][] { 
       {"", "","Please provide a valid password (6-32 chars)"}, 
       {"testuser_1@gmail.com","master","wrong credentials/password"},
       {"testuser_2@gmail.com","Tes","wrong credentials/password"},
       {"testuser_3@","Tes","wrong credentials/password"},
       {"***", "master","wrong credentials/password"},
       {"---", "master","wrong credentials/password"},
       {"null", "null","Please provide a valid password (6-32 chars)"},
       {"vikasgarg.mgl@gmail.com","master1","Vikas Kumar"},
        };
 
   }
	
@DataProvider(name = "apitestdata")
public static Object[][] Test_Data() {
   // The number of times data is repeated, test will be executed the same no. of times
     // Here it will execute 7 times
     return new Object[][] { {"t","Spiderman","y"," ","plot","short","r","json",200}, 
    		                 {"t","","","r","plot","short","r","json",200},
    		                 {"","",""," ","","","","",400},
    		                 {"t","Spiderman",""," ","","","","",200},
    		                 {"","","y"," ","","","","",400},
    		                 {"","",""," ","plot","short","","",200},
    		                 {"","",""," ","","","r","json",200},
                           };

}
}

