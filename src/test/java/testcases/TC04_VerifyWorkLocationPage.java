package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P04_WorkLocationPage;

import static drivers.DriverHolder.getDriver;

public class TC04_VerifyWorkLocationPage extends TestBase {

    @Test(description = "Verify Work Location page loads and table has data")
    public void verifyWorkLocationTableHasData() {
        P04_WorkLocationPage workLocationPage = new P04_WorkLocationPage(getDriver());
        workLocationPage.navigateToPage();

        boolean hasData = workLocationPage.verifyTableHasData();
        Assert.assertTrue(hasData, "Work Location table is empty - no data found");
    }
}
