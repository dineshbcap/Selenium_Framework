package utils.baseclass;

import static org.testng.internal.EclipseInterface.ASSERT_LEFT;
import static org.testng.internal.EclipseInterface.ASSERT_MIDDLE;
import static org.testng.internal.EclipseInterface.ASSERT_RIGHT;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import utils.annotations.MapToTestLink;

import utils.genericutility.Config;
import utils.genericutility.DateTimeUtility;
import utils.genericutility.ExceptionHandler;
import utils.genericutility.FileUtility;
import pageobjects.LoginPage;
import utils.seleniumutils.SeleniumWebDriver;
import utils.testdatareader.DataAccessClient;
import utils.testdatareader.EnvironmentPropertiesReader;
import utils.testlink.xmlrpcclient.TestLinkAPIClient;
import utils.testlink.xmlrpcclient.TestLinkAPIException;
import utils.testlink.xmlrpcclient.TestLinkAPIResults;

public class TestBaseClass {

	/**
	 * This page object is initialized before the start of every test.
	 */
	protected LoginPage loginPage;

	/**
	 * For Core Selenium2 functionality
	 */
	protected static WebDriver driver = null;
	protected WebDriverWait wait;
	private final String deliminator = "####";

	/**
	 * Standard log4j logger.
	 */
	protected Logger log = Logger.getLogger(getClass().getSimpleName());

	/**
	 * To Read the environment details
	 */
	EnvironmentPropertiesReader environmentPropertiesReader;
	String notes = null;
	String testLinkResult = null;
	TestLinkAPIClient api = null;

	/**
	 * Getting the base path of screen shot
	 */
	String screenshotBasePath;
	@SuppressWarnings("unused")
	private String logBasePath;
	private String logFile;

	/**
	 * Instantiating the driver path
	 */
	private static final String IE_FILE_PATH = "/src/main/resources/extensions/IEDriverServer.exe";
	private static final String CHROME_FILE_PATH = "/src/main/resources/extensions/chromedriver.exe";

	/**
	 * For DB connection
	 */
	private static DataAccessClient dataAccess = null;

	public enum BrowserType {
		FIREFOX("firefox"), IE("iexplore"), SAFARI("SAFARI"), CHROME("CHROME");
		private String label;

		private BrowserType(String label) {
			this.label = label;
		}

		public String Value() {
			return label;
		}
	}

	/**
	 * Displaying the environment details
	 * 
	 * @throws IOException
	 */

	public TestBaseClass() {
		// Getting the properties
		try {
			PropertyConfigurator.configure(new File(".").getCanonicalPath()
					+ File.separator + "src" + File.separator + "main"
					+ File.separator + "resources" + File.separator
					+ "log4j.properties");

			// Location for screenshot
			screenshotBasePath = new File(".").getCanonicalPath()
					+ File.separator + "test-output" + File.separator
					+ "screenshots";

			// Location for logs
			logBasePath = new File(".").getCanonicalPath() + File.separator
					+ "test-output" + File.separator + "logs";

			// Instantiating logger
			logFile = new File(".").getCanonicalPath() + File.separator
					+ "test-output" + File.separator + "temp.log";

		} catch (IOException e) {
			e.getMessage();
		}
	}

	/**
	 * Initializing the DB connection
	 * 
	 * @return DataAccessClient
	 * @throws Exception
	 */
	protected DataAccessClient getDataAccessClient() throws Exception {
		if (dataAccess == null) {
			dataAccess = new DataAccessClient(
					environmentPropertiesReader.getDbDriver(),
					environmentPropertiesReader.getDBurl(),
					environmentPropertiesReader.getDBusername(),
					environmentPropertiesReader.getDBpassword());
		}
		return dataAccess;
	}

	/**
	 * Set up logger info
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({"webSite","browser","seleniumHost","seleniumPort"})
	public final void genericSetUp(String webSite, String browser, String seleniumHost, String seleniumPort) throws ExceptionHandler, IOException {
		// Instantiating Logger

		driver = loadURL(webSite,browser,seleniumHost, seleniumPort);
		SeleniumWebDriver.driver = driver;
		Layout layout = new PatternLayout(
				"%d{dd-MMM-yyyy HH:mm:ss:SSS} %-5p %c{1}:%L - %m%n");
		log.removeAllAppenders();
		FileAppender appender = new FileAppender(layout, logFile, false);
		log.addAppender(appender);

		String fileParam = System.getProperty("selenium.properties.file");

		log.info("=====================================================================================================");
		if (fileParam == null || fileParam.contains("selenium.properties.file")) {
			environmentPropertiesReader = new EnvironmentPropertiesReader();
		} else {
			log.info("Properties file used : " + fileParam);
			environmentPropertiesReader = new EnvironmentPropertiesReader(
					fileParam);
		}

		log.info("App URL    : " + environmentPropertiesReader.getURL());
		log.info("Browser    : " + environmentPropertiesReader.getBrowser());
		log.info("=====================================================================================================");
	}

	/**
	 * Returning the driver based on the browser
	 * 
	 * @param browser
	 * @return
	 * @throws IOException
	 */
	public WebDriver loadURL(String webSite,String browser,String seleniumHost, String seleniumPort) throws IOException {

		// Instantiating the browser
		driver = getWebDriver(browser,seleniumHost, seleniumPort);
		wait = new WebDriverWait(driver, 30);
		driver.get(webSite);
		
		// Maximize the window
		driver.manage().window().maximize();

		return driver;

	}

