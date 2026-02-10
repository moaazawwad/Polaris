package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P03_JobPositionPage;

import static drivers.DriverHolder.getDriver;

public class TC03_VerifyJobPositionPage extends TestBase {

    @Test(description = "Verify Job Position page loads and table has data")
    public void verifyJobPositionTableHasData() {
        P03_JobPositionPage jobPositionPage = new P03_JobPositionPage(getDriver());
        jobPositionPage.navigateToPage();

        boolean hasData = jobPositionPage.verifyTableHasData();
        Assert.assertTrue(hasData, "Job Position table is empty - no data found");
    }
}
