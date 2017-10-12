package pcs.pom.gui;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;

import pcs.core.CommonFunction;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * This class contains: Page Objects and Methods for login
 * @author Vikas
 */
public class Login_POM {
	
	// Extent reports
	static ExtentReports objExtent_Report, objExtent_Log;
	static ITestResult objResult;
	static ExtentTest objTest_Report, objTest_Log;	 
	// Object creation & referring other classes
	WebDriver objDriver = null; // Web driver obj

/**This is default constructor which initializes driver and will create all web element **********************/
	public Login_POM(WebDriver driver) {
		this.objDriver = driver;
		PageFactory.initElements(driver, this);
	}

 /** PAGE OBJECTS identification with locators*/
@FindBy(className = "signin")
private WebElement Login_Link;

@FindBy(xpath="//div[@data-type='basic-login']")
private WebElement Login_With_Email_Btn;

@FindBy(id="username")
private WebElement User_Name;

@FindBy(id = "password")
private WebElement User_Password;

@FindBy(xpath = "//div[@data-type='login-btn']")
private WebElement Login_Button;

@FindBy(xpath="//div[@class='login-input-box error']//p[@class='error']")
private WebElement pwd_error;
@FindBy(xpath="(//div[@class='mian-field-box']/p)[1]")
private WebElement login_error;

@FindBy(linkText="Vikas Kumar")
private WebElement logged_In;

	/**
	 * Page Methods Declaration and definition
	 * @throws InterruptedException 
	 */
		
	public String LogIn_Test(String user_Name,String Password,ExtentTest objTest_Report, ExtentTest objTest_Log) throws InterruptedException {
		CommonFunction.WaitClass.waitforElement(Login_Link, objTest_Report, objTest_Log);
		
		CommonFunction.ClickClass.click(Login_Link, objTest_Report, objTest_Log);
		
	    CommonFunction.BrowserClass.switchToChildWindow(objDriver, objTest_Report, objTest_Log);
	    
	    CommonFunction.ClickClass.click(Login_With_Email_Btn, objTest_Report, objTest_Log);
	    CommonFunction.BrowserClass.clear(User_Name, objTest_Report, objTest_Log);
	    CommonFunction.BrowserClass.inputText(User_Name, user_Name, objTest_Report, objTest_Log);
	    CommonFunction.BrowserClass.clear(User_Password, objTest_Report, objTest_Log);
		CommonFunction.BrowserClass.inputText(User_Password, Password, objTest_Report, objTest_Log);
		CommonFunction.ClickClass.clickByJS(Login_Button, objTest_Report, objTest_Log);
		Thread.sleep(4000L);
		if(login_error.isDisplayed())
			{System.out.println(login_error.getText().trim());
		    return login_error.getText().trim();}
		if(pwd_error.isDisplayed())
			{System.out.println(pwd_error.getText().trim());
			return pwd_error.getText().trim();}
			if(logged_In.isDisplayed());
			{System.out.println(logged_In.getText().trim());
			return logged_In.getText().trim();}	
	}
}
