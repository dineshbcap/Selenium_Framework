package utils.testlink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import utils.testlink.xmlrpcclient.TestLinkAPIClient;
import utils.testlink.xmlrpcclient.TestLinkAPIException;
import utils.testlink.xmlrpcclient.TestLinkAPIHelper;
import utils.testlink.xmlrpcclient.TestLinkAPIResults;

/**
 * TestLink Helper class which is having the static useful methods
 */
public class TestLinkHelper {
	private static final String TPROJECTGLUECHAR = "-";

	/**
	 * Gets only the Automation testcases That is which are marked with Execution Type as 'Automated'
	 * 
	 * @param api
	 * @param tProjectName
	 * @param tplanID
	 * @param keyToMatch
	 * @param valueToMatch
	 * @param KeyToFetch
	 * @return
	 * @throws TestLinkAPIException
	 */
	
	private TestLinkHelper(){
		
	}
	public static Hashtable<String, ArrayList<String>> getAutomationTestCases(TestLinkAPIClient api, String tProjectName, int tplanID, String keyToMatch, String valueToMatch, String KeyToFetch) throws TestLinkAPIException {
		TestLinkAPIResults getProjectResults = api.getAutomatedTestCasesForTestPlan(tplanID);
		Hashtable<String, ArrayList<String>> tcArray = mapFetcher(api, getProjectResults, tProjectName, keyToMatch, valueToMatch,
				KeyToFetch);
		if(tcArray != null) {
			return tcArray;
		}
		else {
			throw new NullPointerException("Failed to fetch the Automation enabled testcases. No Case found");
		}
	}

	/**
	 * Helps in mapping the testlink api results back to desired format of hashtable
	 * 
	 * @param api
	 * @param results
	 * @param tProjectName
	 * @param key
	 * @param Value
	 * @param reqKey
	 * @return
	 * @throws TestLinkAPIException
	 */
	@SuppressWarnings("unchecked")
	public static Hashtable<String, ArrayList<String>> mapFetcher(TestLinkAPIClient api, TestLinkAPIResults results, String tProjectName, String key, String Value, String reqKey) throws TestLinkAPIException {
		Map<String, Object> resultMap = null;
		Hashtable<String, ArrayList<String>> resultTable = new Hashtable<String, ArrayList<String>>();

		int projID = TestLinkAPIHelper.getProjectID(api, tProjectName);
		for(int i = 0; i < results.size(); i++) {
			resultMap = results.getData(i);

			for(String keyForMap : resultMap.keySet()) {
				Object[] arrayHolder = (Object[]) resultMap.get(keyForMap);

				for(int j = 0; j < arrayHolder.length; j++) {
					HashMap<String, String> valueForMap = (HashMap<String, String>) arrayHolder[j];

					String extid = valueForMap.get("external_id");
					String internalID = valueForMap.get("tc_id");
					String version = valueForMap.get("version");

					String prefix = TestLinkAPIHelper.getKeyValue(api, tProjectName, "prefix");

					String customValue = api.getCustomFieldsTestCase(Integer.parseInt(extid), projID, "script_name",
							Integer.parseInt(internalID), Integer.parseInt(version));
					ArrayList<String> testCaseIds;
					if(resultTable.get(customValue) != null) {
						testCaseIds = resultTable.get(customValue);
					}
					else {
						testCaseIds = new ArrayList<String>();
					}
					testCaseIds.add(prefix + TPROJECTGLUECHAR + valueForMap.get(reqKey).toString());
					resultTable.put(customValue, testCaseIds);
				}
			}
		}
		if(resultTable.size() != 0) {
			return resultTable;
		}
		else {
			return null;
		}
	}
}
