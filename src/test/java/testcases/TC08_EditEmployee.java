package testcases;

import org.testng.annotations.Test;
import pages.P02_EmployeesDataPage;
import pages.P08_EditEmployeePage;

import static drivers.DriverHolder.getDriver;

public class TC08_EditEmployee  extends TestBase{


    @Test(description = "Add employee - Complete data entry in single flow without page reload")
    public void testEditEmployeeWithRequiredData_P() throws InterruptedException {


        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
        listPage.navigateToPage().clickEditButton();

        P08_EditEmployeePage editPage = new P08_EditEmployeePage(getDriver());

Thread.sleep(4000);
        editPage.setEmployeeNameAr("شسيسششسيسشيسشي")
                .clickSave();

    }
    }