	/**
	 * Returning the driver based on the browser
	 * 
	 * @param browser
	 * @return
	 * @throws IOException
	 */
	public static WebDriver getWebDriver(String browser,String seleniumHost, String seleniumPort) throws IOException {
		switch (BrowserType.valueOf(browser)) {
		case FIREFOX:
			DesiredCapabilities capabilities = new DesiredCapabilities();
			FirefoxProfile fireFoxProfile;
			if(seleniumHost.contains("localhost")){ 
	            fireFoxProfile = new FirefoxProfile(); 
	           	driver=new FirefoxDriver(fireFoxProfile);
			}else{
			// if a matching driver cannot be located
			URL url=new URL("http",seleniumHost,Integer.parseInt(seleniumPort),"/wd/hub");
			capabilities.setBrowserName(browser);
			driver = new RemoteWebDriver(url,capabilities);
			}
			return driver;
			
		case IE:
			DesiredCapabilities ieCapabilities = DesiredCapabilities
					.internetExplorer();
			ieCapabilities.setCapability("nativeEvents", false);
			ieCapabilities.setCapability("requireWindowFocus", true);
			ieCapabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			ieCapabilities.setCapability("enableElementCacheCleanup", true);
			File file = new File(new java.io.File(".").getCanonicalPath()
					+ IE_FILE_PATH);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			return new InternetExplorerDriver(ieCapabilities);
		case SAFARI:
			DesiredCapabilities safariCapabilities = DesiredCapabilities
					.safari();
			safariCapabilities.setCapability(
					SafariDriver.CLEAN_SESSION_CAPABILITY, true);
			return new SafariDriver(safariCapabilities);
		case CHROME:
			DesiredCapabilities chromeCapabilities = DesiredCapabilities
					.chrome();
			File chromeFile = new File(new java.io.File(".").getCanonicalPath()
					+ CHROME_FILE_PATH);
			System.setProperty("webdriver.chrome.driver",
					chromeFile.getAbsolutePath());
			return new ChromeDriver(chromeCapabilities);
		default:
			throw new RuntimeException("Browser type unsupported");
		}
	}

	/**
	 * Capturing screenshot, Setting test result and creating log files after
	 * each test run
	 * 
	 * @param result
	 * @throws IOException
	 * @throws TestLinkAPIException
	 */
	@AfterMethod(alwaysRun = true)
	@Parameters({"seleniumHost"})
	public final void tearDown(ITestResult result, String host) throws IOException,
			TestLinkAPIException {

		String dateTimeStamp = DateTimeUtility
				.getCurrentDateAndTimeInLoggerFormat();
		status = "PASS";
		MapToTestLink mapToTestLink = result.getMethod()
				.getConstructorOrMethod().getMethod()
				.getAnnotation(MapToTestLink.class);
		
		if(Config.TESTLINKUPDATE) {
			if (mapToTestLink != null) {
				String testCase = mapToTestLink.testCaseID();
				if (result.isSuccess()) {
					testLinkResult = TestLinkAPIResults.TEST_PASSED;
					notes = "Executed successfully";
					api = new TestLinkAPIClient(
							environmentPropertiesReader.getTestLinkDevKey(),
							environmentPropertiesReader.getTestlinkURL());
					api.reportTestCaseResult(
							environmentPropertiesReader.getTestProject(),
							environmentPropertiesReader.getTestPlan(),
							environmentPropertiesReader.getTestSuiteID(), testCase,
							notes, testLinkResult,
							environmentPropertiesReader.getTestBuildId());
				} else {
					testLinkResult = TestLinkAPIResults.TEST_FAILED;
					notes = "Execution Failed";
					api = new TestLinkAPIClient(
							environmentPropertiesReader.getTestLinkDevKey(),
							environmentPropertiesReader.getTestlinkURL());
					api.reportTestCaseResult(
							environmentPropertiesReader.getTestProject(),
							environmentPropertiesReader.getTestPlan(),
							environmentPropertiesReader.getTestSuiteID(), testCase,
							notes, testLinkResult,
							environmentPropertiesReader.getTestBuildId());
				}
			}
		}
		// Capture screen shot in case test has failed.
		try {
			if (!result.isSuccess()) {
				String destFile = screenshotBasePath + File.separator
						+ result.getName() + " " + dateTimeStamp + ".png";
				log.info("Captured Screenshot : " + destFile);
				status = "FAIL";
				WebDriver d;
		    	 if (driver.getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
		    	      d = new Augmenter().augment(driver);
		    	    } else {
		    	      d = driver;
		    	    }
				File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
				FileUtility.copyFile(scrFile, new File(destFile));
			}
		} catch (Exception e) {
			log.error("The following error has occured while capturing a screen shot : "
					+ e.getMessage());
		} finally {

			// Logging the test result
			log.info("The test result for " + result.getName() + " is "
					+ status);

			// Closing the browser and closing driver
			driver.quit();
		}
	}

