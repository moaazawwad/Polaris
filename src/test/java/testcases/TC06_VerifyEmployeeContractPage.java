package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P06_EmployeeContractPage;

import static drivers.DriverHolder.getDriver;

public class TC06_VerifyEmployeeContractPage extends TestBase {

    @Test(description = "Verify Employee Contract page loads and table has data")
    public void verifyEmployeeContractTableHasData() {
        P06_EmployeeContractPage contractPage = new P06_EmployeeContractPage(getDriver());
        contractPage.navigateToPage();

        boolean hasData = contractPage.verifyTableHasData();
        Assert.assertTrue(hasData, "Employee Contract table is empty - no data found");
    }
}
