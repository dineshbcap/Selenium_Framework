package utils.baseclass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx4j.log.Log;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import utils.genericutility.Config;

public class CustomizedReporter implements ITestListener, IReporter,
		ISuiteListener {

	private PrintWriter fout;
	private File screenshotDir;
	private final String outFolder = "custom-test-report";
	private String className;
	private static final String PASSED = "_Passed";
	private static final String FAILED = "_Failed";
	private static final String SKIPPED = "_Skipped";
	private Boolean flag = true;
	private String os = null;
	private String arch = null;
	private String JavaVersion=null;
	private String testStartedOn=null;
	private String testEndedOn=null;
	
	/**
	 * Standard log4j logger.
	 */
	protected final Logger log = Logger.getLogger(getClass().getSimpleName());
	
	
	/**
	 * This function will execute before suite start
	 */
	public void onStart(ISuite suite) {
		// for get the current system time
		Calendar cal = Calendar.getInstance();				
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		testStartedOn = dateFormat1.format(cal.getTime());
		
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		// used to delete directory
		File directory = new File(workingdirectory + "/"+outFolder);

		// make sure directory exists
		if (directory.exists()) {
			try {
				delete(directory);

			} catch (IOException e) {
				log.info(e.toString());
			}
		}
		// used to create directory
		screenshotDir = new File(workingdirectory + "/"+outFolder);
		screenshotDir.mkdir();

		screenshotDir = new File(workingdirectory
				+ "/"+outFolder+"/Failure_Screenshot");
		screenshotDir.mkdir();
	}

	/**
	 * This function will execute after all suite done
	 */
	public void onFinish(ISuite suite) {
		// for get the current system time
				Calendar cal = Calendar.getInstance();				
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				testEndedOn = dateFormat1.format(cal.getTime());
	}

	/**
	 * This function will execute before test start
	 */
	public void onStart(ITestContext context) {
		try {
			// used to get the test class name
			className = context.getCurrentXmlTest().getClasses().get(0)
					.getName();
			// used for creating required directories
			createRequiredDirectory(className);
		} catch (IOException e) {
			e.getMessage();
		}
	}

	/**
	 * This function will execute once the current test execution pass
	 */
	public void onTestSuccess(ITestResult result) {
		try {
			// used to write result details in html
			generateTestExecution(result, PASSED);
		} catch (IOException e) {
			e.getMessage();
		}

	}

	/**
	 * This function will execute once the current test execution fail
	 */
	public void onTestFailure(ITestResult result) {
		try {
			// used to write result details in html
			generateTestExecution(result, FAILED);
		} catch (IOException e) {
			e.getMessage();
		}

	}

	/**
	 * This function will execute once the current test execution skiped
	 */
	public void onTestSkipped(ITestResult result) {
		try {
			// used to write result details in html
			generateTestExecution(result, SKIPPED);
		} catch (IOException e) {
			e.getMessage();
		}

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	private void createRequiredDirectory(String className)
			throws IOException {
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		screenshotDir = new File(workingdirectory + "/"+outFolder+ "/"
				+ className);
		screenshotDir.mkdir();
	}

	private PrintWriter createRequiredFile(String testName) throws IOException {
		String workingdirectory = System.getProperty("user.dir");
		new File(workingdirectory+"\\temp.txt");
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(
				screenshotDir, testName + ".html"), true)));
	}

	/**
	 * Used for create basic tags and styles need for generate report
	 * 
	 * @param out
	 */
	private void startHtmlPage(PrintWriter out, ITestResult result) {
		if (Config.RETRYCOUNT == 0) {
		out.println("<html>");
		out.println("<head>");
		out.println("<meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\"/><meta content=\"cache-control\" http-equiv=\"no-cache\"/><meta content=\"pragma\" http-equiv=\"no-cache\"/>");
		out.println("<style type=\"text/css\">");
		out.println("body, table {");
		out.println("font-family: Verdana, Arial, sans-serif;");
		out.println("font-size: 12;");
		out.println("}");

		out.println("table {");
		out.println("border-collapse: collapse;");
		out.println("border: 1px solid #ccc;");
		out.println("}");

		out.println("th, td {");
		out.println("padding-left: 0.3em;");
		out.println("padding-right: 0.3em;");
		out.println("width: 150px;");
		out.println("}");

		out.println("a {");
		out.println("text-decoration: none;");
		out.println("}");

		out.println(".title {");
		out.println("font-style: italic,bold;");
		out.println("background-color: #D3D3D3;");
		out.println("}");

		out.println(".selected {");
		out.println("background-color: #ffffcc;");
		out.println("}");

		out.println(".status_done {");
		out.println("background-color: #eeffee;");
		out.println("}");

		out.println(".status_passed {");
		out.println("background-color: #ccffcc;");
		out.println("}");

		out.println(".status_failed {");
		out.println("background-color: #ffcccc;");
		out.println("}");

		out.println(".status_maybefailed {");
		out.println("background-color: #ffffcc;");
		out.println("}");

		out.println(".breakpoint {");
		out.println("background-color: #cccccc;");
		out.println("border: 1px solid black;");
		out.println("}");
		out.println("</style>");
		out.println("<title>Test results</title>");
		out.println("</head>");
		out.println("<body>");
			out.println("<b><i><u><h1>Test results </h1></u></i></b>");
			out.println("<b><i><h2><u>Test Name: " + className + "."
					+ result.getName() + "</u></h2></i></b>");
		out.println("<table border=\"1\">");
		out.println("<tbody>");
		out.println("<br><br>");
		out.println("<tr style='background-color: #B2ACAC;'>");
		out.println("<td><b><i>Status</i></b></td>");
		out.println("<td><b><i>Selenium-Command</i></b></td>");
		out.println("<td><b><i>Parameter-1</i></b></td>");
		out.println("<td><b><i>Parameter-2</i></b></td>");
		out.println("<td><b><i>Calling-Class with Linenumber</i></b></td>");
		out.println("</tr>");
		}
		if (Config.RETRYCOUNT > 0) {
			out.println("<table border=\"1\">");
			out.println("<tr class=\"title\" title=\"\" alt=\"\">");
			fout.println("<td align=\"middle\"><img src='../images/info.png' title=\"Info\" height=\"20\" width=\"20\" ></td>");
			out.println("<td style=\"color:red;\" colspan=\"5\"><b> Retry Attempt: "
					+ ((Config.RETRYCOUNT)) + "</b></td>");
			out.println("</tr>");
		}
	}

	/**
	 * This function will execute after test execution
	 */
	private void onFinish() {
		// used for create end html tags
		endHtmlPage(fout);
		// used for write every thing in html file
		fout.flush();
		fout.close();
	}

	/**
	 * Finishes HTML Stream
	 * 
	 * @param out
	 */
	private void endHtmlPage(PrintWriter out) {
		out.println("</table>");
		out.println("</tbody>");
		out.println("</body></html>");
	}

	/**
	 * Used to write test results in file
	 * 
	 * @param result
	 * @throws IOException
	 */
	private void generateTestExecution(ITestResult result, String status)
			throws IOException {
		// create the html file with current running class and test name
		fout = createRequiredFile(result.getName() + status);
		// Write initial html codes neccessary for report
		startHtmlPage(fout, result);
		// get all the attributes set during the test execution
		Object[] array = result.getAttributeNames().toArray();
		// Above got values are not in sort. So, Sorting that based on time
		Arrays.sort(array);
		// Iterating the array value to generate report
		for (Object name : array) {
			// Each and every array value contains, All the info about the
			// particular action
			// And Values combined using deliminator. So, split using
			// deliminator
			String temp[] = result.getAttribute((String) name).toString()
					.split("####");
			// After split up, If the third array value contains 'Fail' means
			// that step failed
			if (temp[3].toLowerCase().contains("fail")) {
				// If Fail create '<tr>' tag with 'status_failed' class(Which is
				// used for create 'red' background color for failed cases)
				temp[3]="<center><img src='../images/fail.jpg' title=\"Failed\" height=\"20\" width=\"20\"></center>";
				fout.println("<tr class=\"status_failed\" title=\"\" alt=\"\">");
				// If failed in constructor, the function name will be like
				// '<init>'.
				// So, if it is like that, we can not use that name as a file
				// name
				// So, Replacing the value with empty space
				temp[4] = temp[5].replace(".<init>", "");
				// create the screenshot path
				String pathToScreenshot = "../Failure_Screenshot/" + temp[4]
						+ ".jpg";
				// creating mapping for failed step(Link to screen shot and
				// embed the screenshot in that step)
				temp[4] = temp[4]+"<a href=\'" + pathToScreenshot + "\'> <center><img src=\'"
						+ pathToScreenshot
						+ "\' height=\"100\" width=\"100\"> </center></a>";
			}
			// After split up, If the third array value contains 'title' means
			// that is title
			else if (temp[3].toLowerCase().contains("title")) {
				// So, If it is a title then create '<tr>' tag with class name
				// 'title'
				fout.println("<tr class=\"title\" title=\"\" alt=\"\">");
				fout.println("<td align=\"middle\"><img src='../images/info.png' title=\"Info\" height=\"20\" width=\"20\" ></td>");
				fout.println("<td colspan=\"5\">"+temp[0]+"</td>");
				String workingdirectory = System.getProperty("user.dir");
				try {
				    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(workingdirectory+"/temp.txt", true)));
				    out.println(temp[0]);
				    out.close();
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
				fout.println("</tr>");
				continue;
			}
			// Else status is passed
			else {
				fout.println("<tr class=\"status_passed\" title=\"\" alt=\"\">");
				temp[3]="<center><img src='../images/pass.jpg' title=\"Pass\" height=\"20\" width=\"20\"></center>";
				temp[4]=temp[5];
				temp[5]="";
			}
			// this will create separate '<td>' for messages inside the action
			fout.println("<td>" + temp[3] + "</td>");
			for (String temp1 : temp) {
					if(temp1.contains("fail.jpg")||temp1.contains("pass.jpg")||temp1.contains("info.png"))
					{
						continue;
					}
					if(temp1.equals(temp[5]))
					{
						continue;
					}
					
					fout.println("<td>" + temp1 + "</td>");
			}
			// end up '<tr>' tag
			fout.println("</tr>");
		}
		// this used for write some end up html tags
		onFinish();
		// make sure directory exists
		if (screenshotDir.exists()) {
			if (status.equalsIgnoreCase("_Passed")) {
				String files[] = screenshotDir.list();
				for (String temp : files) {
					if ((temp.equalsIgnoreCase(result.getName() + FAILED
							+ ".html"))) {
						fout = new PrintWriter(new BufferedWriter(
								new FileWriter(new File(screenshotDir, result
										.getName() + FAILED + ".html"), true)));
						File file = new File(screenshotDir, result.getName()
								+ PASSED + ".html");
						String aLine;
						BufferedReader bf = new BufferedReader(new FileReader(
								file));
						while ((aLine = bf.readLine()) != null) {
							fout.println(aLine);
						}
						bf.close();
						// this used for write some end up html tags
						onFinish();

						file = new File(screenshotDir, result.getName()
								+ PASSED + ".html");
						file.delete();

						File oldName = new File(screenshotDir, result.getName()
								+ FAILED + ".html");
						File newName = new File(screenshotDir, result.getName()
								+ PASSED + ".html");
						oldName.renameTo(newName);
					}
				}
			}
		}
	}

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		
		// get OS information, browser information, Execution Info 
        os = System.getProperty("os.name");
        arch=System.getProperty("os.arch");
        JavaVersion=System.getProperty("java.version") ;
        
		// Overview report block
		int totalPassedMethods = 0;
		int totalFailedMethods = 0;
		int totalSkippedMethods = 0;
		int totalMethods = 0;

		// Iterating over each suite included in the test
		for (ISuite suite : suites) {
			// Following code gets the suite name
			// Getting the results for the said suite
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				totalMethods = totalMethods + tc.getAllTestMethods().length;
				totalPassedMethods = totalPassedMethods
						+ tc.getPassedTests().getAllResults().size();
				totalSkippedMethods = totalSkippedMethods
						+ tc.getSkippedTests().getAllResults().size();
				totalFailedMethods = totalMethods
						- (totalPassedMethods + totalSkippedMethods);
			}
		}
		
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		// create the html file with current running class and test name
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(new File(
					new File(workingdirectory + "/"+outFolder),
					"index.html"))));
		} catch (IOException e) {
			log.info(e.toString());
		}
		
		fout.println("<!DOCTYPE html>");
		fout.println("<html>");
		fout.println("<body onload=\"trim()\" style=\"border: 1px solid #000000;\">");
		
		fout.println("<script>");
		fout.println("function trim()");
		fout.println("{");
		fout.println("var final=document.getElementById(\"AddressSpan1\").innerHTML;");
		fout.println("if(final.length>20)");
		fout.println("{");
		fout.println("document.getElementById(\"AddressSpan1\").title = final;");
		fout.println(" document.getElementById(\"AddressSpan1\").innerHTML=\"\";");
		fout.println(" for(var i = 0; i < 17; i++)");
		fout.println(" {");
		fout.println(" document.getElementById(\"AddressSpan1\").innerHTML= document.getElementById(\"AddressSpan1\").innerHTML+final[i];");
		fout.println("}");
		fout.println("for(i=17;i<=19;i++)");
		fout.println(" {");
		fout.println("document.getElementById(\"AddressSpan1\").innerHTML= document.getElementById(\"AddressSpan1\").innerHTML+'.';");
		fout.println("}");
		fout.println("}");
		fout.println("}");
	    fout.println("</script>");
		
		
		fout.println("<style>");
		fout.println("#backgroundDiv {");
		fout.println(" background-color: #F9F9F9;");
		fout.println("top : 20px;");
		fout.println("width: 200px;");
		fout.println("height: 400px; ");
		fout.println("width: 1200px; ");
		fout.println("margin: 15px auto");
		fout.println("}");

		fout.println("#HeaderDiv{");
		fout.println("background-color: #F9F9F9;");
		fout.println("height: 50px;");
		fout.println(" line-height: 65Px;");
		fout.println("}");

		fout.println("#HeaderList{");
		fout.println("list-style-type: none;");
		fout.println("}");

		fout.println(".boxFamily{");
		fout.println("  text-align: center;"); 
		fout.println(" font-family: 'Source Sans Pro',Calibri;  ");
		fout.println(" font-size: 20px; ");
		fout.println(" line-height: 30px;");
		fout.println("}");

		fout.println(".boxFamilySmall{");
		fout.println("   text-align: center;");
		fout.println("  font-size: 13px;");
		fout.println("}");
		fout.println("</style>");
		
		fout.println("</style>");
		fout.println("<div id=\"HeaderDiv\" align=center>");
		fout.println("<center style=\"font-family: 'Source Sans Pro',Calibri; color: #3232FF; font-size: 26px;\"><b>Test Summary Report</b></center>");
		fout.println("</div>");
		fout.println("<div align=center id=\"backgroundDiv\">");

		fout.println("<html>");
		fout.println("<body>");
		fout.println("<style>");
		fout.println("#InitialChart {");
		fout.println("  position: absolute;");
		fout.println("top: 65px;");
		fout.println("right:925Px;");
		fout.println("width: 0px;");
		fout.println(" height: 0px;");
		fout.println(" padding: 20px; ");
		fout.println("}");
		fout.println("</style>");

		fout.println("<html>");
		fout.println(" <head>");
		fout.println(" <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
		fout.println(" <script type=\"text/javascript\">");
		fout.println("   google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
		fout.println(" google.setOnLoadCallback(drawChart);");
		fout.println("  function drawChart() {");
		fout.println("   var data = google.visualization.arrayToDataTable([");
		fout.println("     ['Test Sumamry', 'Test Case Count'],");
		fout.println("     ['Passed',  "+  totalPassedMethods +"],");
		fout.println("     ['Failed',   "+  totalFailedMethods +"],");
		fout.println("     ['Skipped', "+  totalSkippedMethods  +"],");
		fout.println(" ]);");
		fout.println("  var options = {");
		fout.println(" title: 'Test Summary','width':380,'height':380,");
		fout.println("colors:['#AADD00','#EE0000','#FFFF7E'],");
		fout.println("'chartArea': {'width': '70%', 'height': '60%'},");
		fout.println("legend:{position:'bottom'},");
		fout.println(" pieSliceText: 'value',");
		fout.println("pieHole: 0.4,");
		fout.println(" };");
	    fout.println(" var chart = new google.visualization.PieChart(document.getElementById('InitialChart'));");
	    fout.println("chart.draw(data, options);");
	    fout.println("}");
	    fout.println("</script>");
	    fout.println(" </head>");
	    fout.println("<body>");
		fout.println("  <div id=\"InitialChart\" style=\"width: 320px; height: 320px; position: absolute; \"></div>");
		fout.println("</body>");
		fout.println("</html>");

		fout.println("<style>");
		fout.println("#TotalCount {");
		fout.println("  position: absolute;");
		fout.println("top: 65px;");
		fout.println(" right:695Px;");
		fout.println(" padding: 20px; ");
		fout.println(" }");
		fout.println("</style>");
		fout.println("<html>");
		fout.println(" <head>");
		fout.println("   <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
		fout.println("  <script type=\"text/javascript\">");
		fout.println("    google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
		fout.println("   google.setOnLoadCallback(drawChart);");
		fout.println("   function drawChart() {");
		fout.println("     var data = google.visualization.arrayToDataTable([");
		fout.println("      ['Total Test Methods', 'Total Test Methods'],");
		fout.println("       ['Total',   "+ totalMethods   +"],");
		fout.println(" ]);");
		fout.println("var options = {");
		fout.println("  title: 'Total Test Methods','width':190,'height':190,");
		fout.println("colors:['#00B2EE'],");
		fout.println("  pieHole: 0.4,");
		fout.println("  pieSliceText: 'value',");
		fout.println("  pieSliceTextStyle: {");
		fout.println("    color: 'black',");
		fout.println("  },");
		fout.println("  legend: 'none'");
		fout.println(" };");
		fout.println(" var chart = new google.visualization.PieChart(document.getElementById('TotalCount'));");
		fout.println(" chart.draw(data, options);");
		fout.println("  }");
		fout.println("</script>");
		fout.println(" </head>");
		fout.println(" <body>");
		fout.println("<div id=\"TotalCount\" style=\"width: 160px; height: 160px;\"></div>");
		fout.println(" </body>");
		fout.println("</html>");
		fout.println("<style>");

		fout.println("#PassCount {");
		fout.println("position: absolute;");
		fout.println(" top: 65px;");
		fout.println("right:495Px;");
		fout.println(" padding: 20px;");
		fout.println(" }");
		fout.println("</style>");

		fout.println("<html>");
		fout.println("  <head>");
		fout.println("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
		fout.println("<script type=\"text/javascript\">");
		fout.println("  google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
		fout.println("google.setOnLoadCallback(drawChart);");
		fout.println("function drawChart() {");
		fout.println("  var data = google.visualization.arrayToDataTable([");
		fout.println("    ['Test Sumamry', 'Total Pass Methods'],");
		fout.println("   ['Total',   "+ totalMethods   +"],");
		fout.println("  ['Passed',  "+  totalPassedMethods  +"],");
		fout.println(" ]);");
		fout.println(" var options = {");
		fout.println(" title: 'Total Pass Methods','width':190,'height':190,");
		fout.println("colors:['#00B2EE','#AADD00'],");
		fout.println(" pieHole: 0.4,");
		fout.println(" pieSliceTextStyle: {");
		fout.println("color: 'black',");
		fout.println(" },");
		fout.println(" legend: 'none'");
		fout.println("  };");
		fout.println(" var chart = new google.visualization.PieChart(document.getElementById('PassCount'));");
		fout.println("  chart.draw(data, options);");
		fout.println(" }");
		fout.println("</script>");
		fout.println(" </head>");
		fout.println("<body>");
		fout.println("<div id=\"PassCount\" style=\"width: 160px; height: 160px;\"></div>");
		fout.println(" </body>");
		fout.println("</html>");

		fout.println("<style>");
		fout.println("#FailCount {");
		fout.println(" position: absolute;");
		fout.println("top: 65px;");
		fout.println(" right:295Px;");
		fout.println("padding: 20px; ");
		fout.println(" }");
		fout.println("</style>");

		fout.println("<html>");
		fout.println(" <head>");
		fout.println("  <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
		fout.println("  <script type=\"text/javascript\">");
		fout.println("   google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
		fout.println(" google.setOnLoadCallback(drawChart);");
		fout.println(" function drawChart() {");
		fout.println("  var data = google.visualization.arrayToDataTable([");
		fout.println("    ['Test Sumamry', 'Total Fail Methods'],");
		fout.println("   ['Total',   "+totalMethods    +"],");
		fout.println("  ['Failed',  "+ totalFailedMethods   +"],");
		fout.println(" ]);");
		fout.println(" var options = {");
		fout.println("   title: 'Total Fail Methods','width':190,'height':190,");
		fout.println("colors:['#00B2EE','#EE0000'],");
		fout.println("   pieHole: 0.4,");
		fout.println("  pieSliceTextStyle: {");
		fout.println("     color: 'black',");
		fout.println("   },");
		fout.println("   legend: 'none'");
		fout.println(" };");
		fout.println("  var chart = new google.visualization.PieChart(document.getElementById('FailCount'));");
		fout.println(" chart.draw(data, options);");
		fout.println("}");
		fout.println(" </script>");
		fout.println(" </head>");
		fout.println("<body>");
		fout.println("<div id=\"FailCount\" style=\"width: 160px; height: 160px;\"></div>");
		fout.println("</body>");
		fout.println("</html>");

		fout.println("<style>");
		fout.println("#SkipCount {");
		fout.println(" position: absolute;");
		fout.println("top: 65px;");
		fout.println("right:95Px;");
		fout.println(" padding: 20px; ");
		fout.println(" }");
		fout.println("</style>");

		fout.println("<html>");
		fout.println(" <head>");
		fout.println(" <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
		fout.println(" <script type=\"text/javascript\">");
		fout.println("  google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
		fout.println("  google.setOnLoadCallback(drawChart);");
		fout.println("  function drawChart() {");
		fout.println("    var data = google.visualization.arrayToDataTable([");
		fout.println("     ['Test Sumamry', 'Total Skip Methods'],");
		fout.println("     ['Total',   "+ totalMethods   +"],");
		fout.println("	  ['Skipped',  "+  totalSkippedMethods  +"],");
		fout.println("   ]);");
		fout.println("   var options = {");
		fout.println("     title: 'Total Skip Methods','width':190,'height':190,");
		fout.println("  pieHole: 0.4,");
		fout.println("colors:['#00B2EE','#FFFF7E'],");
		fout.println(" pieSliceTextStyle: {");
		fout.println(" color: 'black',");
		fout.println(" },");
		fout.println("legend: 'none'");
		fout.println(" };");
		fout.println("var chart = new google.visualization.PieChart(document.getElementById('SkipCount'));");
		fout.println(" chart.draw(data, options);");
		fout.println("}");
		fout.println("</script>");
		fout.println(" </head>");
		fout.println(" <body>");
		fout.println("  <div id=\"SkipCount\" style=\"width: 160px; height: 160px;\">");
		fout.println("</div>");
		fout.println(" </body>");
		fout.println("</html>");

		fout.println("<style>");
		fout.println(".boxWidth{");
		fout.println(" position: absolute;");
		fout.println(" width: 217px; ");
		fout.println(" height: 45px; ");
		fout.println(" background-color:#F9F9F9; ");
		fout.println(" text-align: center;");
		fout.println("}");
		fout.println("</style>");

		fout.println("<style>");
		fout.println("#OS {");
		fout.println(" position: absolute;");
		fout.println(" top: 285px;");
		fout.println(" right:615Px;");
		fout.println("padding: 20px;");
		fout.println(" border: 1px solid #B4BFC3; ");
	    fout.println(" }");
	    fout.println("</style>");
	    fout.println("<div id=\"OS\" class=\"boxWidth\">");
	    fout.println("<span class=\"boxFamily\">"+ os  +"</span>");
	    fout.println("<br>");
	    fout.println("<span style=\"text-align: center;font-size: 13px;\">OS</span>");
	    fout.println("</div>");
	    
	    fout.println("<style>");
	    fout.println("#osArch {");
	    fout.println("  position: absolute;");
	    fout.println(" top: 285px;");
	    fout.println(" right:350Px;");
	    fout.println(" padding: 20px;");
	    fout.println(" border: 1px solid #B4BFC3; ");
	    fout.println(" }");
	    fout.println("</style>");
	    fout.println("<div id=\"osArch\" class=\"boxWidth\">");
	    fout.println("<span class=\"boxFamily\">"+ arch +"</span>");
	    fout.println("<br>");
	    fout.println("<span style=\"text-align: center;font-size: 13px;\">OS Arch</span>");
	    fout.println("</div>");

	    fout.println("<style>");
	    fout.println("#JavaVersion {");
	    fout.println(" position: absolute;");
	    fout.println(" top: 285px;");
	    fout.println(" right:85px;");
	    fout.println(" padding: 20px; ");
	    fout.println(" border: 1px solid #B4BFC3;");
	    fout.println("}");
	    fout.println("</style>");
	    fout.println("<div id=\"JavaVersion\" class=\"boxWidth\">");
		fout.println("<span class=\"boxFamily\">"+ JavaVersion +"</span>");
		fout.println("<br>");
		fout.println("<span class=\"boxFamilySmall\">Java Ver.</span>");
		fout.println("</div>");

		fout.println("<style>");
		fout.println("#Address {");
		fout.println(" position: absolute;");
		fout.println(" top: 380px;");
		fout.println(" right:615Px;");
		fout.println(" padding: 20px; ");
		fout.println(" border: 1px solid #B4BFC3;");
		fout.println(" }	");
		fout.println("</style>");
		fout.println("<div id=\"Address\" class=\"boxWidth\">");
		fout.println("<span id=\"AddressSpan1\" class=\"boxFamily\">"+ Config.URL +"</span>");
		fout.println("<br>");
		fout.println("<span class=\"boxFamilySmall\">URL</span>");
		fout.println("</div>");

		fout.println("<style>");
		fout.println("#TestStartedOn {");
		fout.println(" position: absolute;");
		fout.println(" top: 380px;");
		fout.println(" right:350Px;");
		fout.println(" padding: 20px; ");
		fout.println(" border: 1px solid #B4BFC3;");
		fout.println(" }");
		fout.println("</style>");
		fout.println("<div id=\"TestStartedOn\" class=\"boxWidth\">");
		fout.println("<span class=\"boxFamily\">"+ testStartedOn +"</span>");
		fout.println("<br>");
		fout.println("<span class=\"boxFamilySmall\">TestStartedOn</span>");
		fout.println("</div>");

		fout.println("<style>");
		fout.println("#TestEndedOn{");
		fout.println(" position: absolute;");
		fout.println(" top: 380px;");
		fout.println(" right:85Px;");
		fout.println(" padding: 20px;");
		fout.println(" border: 1px solid #B4BFC3; ");
		fout.println(" }");
		fout.println("</style>");
		fout.println("<div id=\"TestEndedOn\" class=\"boxWidth\">");
		fout.println("<span class=\"boxFamily\">"+ testEndedOn +"</span>");
		fout.println("<br>");
		fout.println("<span class=\"boxFamilySmall\">TestEndedOn</span>");
		fout.println("</div>");
		fout.println("</div>");
		fout.println("<br>");
		fout.println("<br>");
		fout.println("</body>");
		fout.println("</html>");

		
        
		// Write initial html codes neccessary for report
		fout.println("<html>");
		fout.println("<body>");
		fout.println("<style type=\"text/css\">");
		fout.println("<br>");
		fout.println("<br>");
		fout.println("body, table {");
		fout.println("font-family: Verdana, Arial, sans-serif;");
		fout.println("font-size: 12;");
		fout.println("}");
		fout.println("table {");
		fout.println("border-collapse: collapse;");
		fout.println("border: 1px solid #ccc;");
		fout.println("table-layout: fixed;");
		fout.println("}");
		fout.println("th, td {");
		fout.println("padding-left: 0.3em;");
		fout.println("padding-right: 0.3em;");
		fout.println("border: 1px solid black;");
		fout.println("overflow: hidden;");
		fout.println("width: 400px;");
		fout.println("}");
		fout.println(".result {");
		fout.println("padding-left: 0.3em;");
		fout.println("padding-right: 0.3em;");
		fout.println("border: 1px solid black;");
		fout.println("overflow: hidden;");
		fout.println("width: 100px;");
		fout.println("}");
		fout.println(".report {");
		fout.println("padding-left: 0.3em;");
		fout.println("padding-right: 0.3em;");
		fout.println("border: 1px solid black;");
		fout.println("overflow: hidden;");
		fout.println("width: 200px;");
		fout.println("}");
		fout.println("</style>");
		
		fout.println("<center  style=\"font-family: 'Source Sans Pro',Calibri; color: #3232FF;\"><b>Choose a filter:</b></center>");
		fout.println("<select style=\"margin-left:47%;\"  align=\"center\" id=\"dropDown\" onchange=\"changeDropDown()\">");
		fout.println("<option selected value=\"All\">All</option>");
		fout.println("<option value=\"Passed\">Passed</option>");
		fout.println("<option value=\"Failed\">Failed</option>");
		fout.println("<option value=\"Skipped\">Skipped</option>");
		fout.println("</select>");
		fout.println("<br>");
		fout.println("<br>");
	    fout.println("<br>");

	    
	    fout.println("<script type=\"text/javascript\">");
	    fout.println("function changeDropDown(){");
	    fout.println("var tabToDisplay=document.getElementById(\"dropDown\").value;");
	    fout.println("if(tabToDisplay && tabToDisplay==\"All\") {");
	    fout.println("document.getElementById(\"updateValue\").innerHTML =\"\";");
	    fout.println("document.getElementById(\"passTable\").style.display = \"initial\";");
	    fout.println("document.getElementById(\"failTable\").style.display = \"initial\";");
	    fout.println("document.getElementById(\"skipTable\").style.display = \"initial\";");
	    fout.println("}");
	    fout.println("else if(tabToDisplay && tabToDisplay==\"Passed\") {");
	    fout.println("var myElem = document.getElementById('passTable');");
	    fout.println("if (myElem == null)");
	    fout.println("document.getElementById(\"updateValue\").innerHTML = \"Search Result Not Found.\";");
	    fout.println(" else");
	    fout.println("document.getElementById(\"updateValue\").innerHTML = \"\";");
	    fout.println("document.getElementById(\"passTable\").style.display = \"initial\";");
	    fout.println("document.getElementById(\"failTable\").style.display = \"none\";");
	    fout.println("document.getElementById(\"skipTable\").style.display = \"none\";");
	    fout.println("}");
	    fout.println("else if(tabToDisplay && tabToDisplay==\"Failed\"){");
	    fout.println("var myElem = document.getElementById('failTable');");
	    fout.println("if (myElem == null)");
	    fout.println("document.getElementById(\"updateValue\").innerHTML = \"Search Result Not Found.\";");
	    fout.println("else");
	    fout.println("document.getElementById(\"updateValue\").innerHTML = \"\";");
	    fout.println("document.getElementById(\"passTable\").style.display = \"none\";");
	    fout.println("document.getElementById(\"failTable\").style.display = \"initial\";");
	    fout.println("document.getElementById(\"skipTable\").style.display = \"none\";");
	    fout.println("}");
	    fout.println("else if(tabToDisplay && tabToDisplay==\"Skipped\") {");
	    fout.println("var myElem = document.getElementById('skipTable');");
	    fout.println("if (myElem == null)");
	    fout.println("document.getElementById(\"updateValue\").innerHTML = \"Search Result Not Found.\";");
	    fout.println("else");
	    fout.println("document.getElementById(\"updateValue\").innerHTML = \"\";");
	    fout.println("document.getElementById(\"passTable\").style.display = \"none\";");
	    fout.println("document.getElementById(\"failTable\").style.display = \"none\";");
	    fout.println("document.getElementById(\"skipTable\").style.display = \"initial\";");
	    fout.println("}");
	    fout.println("}");
	    fout.println("</script>");
		// Passed cases
		generateIndexHtmlAreas(PASSED);
		// Failed Cases
		generateIndexHtmlAreas(FAILED);
		// Skipped Cases
		generateIndexHtmlAreas(SKIPPED);
		// used for create end html tags
		fout.println("</tbody>");
		fout.println("<br>");
		fout.println("<p align=center id=\"updateValue\"></p>");
		fout.println("</body></html>");
		// used for write every thing in html file
		fout.flush();
		fout.close();
		sendMail();
		
	}
	public void sendMail() {
		String screenshotBasePath;
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		// Get system properties
		System.setProperty("java.net.preferIPv4Stack" , "true");
	      Properties properties = System.getProperties();
		// Setup mail server
	      properties.put("mail.transport.protocol", "smtp");
	      properties.put("mail.smtp.socketFactory.port", "465");
	      properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.auth", true);
	      properties.put("mail.smtp.host", Config.MAILSMTP);
	
	      // Get the default Session object.
	      Session session = Session.getInstance(properties,
	              new javax.mail.Authenticator() {
	                  protected PasswordAuthentication getPasswordAuthentication() {
	                      return new PasswordAuthentication(Config.MAILFROM,Config.MAILPWD);
	                  }
	              });
		 try{
	         // Create a default MimeMessage object.
	         MimeMessage msg = new MimeMessage(session);

	         // Set From: header field of the header.
	         msg.setFrom(new InternetAddress(Config.MAILFROM));

	         // Set To: header field of the header.
	         msg.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(Config.MAILTO));

	         // Set Subject: header field
	         msg.setSubject("This is the Subject Line!");
	        screenshotBasePath = new File(".").getCanonicalPath();
			File file= new File(screenshotBasePath + File.separator
						+ outFolder+File.separator+"index.html");
	         // Now set the actual message
	         DataSource source = new FileDataSource(file);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("index.html");
	         multipart.addBodyPart(messageBodyPart);
	         msg.setSubject("Execution Report");
	         msg.setContent(multipart);

	         // Send message
	         Transport.send(msg);
	      }catch (MessagingException mex) {
	    	  log.info(mex.toString());
	      }catch (IOException e) {
				// TODO Auto-generated catch block
	    	  log.info(e.toString());
			}
		}

	public void generateIndexHtmlAreas(String status) {
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		File file = new File(workingdirectory + "/"+outFolder);
		String[] names = file.list();
		flag = true;
		for (String fullClassName : names) {
			String splitClassName[] = fullClassName.split("\\.");
			int length = splitClassName.length;
			if (!(fullClassName.equalsIgnoreCase("Failure_Screenshot"))) {
				if (new File(workingdirectory + "/"+outFolder+"/"
						+ fullClassName).isDirectory()) {
					String fullPackage = "";

					for (int temp = 0; temp < length - 1; temp++) {
						if (temp == 0) {
							fullPackage = splitClassName[temp];
						} else {
							fullPackage = fullPackage + "."
									+ splitClassName[temp];
						}
					}
					File file1 = new File(workingdirectory
							+ "/"+outFolder+"/" + fullClassName);
					String[] names1 = file1.list();

					int totalRowCountToMerge = 0;
					boolean packageFlag = true;
					for (String testName : names1) {
						if (testName.endsWith(status + ".html")) {
							totalRowCountToMerge = totalRowCountToMerge + 1;
						}
					}
					for (String testName : names1) {
						if (testName.endsWith(status + ".html")) {
							if (flag) {
								if (status.equalsIgnoreCase(PASSED)) {
									// Passed
									fout.println("<table align=center id=\"passTable\" style=\"font-family: \'Source Sans Pro\',Calibri; width:90%; font-size: 18px; border: 2px solid #B4BFC3;\" bgcolor=\"#F9F9F9\">");
									fout.println("<tbody>");
									fout.println("<tr bgcolor=\"#F9F9F9\" style=\"font-family: 'Source Sans Pro',Calibri;  font-size: 18px; border: 2px solid #B4BFC3\";>");
									fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" colspan=\"3\"><b> Passed cases</b> </td>");
									fout.println("</tr>");
								} else if (status.equalsIgnoreCase(FAILED)) {
									// Failed
									fout.println("<table align=center id=\"failTable\" style=\"font-family: \'Source Sans Pro\',Calibri; width:90%; font-size: 18px; border: 2px solid #B4BFC3;\" bgcolor=\"#F9F9F9\">");
									fout.println("<tbody>");
									fout.println("<tr bgcolor=\"#F9F9F9\" style=\"font-family: 'Source Sans Pro',Calibri;  font-size: 18px; border: 2px solid #B4BFC3\";>");
									fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" colspan=\"3\"> <b>Failed cases</b> </td>");
									fout.println("</tr>");
								} else {
									// Skipped
									fout.println("<table align=center id=\"skipTable\" style=\"font-family: \'Source Sans Pro\',Calibri; width:90%; font-size: 18px; border: 2px solid #B4BFC3;\" bgcolor=\"#F9F9F9\">");
									fout.println("<tbody>");
									fout.println("<tr bgcolor=\"#F9F9F9\" style=\"font-family: 'Source Sans Pro',Calibri;  font-size: 18px; border: 2px solid #B4BFC3\";>");
									fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" colspan=\"3\">"+ "<b>Skipped cases</b>" + "</td>");
									fout.println("</tr>");
								}

								fout.println("<tr bgcolor=\"#F9F9F9\" style=\"font-family: 'Source Sans Pro',Calibri;  font-size: 18px; border: 2px solid #B4BFC3;\">");
								fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\"><b><i>Package Name</i></b></td>");
								fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\"><b><i>Class Name With Test Name</i></b></td>");
								fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" class=\"result\"><b><i>Result</i></b></td>");
								fout.println("</tr>");
								flag = false;
							}
							String temptestName = testName.replace(status
									+ ".html", "");
							fout.println("<tr>");
							if (packageFlag) {
								fout.println("<td rowspan='"
										+ totalRowCountToMerge + "'>"
										+ fullPackage + "</td>");
								packageFlag = false;
							}
							String temp = "<a href='" + fullClassName + "/"
									+ testName + "'>"
									+ splitClassName[length - 1] + "."
									+ temptestName + "</a>";
							fout.println("<td style=\"border: 2px solid #B4BFC3;\">" + temp + "</td>");
							if (status.equalsIgnoreCase(PASSED)) {
								// Passed
								fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" class='result'><img src='images/Tick_Mark.png' height=\"20\" width=\"20\">"
										+ "</td>");
							} else if (status.equalsIgnoreCase(FAILED)) {
								// Failed
								fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" class='result'><img src='images/Fail_Mark.jpg' height=\"20\" width=\"20\">"
										+ "</td>");
							} else {
								// Skipped
								fout.println("<td style=\"border: 2px solid #B4BFC3;\" align=\"center\" class='result'>"
										+ SKIPPED + "</td>");
							}
							fout.println("</tr>");
						}
					}

				}
			}
		}
		fout.println("</table> <br> ");
	}

	/**
	 * This function is used for delete all the files and folders in the
	 * directory
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					if (!(temp.equalsIgnoreCase("images"))) {
						// recursive delete
						delete(fileDelete);
					}
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			// if file, then delete it
			file.delete();
		}
	}
}
