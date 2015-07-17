package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import utils.seleniumutils.SeleniumWebDriver;

public class IntranetHomePage extends SeleniumWebDriver {

	private By lnkIDM = By.linkText("123");
	private By readyLocator = By.linkText("IDM/PMS");
	private By lnkLMS = By.linkText("LMS");
	private By lnkSeventhSense = By.linkText("Seventh Sense-Old");
	private By lnkTrainingSystem = By.linkText("Training System");

	/***
	 * Call to super constructor
	 */
	public IntranetHomePage() {
		PageFactory.initElements(SeleniumWebDriver.driver, this);
		logTitleMessage("Instantiating page, waiting for element: %s to be present::" +readyLocator);
		waitForElement(readyLocator, READYLOCATORWAITTIME);
		logTitleMessage(String.format("Instantiated %s, ready for use", getClass().getSimpleName()));

	}
	
	/**
	 * verify elements present on Intranet Home Page
	 * @param 
	 * @return boolean- returns true if all required elements for Intranet home page are present, else false 
	 */
	public boolean verifyelement() {
		boolean returnValue = true;
		if (!verifyElementIDM()) {
			returnValue = false;
		}
		if (!verifyElementMS()) {
			returnValue = false;
		}
		if (!verifyElementSeventhSense()) {
			returnValue = false;
		}
		return returnValue;
	}
	
	
	/**
	 * click Training Systems Link
	 * @param 
	 * @return TrainingSystemPage- returns new Training System Page
	 */
	public TrainingSystemPage clickTrainingSystemLink() {
		click(lnkTrainingSystem);
		waitForPageToLoad();
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		return new TrainingSystemPage();
	}
	
	/**
	 * click IDM Link
	 * @param 
	 * @return IDMPage- returns new IDM Page
	 */
	public IdmPage clickIDMLink() {
		click(readyLocator);
		waitForPageToLoad();
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		return new IdmPage();
	}

	
	/***
	 * Verify if IDM Link is present
	 * @return boolean- returns true if IDM link is present, else false
	 */
	public boolean verifyElementIDM() {
		return isElementPresent(lnkIDM);
	}
	/***
	 * Verify if LMS Link is present
	 * @return boolean- returns true if LMS link is present, else false
	 */
	public boolean verifyElementMS() {
		return isElementPresent(lnkLMS);
	}
	/***
	 * Verify if Seventh Sense Link is present
	 * @return boolean- returns true if Seventh Sense link is present, else false
	 */
	public boolean verifyElementSeventhSense() {
		return isElementPresent(lnkSeventhSense);
	}
}
