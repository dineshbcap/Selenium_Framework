package utils.genericutility;

public final class Config {

	//Config
	private Config(){
		
	}
	//Flag for to On/Off detailed log  
	public static final Boolean REQUIRETOWRITE = true; 
	
	//Retry Analyzer Initialization
	//Change here to specify the number of attempts
	public static final int RETRYCOUNTER = 1; 
	
	public static int RETRYCOUNT=0;
	
	//TestLink Update
	public static final Boolean TESTLINKUPDATE = false;
	//Browser
	public static final String BROWSER="FIREFOX";
	

	//Credentials
	public static final String URL="http://systems.aspiresys.com/";
	public static final String MAILFROM="priya.marimuthu@aspiresys.com";
	public static final String MAILTO="saranya.subramani@aspiresys.com";
	public static final String MAILPWD="proxima@123";
	public static final String MAILSMTP="mail.aspiresystems.net";

	//Time Variables
	public static final int DELAYTIME = 2;
	public static final int MINORWAITTIME = 7;
	public static final int WAITTIME = 10;
	public static final int AVGWAITTIMEFORELEMENT = 15;
	public static final int MAXWAITTIMEFORELEMENT = 30;
	
	
}
