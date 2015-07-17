package utils.testdatareader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelReader {

	/**
	 * findRowColumnCount function to establish an initial connection with a
	 * work sheet
	 * 
	 * @param sheet
	 * @param rowColumnCount
	 *            (Hashtable)
	 * @return Hashtable (returns row count and column count)
	 * @since April 04, 2010
	 */
	private ExcelReader(){
		
	}
	public static Hashtable<String, Integer> findRowColumnCount(
			HSSFSheet sheet, Hashtable<String, Integer> rowColumnCount) {

		HSSFRow row = null;
		int rows;
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0;
		int tmp = 0;
		int counter = 0;
		String temp = null;

		for (int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				temp = convertHSSFCellToString(row.getCell(0));
				if (!temp.equals("")) {
					counter++;
				}
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols) {
					cols = tmp;
				}
			}
		}

		rowColumnCount.put("RowCount", counter);
		rowColumnCount.put("ColumnCount", cols);

		return rowColumnCount;
	}

	/**
	 * readExcelHeaders function to establish an initial connection with a work
	 * sheet
	 * 
	 * @param sheet
	 * @param excelHeaders
	 *            (Hashtable)
	 * @param rowColumnCount
	 *            (Hashtable)
	 * @return Hashtable (Having Header column values)
	 * @since April 04, 2010
	 */
	public static Hashtable<String, Integer> readExcelHeaders(HSSFSheet sheet,
			Hashtable<String, Integer> excelHeaders,
			Hashtable<String, Integer> rowColumnCount) {

		HSSFRow row = null;
		HSSFCell cell = null;
		for (int r = 0; r < rowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);

			if (row != null) {
				for (int c = 0; c < rowColumnCount.get("ColumnCount"); c++) {
					cell = row.getCell(c);
					if (cell != null) {
						excelHeaders.put(cell.toString(), c);
					}
				}
				break;
			}
		}
		return excelHeaders;
	}

	/**
	 * convertHSSFCellToString function will convert the HSSFCell type value to
	 * its equivalent string value
	 * 
	 * @param cell
	 * @return String
	 * @since April 04, 2010
	 */
	public static String convertHSSFCellToString(HSSFCell cell) {
		String cellValue = null;
		if (cell != null) {
			cellValue = cell.toString();
			cellValue = cellValue.trim();
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	/**
	 * Fetch the test data for a test case based on test case ID
	 * 
	 * @param testCaseId
	 * @param testClassName
	 * @return
	 */
	public static ArrayList<HashedMap> getTestDataByTestCaseId(
			String testCaseId, String testClassName) {
		String filePath = "";
		String sheetName = "";
		String fileName = "";
		HSSFRow row = null;
		HSSFCell cell = null;
		ArrayList<HashedMap> data = new ArrayList<HashedMap>();

		try {

			String basePath = new File(".").getCanonicalPath() + File.separator
					+ "src" + File.separator + "main" + File.separator
					+ "resources" + File.separator + "testdata"
					+ File.separator;
			String configFilePath = basePath + "Config-TD.xls";
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					configFilePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("Config");

			// Function call to find excel header fields
			Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();
			excelrRowColumnCount = findRowColumnCount(sheet,
					excelrRowColumnCount);
			// Get test data set
			for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {
				row = sheet.getRow(r);
				if (row != null) {
					HSSFCell tempCell = sheet.getRow(r).getCell(0);
					if (tempCell != null) {
						String testClass = convertHSSFCellToString(row
								.getCell(0));

						if (testClass.equalsIgnoreCase(testClassName)) {
							cell = sheet.getRow(r).getCell(1);
							if (cell != null)
								fileName = convertHSSFCellToString(row
										.getCell(1));
							cell = sheet.getRow(r).getCell(2);
							if (cell != null)
								sheetName = convertHSSFCellToString(row
										.getCell(2));
							break;
						}
					}
				}
			}
			filePath = basePath + fileName;
			data = getTestData(filePath, fileName, sheetName, testCaseId);

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Fetch the test data for a test case based on test case ID
	 * 
	 * @param filePath
	 * @param workBook
	 * @param sheetName
	 * @return testData
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static ArrayList<HashedMap> getTestData(String filePath,
			String workBook, String sheetName, String testCaseId)
			throws FileNotFoundException, IOException {
		HSSFRow row = null;
		HSSFCell cell = null;

		// Establish connection to work sheet
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheet(sheetName);
		Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();
		excelrRowColumnCount = findRowColumnCount(sheet, excelrRowColumnCount);

		// function call to find excel header field
		HashedMap data = null;
		ArrayList<String> header = new ArrayList<String>();
		ArrayList<String> matcher = null;
		ArrayList<HashedMap> matcherList = new ArrayList<HashedMap>();

		// Get all header
		row = sheet.getRow(0);
		if (row != null) {
			for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {
				cell = sheet.getRow(0).getCell(c);
				if (cell != null) {
					String temp = convertHSSFCellToString(row.getCell(c));
					header.add(temp);
				}
			}
		}

		// Get test data set
		for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);
			if (row != null) {
				HSSFCell tempCell = sheet.getRow(r).getCell(0);
				if (tempCell != null) {
					String tcID = convertHSSFCellToString(row.getCell(0));
					if (tcID.equalsIgnoreCase(testCaseId)) {
						data = new HashedMap();
						matcher = new ArrayList<String>();
						matcher.add(tcID);
						for (int c = 1; c < excelrRowColumnCount
								.get("ColumnCount"); c++) {
							cell = sheet.getRow(r).getCell(c);
							String temp = convertHSSFCellToString(row
									.getCell(c));
							matcher.add(temp);
						}
						// Add all the test data to a Map
						for (int i = 0; i < matcher.size(); i++) {
							data.put(header.get(i), matcher.get(i));
						}
						matcherList.add(data);
					}
				}
			}
		}

		return matcherList;
	}
}
