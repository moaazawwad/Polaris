package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P05_EmployeeOrgStructurePage;

import static drivers.DriverHolder.getDriver;

public class TC05_VerifyEmployeeOrgStructurePage extends TestBase {

    @Test(description = "Verify Employee Org Structure page loads and table has data")
    public void verifyEmployeeOrgStructureTableHasData() {
        P05_EmployeeOrgStructurePage orgStructurePage = new P05_EmployeeOrgStructurePage(getDriver());
        orgStructurePage.navigateToPage();

        boolean hasData = orgStructurePage.verifyTableHasData();
        Assert.assertTrue(hasData, "Employee Org Structure table is empty - no data found");
    }
}
