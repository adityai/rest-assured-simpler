package com.iaditya.testng.rest_assured_sample;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


/**
 * Simple rest assured test suite to test simple response from a Get
 * 
 * @author adityai
 *
 */
public class SimpleResponseSmokeTest {

    private Workbook workbook = null;
    private static Object[][] testData = null;

    public SimpleResponseSmokeTest() {
    	initExcelReader();
	}
    
	/**
	 * Simple get response verification
	 */
	@Test(dataProvider="excelDataProvider")
	public void testGetResponseSuccess(String testCaseName, String serviceUri, String jsonPath, String jsonValueExpected, String httpStatusCodeExpected){
		Response response = RestAssured.given().contentType("application/json").when().get(serviceUri);
		System.out.println("******* " + response.getBody().asString());
		RestAssured.given().
			contentType(ContentType.JSON).
		when().
			get(serviceUri).
		then().
		assertThat().
			statusCode(Integer.parseInt(httpStatusCodeExpected)).
		assertThat().
			body(
					jsonPath, 
					equalTo(jsonValueExpected)
				);
		;
	}	


	  /**
	   * Load data from excel into an object array
	   */
	  private void initExcelReader() {
		  int i = 0;
		  int j = 0;
		  int rows = 0;
		  int columns = 0;
		  
	      try {
	          FileInputStream excelFile = new FileInputStream(new File("src/test/resources/ExcelTestData.xlsx"));
	          workbook = new XSSFWorkbook(excelFile);
	          Sheet sheet = workbook.getSheetAt(0);
	          rows = sheet.getPhysicalNumberOfRows();
	          columns = sheet.getRow(0).getPhysicalNumberOfCells();
	          
	          Iterator<Row> iterator = sheet.iterator();
	          testData = new String[rows - 1][columns];

	          iterator.next();
	          
	          while (iterator.hasNext()) {
	              Row currentRow = iterator.next();
	              Iterator<Cell> cellIterator = currentRow.iterator();
	        	  j = 0;
	              while (cellIterator.hasNext()) {
	                  Cell currentCell = cellIterator.next();
	                  if (currentCell.getCellTypeEnum() == CellType.STRING) {
	                      System.out.print(currentCell.getStringCellValue() + "--");
	                      testData[i][j] = currentCell.getStringCellValue();
	                  } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
	                      System.out.print(currentCell.getNumericCellValue() + "--");
	                      testData[i][j] = currentCell.getNumericCellValue();
	                  }
	                  j++;
	              }
	              i++;
	              System.out.println();
	          }
	      } catch (FileNotFoundException e) {
	          e.printStackTrace();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }

	  /**
	   * AfterSuite method to close the Excel file
	   */
	  @AfterSuite
	  private void closeExcel() {
		  try {
			  if (workbook != null) {
				  workbook.close();
			  }
		} catch (IOException e) {
			System.out.println("ERROR: While closing workbook");
			e.printStackTrace();
		}
		  
	  }

	  /**
	   * DataProvider loaded from test data present in the Excel file
	   * @param method
	   * @return
	   */
	  @DataProvider(name="excelDataProvider")
	  private Object[][] getData() {
		  return testData;
	  }

}
