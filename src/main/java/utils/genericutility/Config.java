package utils.genericutility;

public class Config {

	//Config
	
	//Flag for to On/Off detailed log  
	public static final Boolean requireToWrite = true; 
	
	//Retry Analyzer Initialization
	//Change here to specify the number of attempts
	public static final int RETRYCOUNTER = 1; 
	
	public static int retryCount=0;
	
	//TestLink Update
	public static final Boolean testLinkUpdate = false;
	//Browser
	public static final String browser="FIREFOX";
	

	//Credentials
	public static final String url="http://systems.aspiresys.com/";
	public static final String MAILFROM="priya.marimuthu@aspiresys.com";
	public static final String MAILTO="saranya.subramani@aspiresys.com";
	public static final String MAILPWD="proxima@123";
	public static final String MAILSMTP="mail.aspiresystems.net";

	//Time Variables
	public static final int DELAYTIME = 2;
	public static final int minorWaitTime = 7;
	public static final int waitTime = 10;
	public static final int AVGWAITTIMEFORELEMENT = 15;
	public static final int MAXWAITTIMEFORELEMENT = 30;
	
	
}
