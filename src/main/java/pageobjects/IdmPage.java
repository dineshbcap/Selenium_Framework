package pageobjects;
import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import utils.seleniumutils.SeleniumWebDriver;


public class IdmPage extends SeleniumWebDriver{
	
	private By readyLocator = By.id("imgSearch");	
	
	
	/***
	 * Call to super constructor
	 */
	public IdmPage(){
		PageFactory.initElements(driver, this);
		logTitleMessage("Instantiating page, waiting for element: "+readyLocator+ " to be present");
		waitForElement(readyLocator, READYLOCATORWAITTIME);
		logTitleMessage("Instantiated "+readyLocator +" , ready for use");

	}
	
	/**
	 * verify IDM Page
	 * @param HashedMap- Test data to verify the IDM page
	 * @return boolean- returns true if all required data is present, else false
	 */
	public boolean verifyIdmPage(HashedMap idmData){
		boolean returnValue=true;
		
		if(!isTextPresent(idmData.get("EN").toString())){
			returnValue=false;
		}
		if(!isTextPresent(idmData.get("DOJ").toString())){
			returnValue=false;
		}
		if(!isTextPresent(idmData.get("EmployeeType").toString())){
			returnValue=false;
		}
		if(!isTextPresent(idmData.get("PrimaryManager").toString())){
			returnValue=false;
		}
		return returnValue;
	}
	
	
}
