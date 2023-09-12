package testscripts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utilities.SeleniumUtility;
import webpagesfororangehrm.AddEmployeePage;
import webpagesfororangehrm.HomePage;
import webpagesfororangehrm.LoginPage;
import webpagesfororangehrm.PersonalDetailsPage;
import webpagesfororangehrm.PimHomePage;
public class TestOrangeHrmPim extends SeleniumUtility{
	LoginPage getLoginPage;
	HomePage getHomePage;
	PimHomePage getPimHomePage;
	AddEmployeePage getAddEmployeePage;
	PersonalDetailsPage getPersonaldetails;
	
	@BeforeMethod
	public void precondition() {
		WebDriver driver=setUp("chrome", "https://opensource-demo.orangehrmlive.com");
		getLoginPage=new LoginPage(driver);
		getHomePage=new HomePage(driver);
		getPimHomePage=new PimHomePage(driver);
		getAddEmployeePage=new AddEmployeePage(driver);
		getPersonaldetails=new PersonalDetailsPage(driver);
		getLoginPage.loginIntoOrangeHrm("Admin", "admin123");
		getHomePage.clickOnPIM();
	}
	@Test(priority=1)
	public void testPimCreation() {
		getPimHomePage.clickOnAddButton();
		getAddEmployeePage.createEmployee("Mobile", "Lead", 123456789);
		getHomePage.clickOnPIM();
		getPimHomePage.searchCreatedEmployee(123456789);
		String acutalMsg=getPimHomePage.getSearchResultMsg();
		String expectedMsg="(1) Record Found";
		Assert.assertEquals(acutalMsg, expectedMsg);
	
}

	@Test(priority=2)
	 public void testPimEdit() {
		    getPimHomePage.searchCreatedEmployee(123456789);
		    getPimHomePage.clickonEdit();
		    getPersonaldetails.clickOnJob();
		    getPersonaldetails.selectdrop();
		  
		    getPersonaldetails.clickOndrop();
			getPersonaldetails.savebtn();
					
}
	@Test(priority=3)
	public void testPimDeletion() {
		getHomePage.clickOnPIM();
		getPimHomePage=new PimHomePage(driver);
		getPimHomePage.searchCreatedEmployee(123456789);
		getPimHomePage.deleteCreatedEmployee();
		String acutalMsg=getPimHomePage.getSearchResultMsg();
		String expectedMsg="No Records Found";
		Assert.assertEquals(acutalMsg, expectedMsg);
	}
	
	@AfterMethod
	public void postcondition() {
		getHomePage.logoutFromHrm();
		tearDown();
	}
}
