package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P10_GeneralSettingsPage;

import static drivers.DriverHolder.getDriver;

/**
 * Test cases for General Settings (Payroll) page load verification.
 */
public class TC10_VerifyGeneralSettingsPage extends TestBase {

    @Test(priority = 1, description = "Verify General Settings page loads and contains التأمينات الاجتماعية")
    public void verifyGeneralSettingsPageContainsSocialInsurance() {
        P10_GeneralSettingsPage page = new P10_GeneralSettingsPage(getDriver());
        page.navigateToPage();

        boolean hasText = page.verifyPageContainsSocialInsuranceText();
        Assert.assertTrue(hasText, "General Settings page does not contain التأمينات الاجتماعية");
    }

    @Test(priority = 2, description = "Click Save and verify success toast appears")
    public void saveGeneralSettings_VerifySuccessToast() throws InterruptedException {
        P10_GeneralSettingsPage page = new P10_GeneralSettingsPage(getDriver());
        page.navigateToPage();
        Thread.sleep(2000);
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الضغط على حفظ");
    }
}
