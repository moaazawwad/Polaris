package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class P03_JobPositionPage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'كود الوظيفة')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");

    /** أول عمود في الصف = كود الوظيفة (من tr.p-selectable-row) */
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";
    /** الصف والعمود: [2] = اسم الوظيفة */
    private static final String ROW_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][%d]";
    /** زر عرض في الصف */
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    /** زر تعديل في الصف */
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    /** زر حذف في الصف */
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    /** زر إضافة (فتح نموذج إضافة وظيفة) */
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    /** تأكيد الحذف (نفس نمط الموظفين) */
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");
    /** في صفحة العرض: قيمة كود الوظيفة داخل الكارد */
    private static final By VIEW_PAGE_JOB_CODE = By.xpath("//lib-card-simple-text[.//p[text()='كود الوظيفة']]//p[contains(@class,'value')]");

    /** في نموذج التعديل: انبوت اسم الوظيفة */
    private static final By JOB_NAME_INPUT = By.cssSelector("input[data-testid='name']");
    /** زر حفظ في نموذج التعديل */
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");
    /** رسالة النجاح: تم بنجاح */
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-content')]//div[contains(@class,'p-toast-summary') and contains(text(),'تم بنجاح')]");







    public P03_JobPositionPage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Job Position page */
    public P03_JobPositionPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/job-position");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    /** ضغط زر إضافة لفتح نموذج إضافة وظيفة. */
    public P03_JobPositionPage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** كود الوظيفة من أول عمود في الصف (rowIndex 1-based). */
    public String getJobCodeFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_FIRST_CELL_XPATH, rowIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** ضغط زر عرض للصف (rowIndex 1-based). */
    public P03_JobPositionPage clickViewButton(int rowIndex) {
        By btnBy = By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** كود الوظيفة المعروض في صفحة العرض (بعد الضغط على عرض). */
    public String getJobCodeFromViewPage() {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(VIEW_PAGE_JOB_CODE));
        return el.getText().trim();
    }

    /** ضغط زر تعديل للصف (rowIndex 1-based). */
    public P03_JobPositionPage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** في نموذج التعديل: تعبئة اسم الوظيفة. */
    public P03_JobPositionPage setJobName(String name) {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(JOB_NAME_INPUT));
        input.clear();
        input.sendKeys(name);
        return this;
    }

    /** في نموذج التعديل: ضغط حفظ. */
    public P03_JobPositionPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** انتظار ظهور رسالة النجاح (تم بنجاح). ترجع true إذا ظهرت. */
    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return driver.findElement(SUCCESS_TOAST).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** اسم الوظيفة من العمود الثاني في الصف (rowIndex 1-based). */
    public String getJobNameFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_CELL_XPATH, rowIndex, 2));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    /** ضغط زر حذف للصف (rowIndex 1-based). */
    public P03_JobPositionPage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** ضغط تأكيد الحذف في الـ dialog. */
    public P03_JobPositionPage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }

    /** انتظار نجاح الحذف (رسالة أو بقاء الصفحة). */
    public P03_JobPositionPage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("job-position"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_TOAST)
        ));
        return this;
    }

    /** هل كود الوظيفة موجود في الجدول (بعد الحذف نتأكد أنه اختفى). */
    public boolean isJobCodeInTable(String jobCode) {
        try {
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                if (cell.getText().trim().contains(jobCode)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /** هل اسم الوظيفة موجود في الجدول (بعد الإضافة نتأكد أنه ظهر). */
    public boolean isJobNameInTable(String jobName) {
        try {
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                if (cell.getText().trim().contains(jobName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
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
}
