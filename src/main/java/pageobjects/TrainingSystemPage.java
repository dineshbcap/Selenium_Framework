package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import utils.seleniumutils.SeleniumWebDriver;


public class TrainingSystemPage extends SeleniumWebDriver{
	
	private By readyLocator = By.linkText("For Information Security Management System (ISMS)");
	private By lnkISMS = By.linkText("For Information Security Management System (ISMS)");
	private By lnkOrientationProg = By.linkText("Orientation Program");
	private By lnkImpactProg = By.linkText("Impact Training Program");
	private By lnkWisdomCurve = By.linkText("Wisdom Curve");
	private By lnkDevTeams = By.linkText("For Development Teams");
	private By lnkTestingTeams = By.linkText("For Testing Teams");
	
	/***
	 * Call to super constructor
	 */
	public TrainingSystemPage(){
		PageFactory.initElements(driver, this);
		logTitleMessage("Instantiating page, waiting for element: "+readyLocator+ " to be present");
		waitForElement(readyLocator, READYLOCATORWAITTIME);
		logTitleMessage("Instantiated "+readyLocator +" , ready for use");
	}

	/**
	 * verify Training System Page
	 * @param 
	 * @return boolean- returns true if all required elements for Training System Page is present, else false
	 */
	public boolean verifyTrainingSystemPage(){
		boolean returnValue=true;
		
		if(!verifyElementISMS()){
			returnValue=false;
		}
		if(!verifyElementOrientationProg()){
			returnValue=false;
		}
		if(!verifyImpactProg()){
			returnValue=true;
		}
		if(!verifyWisdomCurve()){
			returnValue=false;
		}
		if(!verifyDevTeams()){
			returnValue=false;
		}
		if(!verifyTestingTeams()){
			returnValue=true;
		}
		return returnValue;
		
	}
	
	/***
	 * Verify if Impact Program Link is present
	 * @return boolean- returns true if Impact Program link is present, else false
	 */
	public boolean verifyImpactProg() {
		return isElementPresent(lnkImpactProg);
	}
	
	/***
	 * Verify if Wisdom Curve Link is present
	 * @return boolean- returns true if Wisdom Curve link is present, else false
	 */
	public boolean verifyWisdomCurve() {
		return isElementPresent(lnkWisdomCurve);
	}
	
	/***
	 * Verify if Dev Teams Link is present
	 * @return boolean- returns true if Dev Teams link is present, else false
	 */
	public boolean verifyDevTeams() {
		return isElementPresent(lnkDevTeams);
	}
	
	/***
	 * Verify if Testing Teams Link is present
	 * @return boolean- returns true if Testing Teams link is present, else false
	 */
	public boolean verifyTestingTeams() {
		return isElementPresent(lnkTestingTeams);
	}
	
	/***
	 * Verify if ISMS Link is present
	 * @return boolean- returns true if ISMS link is present, else false
	 */
	public boolean verifyElementISMS() {
		return isElementPresent(lnkISMS);

	}
	
	/***
	 * Verify if Orientation Program Link is present
	 * @return boolean- returns true if Orientation Program link is present, else false
	 */
	public boolean verifyElementOrientationProg() {
		return isElementPresent(lnkOrientationProg);
	}

}
