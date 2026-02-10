package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class P02_EmployeesDataPage extends PageBase {

    // Page Locators
    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'هوية الموظف')]");
    private static final By DATA_CELLS = By.cssSelector("div.font-semibold.text-sm");

    // Action Buttons
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    private static final By FILTER_BUTTON = By.xpath("//button[@type='button']//img[contains(@src,'button-filter.svg')]/ancestor::button");

    // Table Action Buttons
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";

    // Delete Confirmation
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");

    // Success Messages
    private static final By SUCCESS_MESSAGE = By.xpath("//*[contains(text(),'تم الحذف') or contains(text(),'تم') or contains(text(),'نجح')]");

    // Search Input
    private static final By SEARCH_INPUT = By.xpath("//input[@placeholder='بحث' and contains(@class,'searchInputBorderBottom')]");

    public P02_EmployeesDataPage(WebDriver driver) {
        super(driver);
    }

    /** Navigate to page */
    public P02_EmployeesDataPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/employees-data");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    /** Scroll to element */
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element
        );
        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }

    /** Click Add button */
    public P02_EmployeesDataPage clickAddButton() {
        longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON)).click();
        return this;
    }

    /** Click Filter button */
    public P02_EmployeesDataPage clickFilterButton() {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(FILTER_BUTTON)).click();
        return this;
    }

    /** Click View (first row by default) */
    public P02_EmployeesDataPage clickViewButton() {
        return clickViewButton(1);
    }

    public P02_EmployeesDataPage clickViewButton(int rowIndex) {
        WebElement btn = driver.findElement(By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]"));
        scrollToElement(btn);
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(btn)).click();
        return this;
    }

    /** Click Edit (first row by default) */
    public P02_EmployeesDataPage clickEditButton() {
        return clickEditButton(1);
    }

    public P02_EmployeesDataPage clickEditButton(int rowIndex) {
        WebElement btn = driver.findElement(By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]"));
        scrollToElement(btn);
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(btn)).click();
        return this;
    }

    /** Click Delete (first row by default) */
    public P02_EmployeesDataPage clickDeleteButton() {
        return clickDeleteButton(1);
    }

    public P02_EmployeesDataPage clickDeleteButton(int rowIndex) {
        WebElement btn = driver.findElement(By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]"));
        scrollToElement(btn);
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(btn)).click();
        return this;
    }

    /** Click Confirm Delete */
    public P02_EmployeesDataPage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(3000);
        return this;
    }

    /** Wait for delete success */
    public P02_EmployeesDataPage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("employees-data"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_MESSAGE)
        ));
        return this;
    }

    /** Type in search */
    public P02_EmployeesDataPage typeSearch(String text) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        input.clear();
        input.sendKeys(text);
        return this;
    }

    /** Verify table has data */
    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> dataCells = driver.findElements(DATA_CELLS);
            for (WebElement cell : dataCells) {
                if (!cell.getText().trim().isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}