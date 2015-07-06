package com.projectname.testutils.genericutility;

public class Config {

	//Config
	
	//Flag for to On/Off detailed log  
	public static Boolean requireToWrite = true; 
	
	//Retry Analyzer Initialization
	//Change here to specify the number of attempts
	public static final int RETRYCOUNTER = 1; 
	
	public static int retryCount=0;
	
	//TestLink Update
	public static Boolean testLinkUpdate = false;
	//Browser
	public static String browser="FIREFOX";

	//Credentials
	public static String URL="http://systems.aspiresys.com/";
	public static String userName="priya.marimuthu";
	public static String password="riya@123";
	public static String mailFrom="priya.marinuthucom";
	public static String mailTo="saranya.subramani@aspiresys.com";
	
	//Identifier Types
	public static final String NAME = "name";
	public static String CSS = "cssSelector";
	public static String ID = "id";
	public static String XPATH = "xpath";
	public static String LINK = "link";

	//Time Variables
	public static int DELAY_TIME = 2;
	public static int MINOR_WAIT_TIME = 7;
	public static int WAIT_TIME = 10;
	public static int AVG_WAIT_TIME_FOR_ELEMENT = 15;
	public static int MAX_WAIT_TIME_FOR_ELEMENT = 30;
	
	
}
