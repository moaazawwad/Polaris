package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class P04_WorkLocationPage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'رمز منطقة العمل')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    /** زر تعديل في الصف */
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    /** زر حذف في الصف */
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    /** زر إضافة (فتح نموذج إضافة وظيفة) */
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    /** تأكيد الحذف (نفس نمط الموظفين) */
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");
    private static final By WORK_LOCATION_NAME_INPUT = By.cssSelector("input[data-testid='workAreaName']");
    private final By RISPONSIBLE_EMPLOYEE_DROPDOWN = By.cssSelector("[data-testid='responsibleEmployeeId']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");
    private static final String ROW_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][%d]";
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-content')]//div[contains(@class,'p-toast-summary') and contains(text(),'تم بنجاح')]");
    private static final By SUCCESS_MESSAGE = By.xpath("//*[contains(text(),'تم الحذف') or contains(text(),'تم') or contains(text(),'نجح')]");

    public P04_WorkLocationPage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Work Location page */
    public P04_WorkLocationPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/work-location");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (!text.isEmpty() && text.contains("HR-")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** ضغط زر عرض للصف (rowIndex 1-based). سكرول أولاً لأن الزر قد يكون خارج الـ viewport. */
    public P04_WorkLocationPage clickViewButton(int rowIndex) {
        By btnBy = By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.presenceOfElementLocated(btnBy));
        scrollToElement(btn);
        btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        btn.click();
        return this;
    }

    /** ضغط زر تعديل للصف (rowIndex 1-based). سكرول أولاً لأن الزر قد يكون خارج الـ viewport. */
    public P04_WorkLocationPage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.presenceOfElementLocated(btnBy));
        scrollToElement(btn);
        btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        btn.click();
        return this;
    }

    /** ضغط زر حذف للصف (rowIndex 1-based). سكرول أولاً لأن الزر قد يكون خارج الـ viewport. */
    public P04_WorkLocationPage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.presenceOfElementLocated(btnBy));
        scrollToElement(btn);
        btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        btn.click();
        return this;
    }

    /** ضغط زر إضافة لفتح نموذج إضافة وظيفة. */
    public P04_WorkLocationPage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }
    /** ضغط تأكيد الحذف في الـ dialog. */
    public P04_WorkLocationPage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }
    public P04_WorkLocationPage setWorkLocationName(String name) throws InterruptedException {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(WORK_LOCATION_NAME_INPUT));
        input.clear();
        Thread.sleep(1000);
        input.clear();
        input.sendKeys(name);
        return this;
    }
    private void openDropdownAndSelectFirst(By dropdownLocator) {
        WebDriverWait wait = shortWait(driver);

        // 1. افتح الـ dropdown
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();

        // 2. انتظر وأختار أول option
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("ul[role='listbox'] li:first-child")
        )).click();
    }
    /** اسم الوظيفة من العمود الثاني في الصف (rowIndex 1-based). */
    public String getWorkLocationNameFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_CELL_XPATH, rowIndex, 2));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    public P04_WorkLocationPage setResponsibleEmployeeToFirst() {
        openDropdownAndSelectFirst(RISPONSIBLE_EMPLOYEE_DROPDOWN);
        return this;
    }

    /** Wait for success toast to appear after save. Returns true if displayed. */
    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return driver.findElement(SUCCESS_TOAST).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isWorkLocationNameInTable(String name) {
        if (name == null || name.isEmpty()) return false;
        try {
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                if (cell.getText().trim().contains(name)) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    /** Wait for delete success */
    public P04_WorkLocationPage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("employees-data"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_MESSAGE)
        ));
        return this;
    }
    public P04_WorkLocationPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }
}
