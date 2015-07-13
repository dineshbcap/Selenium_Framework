package utils.seleniumutils;


import static org.testng.internal.EclipseInterface.ASSERT_LEFT;
import static org.testng.internal.EclipseInterface.ASSERT_MIDDLE;
import static org.testng.internal.EclipseInterface.ASSERT_RIGHT;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.verifier.exc.LoadingException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.Assert;

import utils.genericutility.Config;
import utils.genericutility.ExceptionHandler;
import com.thoughtworks.selenium.Selenium;

public class SeleniumWebDriver {

	/**
	 * Creating the web driver object to be used
	*/
	public static WebDriver driver;
	static WebDriverWait wait;
	private static String returnString="";
	private static Boolean result = true;
	public static int READYLOCATORWAITTIME=20;
	protected final Logger log = Logger.getLogger(getClass().getSimpleName());
		
	//Time to wait for page to load
	private static int secondsToWait = 20;
	
	/**
	 * Verify the presence of a text in the page.
	 * @param driver
	 * @param text
	 * @return true/false
	 * @throws IOException 
	 */
	public static boolean isTextPresent(String text){
		try {
			result= driver.getPageSource().contains(text);
			status = "Pass";
		}catch (Exception e) {
			status = "Fail";
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), text, EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), text, EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return result;
	}

	/**
	 * Verify the presence of a element in the page. 
	 * @param By
	 * @param text
	 * @return true/false
	 * @throws IOException 
	 */
	public static boolean isElementPresent(By element){
		boolean exists = true;
		try {
			exists = driver.findElement(element).isDisplayed();
			status = "Pass";
		} catch (Exception e) {
			
			status = "Fail";
			result = false;
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), element.toString().substring(element.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), element.toString().substring(element.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return exists;
	}

	/**
	 * Wait for page to load
	 */
	public static void causeMinorTimeDelay() {
		driver.manage().timeouts()
				.implicitlyWait(Config.DELAYTIME, TimeUnit.SECONDS);
	}

	/**
	 * Wait for page to load
	 */
	public static void causeTimeDelay() {
		try {
			int counter = 0;
			Thread.sleep(2000);
			while (true) {
				String ajaxIsComplete = ((JavascriptExecutor) driver)
						.executeScript("return Ajax.activeRequestCount")
						.toString();
				if (Integer.parseInt(ajaxIsComplete) == 0){
					break;
				}
				if (counter == 100){
					break;
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			status = "Fail";
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), EMPTY, EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}

	}

	/**
	 * To Capture Screenshot
	 * @param driver
	 * @return
	 */
	public static File takeScreenshot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	}

	public static boolean waitForElement(final By ajaxElementName, int timeOutValue){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, timeOutValue);
		try {
			ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return driver.findElement(ajaxElementName).isDisplayed();
				}
			};
			wait.until(e);
			status = "Pass";
		} catch (Exception e) {
			status = "Fail";
			result = false;
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), ajaxElementName.toString().substring(ajaxElementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), ajaxElementName.toString().substring(ajaxElementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return result;

	}
	
	/**
	 * To wait for element to load
	 * @param driver
	 * @return
	 */
	
	public static void waitForElementToLoad(Selenium selenium, String elementId)
			throws InterruptedException {
		int i = 0;
		while (!selenium.isElementPresent(elementId)) {
			i++;
			Thread.sleep(3000);
			if (i == 9) {
				Assert.fail("Time out :-CounldNotFind the Element With ID  : "+elementId);
				break;
			}
		}
	}
	
	/**
	 * To type text on the field
	 * @param driver
	 * @return
	 */

	public static boolean sendKeys(By elementLocator, String value) {
		try {
			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
			driver.findElement(elementLocator).clear();
			driver.findElement(elementLocator).sendKeys(value);
			status = "Pass";
		} catch (Exception e) {
			status = "Fail";
			result = false;
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), elementLocator.toString().substring(elementLocator.toString().indexOf(":")+2), value, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), elementLocator.toString().substring(elementLocator.toString().indexOf(":")+2), value, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return result;
	}
	protected boolean logTitleMessage(String message1){
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(message1,"", "", "title", "", getCallingMethodAndLineNumber()));
		log.info(message1);
		return true;
	}

	/**
	 * To click an element on the screen
	 * @param driver
	 * @return
	 */
	
	public static boolean click(final By ajaxElementName){
		try {
			waitForElement(ajaxElementName, Config.AVGWAITTIMEFORELEMENT);
			if (driver.findElement(ajaxElementName).isDisplayed()
					&& driver.findElement(ajaxElementName).isEnabled()) {
				driver.findElement(ajaxElementName).click();
				status = "Pass";
			} 
			
		} catch (Exception e) {
			status = "Fail";
			result = false;
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), ajaxElementName.toString().substring(ajaxElementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), ajaxElementName.toString().substring(ajaxElementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return result;
	}

	/**
	 * isChecked function to verify if the AJAX based Checkbox is checked
	 * @param selenium
	 * @param ajaxCheckboxName (Name of the ajax Checkbox)
	 * @throws IOException 
	 * @throws MyException
	 * @since March 04, 2013
	 */
	
	public static boolean isChecked(final By ajaxCheckboxName){
		try{
			if (waitForElement(ajaxCheckboxName,
					Config.AVGWAITTIMEFORELEMENT)) {
				driver.findElement(ajaxCheckboxName).isSelected();
				boolean checkBoxStatus = driver.findElement(ajaxCheckboxName)
						.isSelected();
				if (checkBoxStatus) {
					status = "Pass";
					result = true;
				} else {
					status = "Fail";
					result = false;
				}
			} else {
				status = "Fail";
				result = false;
			}
		}
		catch (Exception e) {
			status = "fail";
			result = false;
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), ajaxCheckboxName.toString().substring(ajaxCheckboxName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), ajaxCheckboxName.toString().substring(ajaxCheckboxName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return result;
	}


	/**
	 * To wait for page to load
	 * @param driver
	 * @return
	 */
	
	public static void waitForPageToLoad() {
		try {
			int counter = 0;
			
			while (true) {
				String readyState = ((JavascriptExecutor) driver)
						.executeScript("return document.readyState")
						.toString();

				//verify jquery loaded 
				int jQueryComplete =Integer.parseInt(((JavascriptExecutor) driver)
						.executeScript("return jQuery.active")
						.toString());
				
				if ((readyState.equals("complete")) && (jQueryComplete == 0)){
					break;
				}	
				if (counter == secondsToWait){
					throw new LoadingException("PageNotLoadedException"); 
				}
				Thread.sleep(1000);
				counter++;
			}
			status = "Pass";
		}catch (Exception e) {
			status = "Fail";
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), EMPTY, EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), EMPTY, EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
	}

	

	/**
	 * To mouse over on an element
	 * @param driver
	 * @return
	 */
	
	public static void mouseOver(WebElement element){
		try {
			String code = "var fireOnThis = arguments[0];"
					+ "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initEvent( 'mouseover', true, true );"
					+ "fireOnThis.dispatchEvent(evObj);";
			((JavascriptExecutor) driver).executeScript(code, element);
			status = "Pass";
		}catch (Exception e) {
			status = "Fail";
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), element.toString().substring(element.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), element.toString().substring(element.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
	}

	/**
	 * To select an option from the list
	 * @param driver
	 * @return
	 */
	public static boolean select(By listName, String valueForSelection){
		valueForSelection = valueForSelection != null ? valueForSelection
				.trim() : "";
		try {
			waitForElement(listName, Config.AVGWAITTIMEFORELEMENT);
			if (driver.findElement(listName).isDisplayed()) {
				Select elSelect = new Select(driver.findElement(listName));
				elSelect.selectByVisibleText(valueForSelection);
				status = "Pass";
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			status = "Fail";
			result = false;
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), listName.toString().substring(listName.toString().indexOf(":")+2), valueForSelection, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), listName.toString().substring(listName.toString().indexOf(":")+2), valueForSelection, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return result;
	}

	/**
	 * To get the text of a field
	 * @param driver
	 * @return
	 */
	
	public static String getText(By elementName, int wait) throws ExceptionHandler, IOException {

		try {
			if (waitForElement(elementName, wait)) {
				returnString=driver.findElement(elementName).getText();
				status = "Pass";
			}
		}  catch (Exception e) {
			status = "Fail";
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), elementName.toString().substring(elementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), elementName.toString().substring(elementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return returnString;
	}

	/**
	 * To get the value of a field
	 * @param driver
	 * @return
	 */
	public static String getValue(By elementName){

		try {
			if (waitForElement(elementName, Config.AVGWAITTIMEFORELEMENT)) {
				returnString=driver.findElement(elementName).getAttribute("value");
				status = "Pass";
			} 
		} catch (Exception e) {
			status = "Fail";
			new ExceptionHandler(e, driver, getCustomAttributeValue(getCurrentMethodName(), elementName.toString().substring(elementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()) );
		}
		if(Config.requireToWrite){
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(getCurrentMethodName(), elementName.toString().substring(elementName.toString().indexOf(":")+2), EMPTY, status, EMPTY, getCallingMethodAndLineNumber()));
		}
		return returnString;
	}

	// Customized Assert block starts
	
	/**
	 * Asserts that a condition is true. If it isn't,
	 * an AssertionError, with the given message, is thrown.
	 * @param condition the condition to evaluate
	 * @param message the assertion error message
	 */
		 public void assertTrue(boolean condition, String message, WebDriver driver) {
		    if(!condition) {
  	
		    	WebDriver d;
		    	 if (driver.getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
		    	      d = new Augmenter().augment(driver);
		    	    } else {
		    	      d = driver;
		    	    }
				File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
				String workingdirectory = System.getProperty("user.dir");
				File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/AssertFailure.jpg");
					
				try {
					FileUtils.copyFile(scrFile, scrFile1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					
					
				failNotEquals( Boolean.valueOf(condition), Boolean.TRUE, message);
			    }
		}
			  
			  
		 static private void failNotEquals(Object actual , Object expected, String message ) {
			  Assert.fail(format(actual, expected, message));
		  }

		 static String format(Object actual, Object expected, String message) {
		    String formatted = "";
			    if (null != message) {
			      formatted = message + " ";
			    }
			    return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
			  }
			  
	// Customized Assert block Ends
			  
			  
	// Customized Verify block starts
			
	  /**
	   * Asserts that a condition is true. If it isn't,
	   * an AssertionError, with the given message, is thrown.
	   * @param condition the condition to evaluate
	   * @param message the assertion error message
	   */
		 public static void verifyTrue(boolean condition, String message, WebDriver driver) {
		    if(!condition) {
			    	
		    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String workingdirectory = System.getProperty("user.dir");
				File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/AssertFailure.jpg");
			try {
				FileUtils.copyFile(scrFile, scrFile1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
					
			 }
		 }
			  
	// Customized Verify block Ends
	
	//Report Part
	private static final String DELIMINATOR = "####";
	private static final String EMPTY = "";
	
	private static final String DOT = ".";	
	
	private static String status = null;
	

	private static ITestResult logCustomMessage() {
		return Reporter.getCurrentTestResult();
	}
	
	/**
	 * used for get the calling method name with line number
	 * @return
	 */
	private static String getCallingMethodAndLineNumber(){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		String callingMethodWithLineNumber = stackTraceElements[3].getClassName() + DOT + stackTraceElements[3].getMethodName() + DOT + stackTraceElements[3].getLineNumber() ;
		
		return callingMethodWithLineNumber;
	}
	
	
	/**
	 * This method returns the current date and time in format HH-mm-ss.SSS
	 * 
	 * @return time - in the above mentioned format
	 */
	private static String getCurrentDateAndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}
	
	/**
	 * used to get the custom attribute value
	 * @param operation
	 * @param elementLocator1
	 * @param optional
	 * @param status
	 * @param screenShot
	 * @param callingMethodAndLineNumber
	 * @return
	 */
	private static String getCustomAttributeValue(String operation,String elementLocator1, String optional,String status, String screenShot, String callingMethodAndLineNumber){
		
		return operation + DELIMINATOR + elementLocator1 + DELIMINATOR + optional + DELIMINATOR + status + DELIMINATOR + screenShot + DELIMINATOR + callingMethodAndLineNumber;
		
	}
	
	//End of code for reporting
	
	
	/**
	 * used for get the current method name
	 * @return
	 */
	private static String getCurrentMethodName(){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		String currentMethodName = stackTraceElements[2].getMethodName();
		
		return currentMethodName;
	}
		
}
