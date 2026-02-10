package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P02_EmployeesDataPage;

import static drivers.DriverHolder.getDriver;

/**
 * Test cases for Employees Data Page
 */
public class TC02_VerifyEmployeesDataPage extends TestBase {

//    @Test(description = "Verify Employees Data page loads and table contains data")
//    public void testEmployeesDataTableHasData() {
//        P02_EmployeesDataPage employeesPage = new P02_EmployeesDataPage(getDriver());
//        employeesPage.navigateToPage();
//
//        boolean hasData = employeesPage.verifyTableHasData();
//        Assert.assertTrue(hasData, "Employees table is empty - no data found");
//    }

    @Test(description = "Verify user can delete an employee successfully")
    public void testDeleteEmployee() throws InterruptedException {
        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());

        listPage
                .navigateToPage()
                .clickDeleteButton()
                .clickConfirmDeleteButton()
                .waitForDeleteSuccess();
    }
//
//    @Test(description = "Verify delete operation for specific employee row")
//    public void testDeleteSpecificEmployee() {
//        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
//
//        listPage
//                .navigateToPage()
//                .clickDeleteButton(2)
//                .clickConfirmDeleteButton()
//                .waitForDeleteSuccess();
//    }
}