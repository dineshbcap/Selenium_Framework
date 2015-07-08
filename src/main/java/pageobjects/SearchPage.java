package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import utils.seleniumutils.SeleniumWebDriver;

public class SearchPage extends SeleniumWebDriver{

	private By lnkHome = By.linkText("Home");
	private By readyLocator= By.linkText("Home");
	private By lnkAnnouncement = By.linkText("Announcements");
	private By lnkCelebrations = By.linkText("Celebrations");
	private By lnkClassifields = By.linkText("Classifieds");
	
	
	
	/***
	 * Call to super constructor
	 */
	public SearchPage() {
		PageFactory.initElements(SeleniumWebDriver.driver, this);
		logTitleMessage("Instantiating page, waiting for element: "+readyLocator+ " to be present");
		waitForElement(readyLocator, READYLOCATORWAITTIME);
		logTitleMessage("Instantiated "+readyLocator +" , ready for use");
	}
	
	/**
	 * verify Search Page
	 * @param 
	 * @return boolean- returns true if all elements for Search Page are present, else false
	 */
	public boolean verifySearchPage(){
		if(!verifyElementHome()){
			return false;
		}
		if(!verifyElementAnnouncement()){
			return false;
		}
		if(!verifyElementCelebrations()){
			return false;
		}
		if(!verifyElementCelebrations()){
			return false;
		}
		return true;
	}
	
	/***
	 * Verify if Home Link is present
	 * @return boolean- returns true if Home link is present, else false
	 */
	public boolean verifyElementHome() {
		if (isElementPresent(lnkHome)) {
			return true;
		} else{
			return false;
		}
	}
	
	/***
	 * Verify if Announcement Link is present
	 * @return boolean- returns true if Announcement link is present, else false
	 */
	public boolean verifyElementAnnouncement() {
		if (isElementPresent(lnkAnnouncement)) {
			return true;
		} else{
			return false;
		}
	}
	
	/***
	 * Verify if Celebrations Link is present
	 * @return boolean- returns true if Celebrations link is present, else false
	 */
	public boolean verifyElementCelebrations() {
		if (isElementPresent(lnkCelebrations)) {
			return true;
		} else{
			return false;
		}
	}
	
	/***
	 * Verify if Classifieds Link is present
	 * @return boolean- returns true if Classifieds link is present, else false
	 */
	public boolean verifyElementClassifields() {
		if (isElementPresent(lnkClassifields)) {
			return true;
		} else{
			return false;
		}
	}
}
