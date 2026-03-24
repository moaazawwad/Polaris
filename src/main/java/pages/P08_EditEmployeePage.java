package pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.Utility;

public class P08_EditEmployeePage extends PageBase {

    public P08_EditEmployeePage(WebDriver driver) {
        super(driver);
    }

    private final By employeeNameAr = By.cssSelector("[data-testid='nameAr']");
    private final By employeeNameEn = By.cssSelector("[data-testid='nameEn']");
    private final By saveButton = By.xpath("//button[.//span[contains(text(),'حفظ')]]");
    /** Success toast: "تم بنجاح" / "تم تعديل البيانات بنجاح" */
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-content')]//div[contains(@class,'p-toast-summary') and contains(text(),'تم بنجاح')]");

    public P08_EditEmployeePage setEmployeeNameEn(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(employeeNameEn));
        input.clear();
        input.sendKeys(value);
        return this;
    }
    public P08_EditEmployeePage setEmployeeNameAr(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(employeeNameAr));
        input.clear();
        input.sendKeys(value);
        return this;
    }

    public P08_EditEmployeePage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(saveButton));
        btn.click();
        return this;
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
