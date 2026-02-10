package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P01_LoginPage;

import static drivers.DriverHolder.getDriver;

public class TC01_OpenWebsite extends TestBase {

    @Test(description = "Verify website opens successfully")
    public void verifyWebsiteOpens() {
        P01_LoginPage loginPage = new P01_LoginPage(getDriver());

        String currentUrl = loginPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("hrmicro"),
                "Website did not open correctly!");
    }
}