package utils.retryAnalyser;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import utils.genericutility.Config;

/**
 * Retry rule for testclasses which are failed because of wait timed out No of retries can be specified in the TestBaseClass @Rule
 * annotated place
 * @author AspireQA
 * 
 */
public class RetryRule implements IRetryAnalyzer {
	public int retryCount;
	private int maxRetry;

	public RetryRule() {
		retryCount = 0;
		this.maxRetry=Config.RETRYCOUNTER;
	}
	
	public boolean retry(ITestResult result) {
		String e= result.getThrowable().toString();
		if(e.contains("AssertionError")&&maxRetry>retryCount) {
				retryCount++;
				Config.retryCount=retryCount;
				return true;
		}
		Config.retryCount=0;
		return false;
	}

}
