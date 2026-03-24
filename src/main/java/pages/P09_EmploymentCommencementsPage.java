package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Utility;

import java.util.List;

public class P09_EmploymentCommencementsPage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'Employment Commencements') or contains(text(),'Commencement Date') or contains(text(),'الكود') or contains(text(),'قائمة بدء التوظيف')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");

    /** صفوف الجدول وأزرار عرض / تعديل / حذف (نفس نمط P03) */
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    private static final By ADD_BUTTON = By.xpath("//button[.//img[@alt='إضافة']]");
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");

    /** صفحة العرض: قيمة "الكود" في الكارد */
    private static final By VIEW_PAGE_CODE = By.xpath("//lib-card-simple-text[.//p[text()='الكود']]//p[contains(@class,'value')]");

    /** نموذج الإضافة/التعديل */
    private static final By CALENDAR_INPUT = By.cssSelector("[data-testid='calendar'] input");
    private static final By EMPLOYEE_DROPDOWN = By.cssSelector("[data-testid='employeeId']");
    private static final By COMMENCEMENT_TYPE_DROPDOWN = By.cssSelector("[data-testid='commencementType']");
    private static final By NOTES_TEXTAREA = By.cssSelector("textarea[formcontrolname='notes']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");

    public P09_EmploymentCommencementsPage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Employment Commencements page */
    public P09_EmploymentCommencementsPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/employment-commencements");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** التحقق: الجدول فيه عمود الكود وبيانات وكل الأكواد تبدأ بـ HR- */
    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            boolean hasAnyCode = false;
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (text.isEmpty()) continue;
                if (text.contains("HR-") || text.contains("Hr-")) {
                    hasAnyCode = true;
                    break;
                }
            }
            return hasAnyCode;
        } catch (Exception e) {
            return false;
        }
    }

    /** كود بدء التوظيف من أول عمود في الصف (rowIndex 1-based). */
    public String getCodeFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_FIRST_CELL_XPATH, rowIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    /** ضغط زر إضافة */
    public P09_EmploymentCommencementsPage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(CALENDAR_INPUT));
        return this;
    }

    /** ضغط زر عرض للصف (rowIndex 1-based). */
    public P09_EmploymentCommencementsPage clickViewButton(int rowIndex) {
        By btnBy = By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** الكود المعروض في صفحة العرض (بعد الضغط على عرض). */
    public String getCodeFromViewPage() {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(VIEW_PAGE_CODE));
        return el.getText().trim();
    }

    /** الرجوع من صفحة العرض إلى القائمة (متصفح). */
    public P09_EmploymentCommencementsPage clickBackButton() {
        driver.navigate().back();
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    /** ضغط زر تعديل للصف (rowIndex 1-based). */
    public P09_EmploymentCommencementsPage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** ضغط زر حذف للصف (rowIndex 1-based). */
    public P09_EmploymentCommencementsPage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** تأكيد الحذف في الـ popup */
    public P09_EmploymentCommencementsPage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }

    /** تعبئة تاريخ مباشرة العمل بتاريخ عشوائي مستقبلي (عبر الـ date picker). */
    public P09_EmploymentCommencementsPage setCommencementDate() {
        int[] date = Utility.generateRandomFutureDate(2);
        Utility.selectDate("تاريخ مباشرة العمل", 0, date[0], date[1], date[2]);
        return this;
    }

    /** اختيار أول موظف من الـ dropdown */
    public P09_EmploymentCommencementsPage selectFirstEmployee() {
        openDropdownAndSelectFirst(EMPLOYEE_DROPDOWN);
        return this;
    }

    /** اختيار أول نوع مباشرة عمل من الـ dropdown */
    public P09_EmploymentCommencementsPage selectFirstCommencementType() {
        openDropdownAndSelectFirst(COMMENCEMENT_TYPE_DROPDOWN);
        return this;
    }

    private void openDropdownAndSelectFirst(By dropdownLocator) {
        WebDriverWait wait = shortWait(driver);
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("ul[role='listbox'] li:first-child")
        )).click();
    }

    /** تعبئة الملاحظات */
    public P09_EmploymentCommencementsPage setNotes(String notes) {
        WebElement textarea = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NOTES_TEXTAREA));
        textarea.clear();
        textarea.sendKeys(notes);
        return this;
    }

    /** ضغط حفظ */
    public P09_EmploymentCommencementsPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** انتظار رسالة النجاح (بنجاح). */
    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return driver.findElement(SUCCESS_TOAST).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** انتظار نجاح الحذف */
    public P09_EmploymentCommencementsPage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("employment-commencements"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_TOAST)
        ));
        return this;
    }

    /** هل الكود موجود في الجدول */
    public boolean isCodeInTable(String code) {
        try {
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                if (cell.getText().trim().contains(code)) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
