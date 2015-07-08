package tests.idm;


import java.util.ArrayList;

import org.apache.commons.collections.map.HashedMap;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utils.annotations.MapToTestLink;

import tests.login.LoginTest;
import utils.baseclass.TestBaseClass;
import pageobjects.IDMPage;
import pageobjects.IntranetHomePage;
import pageobjects.LoginPage;
import utils.retryAnalyser.RetryRule;
import utils.testdatareader.ExcelReader;

@Listeners(utils.baseclass.CustomizedReporter.class)

public class VerifyIDMPage extends TestBaseClass{
	private IntranetHomePage homePage;
	private IDMPage iDMPage;

	

	/*************************************************************************************************** 
	 * @purpose To verify IDM Page
 	 * @action Go to IDM page and verify user details
   	 * @author AspireQA
   	 * @since October 30, 2014
   	 ***************************************************************************************************/
	
	@Test(retryAnalyzer = RetryRule.class,groups = {"Regression"})
	@MapToTestLink(testCaseID = "TestCase_5")
	public void verifyIDMPage(){

		// ------------------------------------------------------------------//
		// Step-1: Load the application //
		// ------------------------------------------------------------------//
	
		ArrayList<HashedMap> loginTestData = ExcelReader.getTestDataByTestCaseId(
				"TC_EBS_001", LoginTest.class.getSimpleName());
		log.info(loginTestData.get(0).get("UserName").toString() + " - ");
	
		// ------------------------------------------------------------------//
		// Step-1: Login to the application
		// ------------------------------------------------------------------//
		logTitleMessage("Login to application");
		loginPage = new LoginPage();
		homePage=loginPage.login(loginTestData.get(0).get("UserName").toString(), loginTestData.get(0).get("Password").toString());
		logTitleMessage("Login Successful");	
			
		// ------------------------------------------------------------------//
		// Step-2: Go to IDM system Page //
		// ------------------------------------------------------------------//
		iDMPage=homePage.clickIDMLink();
		// ------------------------------------------------------------------//
		// Step-3: Fetch Test Data for IDM Page //
		// ------------------------------------------------------------------//
		ArrayList<HashedMap> IDMData = ExcelReader.getTestDataByTestCaseId(
				"TC_IDM_001", VerifyIDMPage.class.getSimpleName());
		assertTrue(iDMPage.verifyIDMPage(IDMData.get(0)),"Error in verifying training system page",driver);
		
	}
}
