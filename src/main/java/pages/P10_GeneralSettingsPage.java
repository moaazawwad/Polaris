package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P10_GeneralSettingsPage extends PageBase {

    private static final By PAGE_HEADER_OR_TITLE = By.xpath("//*[contains(text(),'الإعدادات العامة') or contains(text(),'الرواتب')]");
    private static final By SOCIAL_INSURANCE_TEXT = By.xpath("//*[contains(text(),'التأمينات الاجتماعية')]");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-content')]//div[contains(@class,'p-toast-summary') and contains(text(),'تم بنجاح')]");

    public P10_GeneralSettingsPage(WebDriver driver) {
        super(driver);
    }
    /** ضغط حفظ */
    public P10_GeneralSettingsPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public P10_GeneralSettingsPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/payroll-wages/master-data/general-settings");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER_OR_TITLE));
        return this;
    }

    public boolean verifyPageContainsSocialInsuranceText() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(SOCIAL_INSURANCE_TEXT));
            return driver.findElement(SOCIAL_INSURANCE_TEXT).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    /** Wait for success toast (تم بنجاح) to appear. Returns true if displayed. */
    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return driver.findElement(SUCCESS_TOAST).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
