package utils.genericutility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateTimeUtility {

	/**
	 * This method returns the current date and time in format dd-MMM-yyyy HH-mm-ss
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getCurrentDateAndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}
	
	/**
	 * This method returns the current date and time in format dd-MMM-yyyy HH-mm-ss
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getCurrentDateAndTimeInLoggerFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}
	/**
	 * This method returns the current date and time in format dd-MMM-yy hh.mm.ss.SSSSSSSSS a
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getCurrentDateAndTimeDDMMYY() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}
	
	/**
	 * This method returns the current year
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}

	/**
	 * This method returns the current date in format MM/dd/yyyy
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		String today = dateFormat.format(date.getTime());
		return today;
	}
	
	/**
	 * This method returns the current date in format MM/dd/yyyy
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hhmm");
		String today = dateFormat.format(date.getTime());
		return today;
	}

	/**
	 * This method returns the yesterday date in format MM/dd/yyyy
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getYesterday() {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String yesterday = dateFormat.format(date.getTime() - (1 * MILLIS_IN_DAY));
		return yesterday;
	}

	/***
	 * This method returns the prev date of the date parameter passed
	 * 
	 * @param testDate
	 * @return prevDate - in format MM/dd/yyyy
	 */
	@SuppressWarnings("deprecation")
	public static String getPrevday(String testDate) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		Date date = new Date(testDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String yesterday = dateFormat.format(date.getTime() - (1 * MILLIS_IN_DAY));
		return yesterday;
	}

	/***
	 * This method returns the next date of the date parameter passed
	 * 
	 * @param testDate
	 * @return nextDate - in format MM/dd/yyyy
	 */
	@SuppressWarnings("deprecation")
	public static String getNextday(String testDate) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		Date date = new Date(testDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String yesterday = dateFormat.format(date.getTime() + (1 * MILLIS_IN_DAY));
		return yesterday;
	}

	/***
	 * This method returns the formatted date of the date parameter passed
	 * 
	 * @param testDate
	 * @return date - in format DD-MMM-yyyy
	 */
	public static String formatDateToDDMonYYYY(String dateToformat) throws ParseException {		
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/***
	 * This method returns the formatted date of the date parameter passed
	 * 
	 * @param testDate
	 * @return date - in format DD-MMM-yyyy
	 */
	public static String formatDateToyyyyMMdd(String dateToformat) throws ParseException {		
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	/***
	 * This method returns the formatted date of the date parameter passed
	 * 
	 * @param testDate
	 * @return date - in format dd/MM/yy
	 */
	public static String formatDateToDDMMYY(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}

	/***
	 * This method returns the formatted date of the date parameter passed
	 * 
	 * @param testDate
	 * @return nextDate - in format dd-MM-yy
	 */
	public static String formatDateToDDMMYYYY(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-dd-mm");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/mm/yyyy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/***
	 * This method returns the formatted date of the date parameter passed
	 * 
	 * @param testDate
	 * @return nextDate - in format dd-MM-yy
	 */
	public static String formatDateToddMMYYYY(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/***
	 * This method returns the formatted date of the date parameter
	 * 
	 * @param testDate
	 * @return date - in format MM/dd/yy
	 */
	public static String formatDateToMMDDYYYY(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}

	/***
	 * This method returns the formatted date of the date parameter
	 * 
	 * @param testDate
	 * @return date - in format dd/MM/yy
	 */
	public static String formatDateToddMMMYY(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/**
	 * This method returns the current date in format dd/MM/yy
	 * 
	 * @return date - in the above mentioned format
	 */
	public static String getTodayddMMyy() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String today = dateFormat.format(date.getTime());
		return today;
	}
	/***
	 * This method returns the formatted date of the date parameter
	 * 
	 * @param testDate
	 * @return date - in format MM/dd/yy
	 */
	public static String formatDateToddMMyyyy(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/***
	 * This method returns the formatted date of the date parameter
	 * 
	 * @param testDate
	 * @return date - in format dd/MMM/yy
	 */
	public static String formatDateToddMMMyy(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/***
	 * This method returns the formatted date of the date parameter
	 * 
	 * @param testDate
	 * @return date - in format dd/MMM/yy
	 */
	public static String formatDateToMMddyyyy(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yy");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = format1.parse(dateToformat);
		return format2.format(date);
	}
	
	/***
	 * This method returns the formatted date of the date parameter
	 * 
	 * @param testDate
	 * @return date - in format MM/dd/yy
	 */
	public static String formatDateToMMddYYYY(String dateToformat) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = format1.parse(dateToformat);
		if(dateToformat.equals("")){
			return dateToformat;
		}
		return format2.format(date);
		
	}
	
	/**
	 * This method verifies whether the given date and time is in format dd.MM.yyyy hh:mma
	 * 
	 * @return true/false
	 */
	public static boolean verifyDateTimeFormatddmmyy(String dateToVerify,String datetimeFormat) {
		try{
			String dateTimeFormatToVerify=datetimeFormat.substring(0,16);
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormatToVerify);
		dateFormat.parse(dateToVerify);
		if(datetimeFormat.length()>16)
		{
			String am=dateToVerify.substring(16,17);
			if(am.equals("p")||am.equals("a")){
				return true;
			}
			else {
				return false;
			}
		}else{
			return true;
		 }
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * This method returns the current date in format dd/MM/yy
	 * 
	 * @return date - in the above mentioned format
	 */
	public static ArrayList<String> getTimeForWebclock(String time) {
		
		String[] st1=time.split(":");
		String st2 = st1[0];
		String st3 = st1[1];
		int j = Integer.parseInt(st3);
		int s = j-1;
		int d = j+1;
		String befTime = ""+s;
		String afTime = ""+d;
		if(j==59)
		{
			afTime = "00";
		}
		if(j==0)
		{
			befTime = "59";
		}
		ArrayList<String> data = new ArrayList<String>();
		data.add(st2);
		data.add(st3);
		data.add(befTime);
		data.add(afTime);		
		return data;

	}
}