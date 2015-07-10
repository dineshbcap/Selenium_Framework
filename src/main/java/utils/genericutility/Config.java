package utils.genericutility;

public class Config {

	//Config
	
	//Flag for to On/Off detailed log  
	public static final Boolean requireToWrite = true; 
	
	//Retry Analyzer Initialization
	//Change here to specify the number of attempts
	public static final int retryCounter = 1; 
	
	public static int retryCount=0;
	
	//TestLink Update
	public static final Boolean testLinkUpdate = false;
	//Browser
	public static final String browser="FIREFOX";

	//Credentials
	public static final String url="http://systems.aspiresys.com/";
	public static final String userName="priya.marimuthu";
	public static final String password="riya@123";
	public static final String mailFrom="priya.marimuthu@aspiresys.com";
	public static final String mailTo="saranya.subramani@aspiresys.com";
	public static final String mailPwd="proxima@123";
	public static final String mailSmtp="mail.aspiresystems.net";

	//Time Variables
	public static final int delayTime = 2;
	public static final int minorWaitTime = 7;
	public static final int waitTime = 10;
	public static final int avgWaitTimeForElement = 15;
	public static final int maxWaitTimeForElement = 30;
	
	
}
