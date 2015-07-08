package tests.searchusers;


import java.util.ArrayList;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utils.annotations.MapToTestLink;

import tests.login.LoginTest;
import utils.baseclass.TestBaseClass;
import pageobjects.LoginPage;
import pageobjects.SearchPage;
import pageobjects.SharedPage;
import utils.retryAnalyser.RetryRule;
import utils.testdatareader.ExcelReader;

@Listeners(utils.baseclass.CustomizedReporter.class)

public class SearchByName extends TestBaseClass{
	private SearchPage searchPage;

	

	/*************************************************************************************************** 
	 * @purpose To verify search function
 	 * @action Search aspirians by name
   	 * @author AspireQA
   	 * @since October 30, 2014
   	 ***************************************************************************************************/
	
	@Test(retryAnalyzer = RetryRule.class,groups = {"Regression","Search"})
	@MapToTestLink(testCaseID = "TestCase_3")
	public void searchbyname(){

		// ------------------------------------------------------------------//
		// Step-1: Load the application //
		// ------------------------------------------------------------------//
	
		ArrayList<HashedMap> loginTestData = ExcelReader.getTestDataByTestCaseId(
				"TC_EBS_001", LoginTest.class.getSimpleName());
		log.info(loginTestData.get(0).get("UserName").toString() + " - ");
	
		// ------------------------------------------------------------------//
		// Step-2: Login to the application
		// ------------------------------------------------------------------//
		logTitleMessage("Login to application");
		loginPage = new LoginPage();
		loginPage.login(loginTestData.get(0).get("UserName").toString(), loginTestData.get(0).get("Password").toString());
		logTitleMessage("Login Successful");
		
		// ------------------------------------------------------------------//
		// Step-3: Get the test data to search//
		// ------------------------------------------------------------------//
		ArrayList<HashedMap> testData = ExcelReader.getTestDataByTestCaseId(
				"TC_CT_001", SearchByName.class.getSimpleName());
		log.info(testData.get(0).get("TC_ID").toString() + " - ");
		
		// ------------------------------------------------------------------//
		// Step-4: Load Home page elements //
		// ------------------------------------------------------------------//
		SharedPage sharedPage = new SharedPage();
		log.info("Successfully loaded Home Page elements");
		
		// ------------------------------------------------------------------//
		// Step-5:Search by name //
		// ------------------------------------------------------------------//
		for(int i=0;i<testData.size();i++){
			log.info("Searching for: - "+testData.get(i).get("UserName"));
			searchPage=sharedPage.searchbyanyname(testData.get(i).get("UserName").toString());
			Assert.assertTrue("Could not find the Name: "+testData.get(i).get("UserName"),searchPage.verifySearchPage());
			log.info("Successfully got: - "+testData.get(i).get("UserName"));
		}
				
	}
}
