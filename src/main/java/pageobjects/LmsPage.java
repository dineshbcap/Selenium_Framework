package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import utils.seleniumutils.SeleniumWebDriver;

public class LmsPage extends SeleniumWebDriver{

	private By readyLocator = By.xpath("//td[@id='ctl00_IDMMenun2']/table/tbody/tr/td");
	
	public LmsPage() {
		PageFactory.initElements(SeleniumWebDriver.driver, this);
		logTitleMessage("Instantiating page, waiting for element: "+readyLocator+ " to be present");
		waitForElement(readyLocator, READYLOCATORWAITTIME);
		logTitleMessage("Instantiated "+readyLocator +" , ready for use");
	}

}
