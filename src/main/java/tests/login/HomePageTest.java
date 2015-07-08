package tests.login;

import java.util.ArrayList;

import org.apache.commons.collections.map.HashedMap;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utils.annotations.MapToTestLink;

import utils.baseclass.TestBaseClass;
import pageobjects.IntranetHomePage;
import pageobjects.LoginPage;
import utils.retryAnalyser.RetryRule;
import utils.testdatareader.ExcelReader;

@Listeners(utils.baseclass.CustomizedReporter.class)

public class HomePageTest  extends TestBaseClass{
		
	private IntranetHomePage homePage;
	/*************************************************************************************************** 
	 * @purpose To verify home page elements
 	 * @action Verify the links present on the home page
   	 * @author AspireQA
   	 * @since October 30, 2014
   	 ***************************************************************************************************/

	@Test(retryAnalyzer = RetryRule.class,groups = {"Regression","Login"})
	@MapToTestLink(testCaseID = "TestCase_2")
	public void homePageTest(){
	
		ArrayList<HashedMap> testData = ExcelReader.getTestDataByTestCaseId(
				"TC_EBS_001", LoginTest.class.getSimpleName());
		log.info(testData.get(0).get("UserName").toString() + " - ");
	
		// ------------------------------------------------------------------//
		// Step-1: Login to the application
		// ------------------------------------------------------------------//
		logTitleMessage("Login to application");
		loginPage = new LoginPage();
		homePage=loginPage.login(testData.get(0).get("UserName").toString(), testData.get(0).get("Password").toString());
		logTitleMessage("Login Successful");
						
		// ------------------------------------------------------------------//
		// Step-2:Verify Home page element //
		// ------------------------------------------------------------------//
		logTitleMessage("Verify presense of home page elements");
		assertTrue(homePage.verifyelement(),"Verification failed",driver);
		logTitleMessage("Verified home page elements");
	}
}
