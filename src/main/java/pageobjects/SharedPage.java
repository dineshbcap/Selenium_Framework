package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import utils.seleniumutils.SeleniumWebDriver;


public class SharedPage extends SeleniumWebDriver{

	
	private By readyLocator = By.id("searchtextbox");


	private By btnLogout = By.linkText("Logout");

	private By lnkUserName = By.cssSelector("css=span.user_name");
	private By txtSearchtextbox = By.id("searchtextbox");
	private By btnSearch = By.id("searchbtn");
	/***
	 * Call to super constructor
	 */
	public SharedPage(){
		PageFactory.initElements(SeleniumWebDriver.driver, this);
		logTitleMessage("Instantiating page, waiting for element: "+readyLocator+ " to be present");
		waitForElement(readyLocator, READYLOCATORWAITTIME);
		logTitleMessage("Instantiated "+readyLocator +" , ready for use");
	}

	/**
	 * Log out of the Application
	 * @param 
	 * @return void
	 */
	public void logOut(){
		click(lnkUserName);
		waitForPageToLoad();
		if (waitForElement(btnLogout, 5)) {
			click(btnLogout);
			waitForPageToLoad();
		}
	}
	
	/***
	 * Search by any name to the Home screen
	 * @return SearchPage- return new Search Page
	 */
	
	public SearchPage searchbyanyname(String userName){
		sendKeys(txtSearchtextbox,userName);
		click(btnSearch);
		waitForPageToLoad();
		return new SearchPage();
	}

}
