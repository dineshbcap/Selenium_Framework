package utils.testdatareader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentPropertiesReader {
	private String accTransusername;
	private String accMgrusername;
	private String password;
	private String url;
	private String browser;
	private String dbURL;
	private String dbUsername;
	private String dbpassword;
	private String dbDriver;
	private static Properties property = new Properties();
	private String filePath = "/src/main/resources/env.properties";
	private String jenkinsPath = "/src/main/resources/jenkinsconfig";
	private String testlinkURL;
	private String testLinkDevKey;
	private String testProject;
	private String testPlan;
	private String testSuiteID;
	private String testBuildId;
	
	public EnvironmentPropertiesReader() {
		try {
			FileInputStream fileInputStream=new FileInputStream(new java.io.File(".").getCanonicalPath() + filePath);
			property.load(fileInputStream);
			this.setTransMgrUsername(property.getProperty("transmgrusername"));
			this.setAccMgrUsername(property.getProperty("accountmgrusername"));
			this.setPassword(property.getProperty("password"));
			this.setBrowser(property.getProperty("browser"));
			this.setURL(property.getProperty("URL"));
			this.setDBurl(property.getProperty("DBurl"));
			this.setDBpassword(property.getProperty("DBpassword"));
			this.setDBusername(property.getProperty("DBusername"));
			this.setDbDriver(property.getProperty("DBdriver"));
			this.setTestlinkURL(property.getProperty("testlinkUrl"));
			this.setTestLinkDevKey(property.getProperty("testlinkKey"));
			this.setTestProject(property.getProperty("testProject"));
			this.setTestPlan(property.getProperty("testPlan"));
			this.setTestSuiteID(property.getProperty("testSuiteID"));
			this.setTestBuildId(property.getProperty("testBuildId"));
			fileInputStream.close();

		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
	}

	public EnvironmentPropertiesReader(String fileName) {
		try {
			FileInputStream fileInputStream=new FileInputStream(new java.io.File(".").getCanonicalPath() + jenkinsPath
					+ File.separatorChar + fileName);
			property.load(fileInputStream);
			this.setTransMgrUsername(property.getProperty("transmgrusername"));
			this.setAccMgrUsername(property.getProperty("accountmgrusername"));
			this.setPassword(property.getProperty("password"));
			this.setBrowser(property.getProperty("browser"));
			this.setURL(property.getProperty("URL"));
			this.setDBurl(property.getProperty("DBurl"));
			this.setDBpassword(property.getProperty("DBpassword"));
			this.setDBusername(property.getProperty("DBusername"));
			this.setDbDriver(property.getProperty("DBdriver"));
			fileInputStream.close();

		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
	}

	/**
	 * Set user name
	 * 
	 * @param username
	 */
	public void setTransMgrUsername(String accTransusername) {
		this.accTransusername = accTransusername;
	}

	/**
	 * Get user name
	 * 
	 * @return userName
	 */
	public String getTransMgrUsername() {
		return accTransusername;
	}
	
	/**
	 * Set user name
	 * 
	 * @param username
	 */
	public void setAccMgrUsername(String accMgrusername) {
		this.accMgrusername = accMgrusername;
	}

	/**
	 * Get user name
	 * 
	 * @return userName
	 */
	public String getAccMgrUsername() {
		return accMgrusername;
	}
	
	/**
	 * set Password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set URL
	 * 
	 * @param url
	 */
	
	/**
	 * set URL1
	 * 
	 * @param url
	 */
	public void setURL(String url) {
		this.url = url;
	}

	
	/**
	 * Get URL
	 * 
	 * @return url
	 */
	public String getURL() {
		return url;
	}

	/**
	 * set browser
	 * 
	 * @param browser
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * get browser
	 * 
	 * @return browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * set DBusername
	 * 
	 * @param dBusername
	 */
	public void setDBusername(String dBusername) {
		dbUsername = dBusername;
	}

	/**
	 * get DBusername
	 * 
	 * @return dBusername
	 */
	public String getDBusername() {
		return dbUsername;
	}

	/**
	 * set dBpassword
	 * 
	 * @param dBpassword
	 */
	public void setDBpassword(String dBpassword) {
		dbpassword = dBpassword;
	}

	/**
	 * get dBpassword
	 * 
	 * @return dBpassword
	 */
	public String getDBpassword() {
		return dbpassword;
	}

	/**
	 * set dbURL
	 * 
	 * @param dbURL
	 */
	public void setDBurl(String dBurl) {
		dbURL = dBurl;
	}

	/**
	 * get dbURL
	 * 
	 * @return dbURL
	 */
	public String getDBurl() {
		return dbURL;
	}

	/**
	 * set dbDriver
	 * 
	 * @param dbDriver
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	/**
	 * get dbDriver
	 * 
	 * @return dbDriver
	 */
	public String getDbDriver() {
		return dbDriver;
	}
	public void setTestlinkURL(String testlinkURL) {
		this.testlinkURL = testlinkURL;
	}

	public String getTestlinkURL() {
		return testlinkURL;
	}

	public void setTestLinkDevKey(String testLinkDevKey) {
		this.testLinkDevKey = testLinkDevKey;
	}

	public String getTestLinkDevKey() {
		return testLinkDevKey;
	}

	public void setTestProject(String testProject) {
		this.testProject = testProject;
	}

	public String getTestProject() {
		return testProject;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}

	public String getTestPlan() {
		return testPlan;
	}

	public void setTestBuildId(String buildId) {
		this.testBuildId = buildId;
	}

	public String getTestBuildId() {
		return testBuildId;
	}

	public void setTestSuiteID(String suiteID) {
		this.testSuiteID = suiteID;
	}

	public String getTestSuiteID() {
		return testSuiteID;
	}
}