	// Report Part
	protected final String empty = "";

	protected final String dot = ".";

	protected String status = null;

	protected ITestResult logCustomMessage() {
		return Reporter.getCurrentTestResult();
	}

	/**
	 * used for get the calling method name with line number
	 * 
	 * @return
	 */
	protected String getCallingMethodAndLineNumber() {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();

		String callingMethodWithLineNumber = stackTraceElements[3]
				.getClassName()
				+ dot
				+ stackTraceElements[3].getMethodName()
				+ dot + stackTraceElements[3].getLineNumber();

		return callingMethodWithLineNumber;
	}

	/**
	 * This method returns the current date and time in format HH-mm-ss.SSS
	 * 
	 * @return time - in the above mentioned format
	 */
	protected static String getCurrentDateAndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}

	/**
	 * used to get the custom attribute value
	 * 
	 * @param operation
	 * @param elementLocator1
	 * @param optional
	 * @param status
	 * @param screenShot
	 * @param callingMethodAndLineNumber
	 * @return
	 */
	protected String getCustomAttributeValue(String operation,
			String elementLocator1, String optional, String status,
			String screenShot, String callingMethodAndLineNumber) {

		return operation + deliminator + elementLocator1 + deliminator
				+ optional + deliminator + status + deliminator + screenShot
				+ deliminator + callingMethodAndLineNumber;

	}

	protected boolean logTitleMessage(String message1) {

		logCustomMessage().setAttribute(
				getCurrentDateAndTime(),
				getCustomAttributeValue(message1, empty, empty, "title", empty,
						getCallingMethodAndLineNumber()));
		log.info(message1);
		return true;
	}

	// End of code for reporting

	// Customized Assert block starts

	/**
	 * Asserts that a condition is true. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 * @param message
	 *            the assertion error message
	 */
	public void assertTrue(boolean condition, String message, WebDriver driver) {
		if (!condition) {

			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			String workingdirectory = System.getProperty("user.dir");

			File scrFile1 = new File(
					workingdirectory
							+ "/custom-test-report/Failure_Screenshot/AssertFailure.jpg");

			try {
				FileUtils.copyFile(scrFile, scrFile1);
			} catch (IOException e1) {
				log.info(e1.toString());
			}

			log.info("Customized Assert true block executed...Temprory function, Need to enhance if You wish scrrenshot in report. Failure screenshot in 'custom-test-report/Failure_Screenshot/AssertFailure.jpg");

			failNotEquals(Boolean.valueOf(condition), Boolean.TRUE, message);
		}
	}

	static private void failNotEquals(Object actual, Object expected,
			String message) {
		Assert.fail(format(actual, expected, message));
	}

	static String format(Object actual, Object expected, String message) {
		String formatted = "";
		if (null != message) {
			formatted = message + " ";
		}

		return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual
				+ ASSERT_RIGHT;
	}

	// Customized Assert block Ends

	// Customized Verify block starts

	/**
	 * Asserts that a condition is true. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 * @param message
	 *            the assertion error message
	 */
	public void verifyTrue(boolean condition, String message, WebDriver driver) {
		if (!condition) {

			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			String workingdirectory = System.getProperty("user.dir");

			File scrFile1 = new File(
					workingdirectory
							+ "/custom-test-report/Failure_Screenshot/AssertFailure.jpg");

			try {
				FileUtils.copyFile(scrFile, scrFile1);
			} catch (IOException e1) {
				log.info(e1.toString());
			}

			log.info("Customized Verify True block executed...Temprory function, Need to enhance if You wish scrrenshot in report. Failure screenshot in 'custom-test-report/Failure_Screenshot/AssertFailure.jpg");

		}
	}

	// Customized Verify block Ends
}
