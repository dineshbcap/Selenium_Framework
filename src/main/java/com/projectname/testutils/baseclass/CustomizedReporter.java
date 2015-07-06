package com.projectname.testutils.baseclass;

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

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.projectname.testutils.genericutility.Config;

public class CustomizedReporter implements ITestListener, IReporter,
		ISuiteListener {

	private PrintWriter fout;
	private File screenshotDir;
	private final String outFolder = "custom-test-report";
	private String className;
	private static final String PASSED = "_Passed";
	private static final String FAILED = "_Failed";
	private static final String SKIPPED = "_Skipped";
	private static Boolean flag = true;
	private String OS = null;
	private String arch = null;
	private String browser = null;
	private String testStartedOn=null;
	private String testEndedOn=null;
	/**
	 * This function will execute before suite start
	 */
	public void onStart(ISuite suite) {
		// for get the current system time
		Calendar cal = Calendar.getInstance();				
		SimpleDateFormat DateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		testStartedOn = DateFormat1.format(cal.getTime());
		
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		// used to delete directory
		File directory = new File(workingdirectory + "/custom-test-report");

		// make sure directory exists
		if (directory.exists()) {
			try {
				delete(directory);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// used to create directory
		screenshotDir = new File(workingdirectory + "/custom-test-report");
		screenshotDir.mkdir();

		screenshotDir = new File(workingdirectory
				+ "/custom-test-report/Failure_Screenshot");
		screenshotDir.mkdir();
	}

	/**
	 * This function will execute after all suite done
	 */
	public void onFinish(ISuite suite) {
		// for get the current system time
				Calendar cal = Calendar.getInstance();				
				SimpleDateFormat DateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				testEndedOn = DateFormat1.format(cal.getTime());
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
			createRequiredDirectory(outFolder, className);
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

	private void createRequiredDirectory(String outdir, String className)
			throws IOException {
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		screenshotDir = new File(workingdirectory + "/custom-test-report" + "/"
				+ className);
		screenshotDir.mkdir();
	}

	private PrintWriter createRequiredFile(String testName) throws IOException {
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(
				screenshotDir, testName + ".html"), true)));
	}

	/**
	 * Used for create basic tags and styles need for generate report
	 * 
	 * @param out
	 */
	private void startHtmlPage(PrintWriter out, ITestResult result) {
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

		if (Config.retryCount == 0) {
			out.println("<b><i><u><h1>Test results </h1></u></i></b>");
			out.println("<b><i><h2><u>Test Name: " + className + "."
					+ result.getName() + "</u></h2></i></b>");
		}

		out.println("<table border=\"1\">");
		out.println("<tbody>");

		if (Config.retryCount > 0) {
			out.println("<tr class=\"title\" title=\"\" alt=\"\">");
			out.println("<td colspan=\"6\"> Retry Attempt: "
					+ ((Config.retryCount)) + "</td>");
			out.println("</tr>");
		}
		out.println("<br><br>");
		out.println("<tr style='background-color: #B2ACAC;'>");
		out.println("<td><b><i>Selenium-Command</i></b></td>");
		out.println("<td><b><i>Parameter-1</i></b></td>");
		out.println("<td><b><i>Parameter-2</i></b></td>");
		out.println("<td><b><i>Status</i></b></td>");
		out.println("<td><b><i>Calling-Class with Linenumber</i></b></td>");
		out.println("<td><b><i>Screenshot</i></b></td>");
		out.println("</tr>");
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
				temp[5] = "<a href=\'" + pathToScreenshot + "\'> <img src=\'"
						+ pathToScreenshot
						+ "\' height=\"100\" width=\"100\"> </a>";
			}
			// After split up, If the third array value contains 'title' means
			// that is title
			else if (temp[3].toLowerCase().contains("title")) {
				// So, If it is a title then create '<tr>' tag with class name
				// 'title'
				fout.println("<tr class=\"title\" title=\"\" alt=\"\">");
				fout.println("<td colspan=\"6\">"+"<img src='../images/info.png' title=\"Info\" height=\"10\" width=\"10\">" + temp[0] +"</td>");
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
			for (String temp1 : temp) {
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
        OS = System.getProperty("os.name");
        browser= Config.browser;
        arch=System.getProperty("os.arch");
        
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
					new File(workingdirectory + "/custom-test-report"),
					"index.html"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Creating #3D pie chart using google javaScript api's
		fout.println("<html>");
		fout.println("<head>");
		fout.println("<style>");
		fout.println("#piechart_3d {");
		fout.println(" position: absolute;");
		fout.println(" top: 0px;");
		fout.println(" right: 0Px;");
		fout.println("width: 0px;");
		fout.println("height: 0px;");
		fout.println("}");
		fout.println("</style>");
		fout.println("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">");
		fout.println("google.load(\"visualization\",\"1\",{packages:[\"corechart\"]});");
        fout.println("google.setOnLoadCallback(drawChart);");
        fout.println("function drawChart() {");
        fout.println("var data = google.visualization.arrayToDataTable([");
        fout.println("['TestStatus', 'Count'],");
        fout.println("['Passed ',"+totalPassedMethods+"],");
        fout.println("['Failed ',"+totalFailedMethods +"],");
        fout.println("['Skiped ',"+totalSkippedMethods+"]");
        fout.println("]);");
        fout.println("var options = {");
        fout.println("  title: 'Test Case Report','width':550,'height':420,");
        fout.println("is3D: true,};");
        fout.println("var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));");
        fout.println("chart.draw(data, options);");
        fout.println("}");
        fout.println("</script>");
        fout.println("</head>");
        fout.println("<body>");
        fout.println(" <div id=\"piechart_3d\" style=\"width: 400px; height: 200px;\"></div>");
        fout.println("</body>");
        fout.println(" </html>");
		
        
		// Write initial html codes neccessary for report
		fout.println("<html>");
		fout.println("<head>");
		fout.println("<title>Test results</title>");
		fout.println("<style type=\"text/css\">");
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
		fout.println("</head>");
		fout.println("<body>");
		fout.println("<b><i><u><h1>Test results </h1></u></i></b>");

		fout.println("<table border=\"1\">");
		fout.println("<tr style='background-color:#F5F5F5;'>");
		fout.println("<td align=\"center\" colspan=\"2\"> <h3> System Information </h3> </td>");
		fout.println("</tr>");
		fout.println("<tr>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>Operating System</i></b></td>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>"+OS+"</i></b></td>");
		fout.println("</tr>");
		fout.println("<tr>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>System Architecture</i></b></td>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>"+arch+"</i></b></td>");
		fout.println("</tr>");
		fout.println("<tr>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>Browser</i></b></td>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>"+browser+"</i></b></td>");
		fout.println("</tr>");
		fout.println("<tr>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>Test Started On</i></b></td>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>"+testStartedOn+"</i></b></td>");
		fout.println("</tr>");
		fout.println("<tr>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>Test Ended On</i></b></td>");
		fout.println("<td align=\"justify\" class=\"report\"><b><i>"+testEndedOn+"</i></b></td>");
		fout.println("</tr>");
		fout.println("</table>");
		fout.println("</br>");
		fout.println("</br>");
		
		
		fout.println("<table border=\"1\">");
		fout.println("<tr style='background-color: ;'>");
		fout.println("<td align=\"center\" colspan=\"4\"> <h3> Report Overview </h3> </td>");
		fout.println("</tr>");
		fout.println("<tr style='background-color: #F5F5F5;'>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>Total Test Methods</i></b></td>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>Passed Test Methods</i></b></td>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>Failed Test Methods</i></b></td>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>Skipped Test Methods</i></b></td>");
		fout.println("</tr>");
		fout.println("<tr>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>"
				+ totalMethods + "</i></b></td>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>"
				+ totalPassedMethods + "</i></b></td>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>"
				+ totalFailedMethods + "</i></b></td>");
		fout.println("<td align=\"center\" class=\"report\"><b><i>"
				+ totalSkippedMethods + "</i></b></td>");
		fout.println("</tr>");
		fout.println("</table>");
		fout.println("<br>");
		fout.println("<br>");
		fout.println("<br>");
		// Passed cases
		generateIndexHtmlAreas(PASSED);
		// Failed Cases
		generateIndexHtmlAreas(FAILED);
		// Skipped Cases
		generateIndexHtmlAreas(SKIPPED);
		// used for create end html tags
		fout.println("</tbody>");
		fout.println("</body></html>");
		// used for write every thing in html file
		fout.flush();
		fout.close();
		sendMail();
		
	}
	public void sendMail() {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		// Get system properties
		System.setProperty("java.net.preferIPv4Stack" , "true");
	      Properties properties = System.getProperties();
		// Setup mail server
	      properties.put("mail.transport.protocol", "smtp");
	     // properties.put("mail.smtp.socketFactory.port", "465");
	     // properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	     // properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.auth", true);
	      properties.put("mail.smtp.host", "smtp.gmail.com");
	      properties.put("mail.smtp.auth", true);
	      //properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getInstance(properties,
	              new javax.mail.Authenticator() {
	                  protected PasswordAuthentication getPasswordAuthentication() {
	                      return new PasswordAuthentication(Config.mailFrom, "kinjalsaranya");
	                  }
	              });
		 try{
	         // Create a default MimeMessage object.
	         MimeMessage msg = new MimeMessage(session);

	         // Set From: header field of the header.
	         msg.setFrom(new InternetAddress(Config.mailFrom));

	         // Set To: header field of the header.
	         msg.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(Config.mailTo));

	         // Set Subject: header field
	         msg.setSubject("This is the Subject Line!");

	         File file= new File(TestBaseClass.screenshotBasePath + File.separator
						+ "custom-test-report"+File.separator+"index.html");
	         // Now set the actual message
	         DataSource source = new FileDataSource(file);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("index.html");
	         multipart.addBodyPart(messageBodyPart);
	         msg.setSubject("Execution Report");
	         msg.setContent(multipart);

	         // Send message
	         Transport.send(msg);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		}

	public void generateIndexHtmlAreas(String status) {
		// for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		File file = new File(workingdirectory + "/custom-test-report");
		String[] names = file.list();
		fout.println("<table border=\"1\">");
		fout.println("<tbody>");
		flag = true;
		for (String fullClassName : names) {
			String splitClassName[] = fullClassName.split("\\.");
			int length = splitClassName.length;
			if (!(fullClassName.equalsIgnoreCase("Failure_Screenshot"))) {
				if (new File(workingdirectory + "/custom-test-report/"
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
							+ "/custom-test-report/" + fullClassName);
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
									fout.println("<tr style='background-color: #ccffcc;'>");
									fout.println("<td align=\"center\" colspan=\"3\"> Passed cases </td>");
									fout.println("</tr>");
								} else if (status.equalsIgnoreCase(FAILED)) {
									// Failed
									fout.println("<tr style='background-color: #ffcccc;'>");
									fout.println("<td align=\"center\" colspan=\"3\"> Failed cases </td>");
									fout.println("</tr>");
								} else {
									// Skipped
									fout.println("<tr style='background-color: #B2ACAC;'>");
									fout.println("<td align=\"center\" colspan=\"3\">"
											+ "Skipped cases" + "</td>");
									fout.println("</tr>");
								}

								fout.println("<tr style='background-color: #F5F5F5;'>");
								fout.println("<td><b><i>Package Name</i></b></td>");
								fout.println("<td><b><i>Class Name With Test Name</i></b></td>");
								fout.println("<td align=\"center\" class=\"result\"><b><i>Result</i></b></td>");
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
							fout.println("<td>" + temp + "</td>");
							if (status.equalsIgnoreCase(PASSED)) {
								// Passed
								fout.println("<td align=\"center\" class='result'><img src='images/Tick_Mark.png' height=\"20\" width=\"20\">"
										+ "</td>");
							} else if (status.equalsIgnoreCase(FAILED)) {
								// Failed
								fout.println("<td align=\"center\" class='result'><img src='images/Fail_Mark.jpg' height=\"20\" width=\"20\">"
										+ "</td>");
							} else {
								// Skipped
								fout.println("<td align=\"center\" class='result'>"
										+ SKIPPED + "</td>");
							}
							fout.println("</tr>");
						}
					}

				}
			}
		}
		fout.println("</table>");
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
