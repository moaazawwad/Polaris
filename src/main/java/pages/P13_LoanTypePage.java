package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * صفحة أنواع السلف (Loan Type) — CRUD: جدول، إضافة، عرض، تعديل، حذف (popup + toaster).
 * الحقول: اسم السلفة (عربي)، اسم السلفة (إنجليزي)، البند المرتبط بالراتب (dropdown)، الوصف.
 */
public class P13_LoanTypePage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//*[contains(text(),'السلفة') or contains(text(),'قائمة') or contains(text(),'الكود')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");

    /** نموذج الإضافة/التعديل (popup) */
    private static final By NAME_AR_INPUT = By.cssSelector("input[data-testid='nameAr']");
    private static final By NAME_EN_INPUT = By.cssSelector("input[data-testid='nameEn']");
    private static final By SALARY_ITEM_DROPDOWN = By.cssSelector("[data-testid='salaryItemId']");
    private static final By DESCRIPTION_INPUT = By.cssSelector("input[data-testid='description']");

    /** إغلاق نافذة العرض (View popup) */
    private static final By DIALOG_CLOSE = By.cssSelector("[aria-label='Close'], .p-dialog-header-close, button.p-dialog-header-close");

    public P13_LoanTypePage(WebDriver driver) {
        super(driver);
    }

    public P13_LoanTypePage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/payroll-wages/master-data/loan-type");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** التحقق: الصفحة تحمل والجدول يظهر ويحتوي بيانات. */
    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (!text.isEmpty()) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public P13_LoanTypePage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_AR_INPUT));
        return this;
    }

    public P13_LoanTypePage clickViewButton(int rowIndex) {
        By btnBy = By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** انتظار ظهور نافذة العرض — التحقق من وجود نص «عرض» (span.current). */
    public boolean waitForViewDialog() {
        try {
            By viewTitle = By.xpath("//span[contains(@class,'current') and contains(.,'عرض')]");
            longWait(driver).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(viewTitle),
                    ExpectedConditions.presenceOfElementLocated(DIALOG_CLOSE)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** إغلاق الـ popup (عرض أو نموذج) إن وُجد. */
    public P13_LoanTypePage closeDialogIfPresent() {
        try {
            List<WebElement> closeBtns = driver.findElements(DIALOG_CLOSE);
            if (!closeBtns.isEmpty() && closeBtns.get(0).isDisplayed()) {
                closeBtns.get(0).click();
            }
        } catch (Exception ignored) { }
        return this;
    }

    public P13_LoanTypePage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_AR_INPUT));
        return this;
    }

    public P13_LoanTypePage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P13_LoanTypePage setNameAr(String nameAr) {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_AR_INPUT));
        input.clear();
        input.sendKeys(nameAr);
        return this;
    }

    public P13_LoanTypePage setNameEn(String nameEn) {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_EN_INPUT));
        input.clear();
        input.sendKeys(nameEn);
        return this;
    }

    /** اختيار أول بند مرتبط بالراتب من الـ dropdown. */
    public P13_LoanTypePage selectSalaryItemFirst() {
        openDropdownAndSelectFirst(SALARY_ITEM_DROPDOWN);
        return this;
    }

    public P13_LoanTypePage setDescription(String description) {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION_INPUT));
        el.clear();
        el.sendKeys(description);
        return this;
    }

    private void openDropdownAndSelectFirst(By dropdownLocator) {
        WebDriverWait wait = shortWait(driver);
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("ul[role='listbox'] li:first-child")
        )).click();
    }

    public P13_LoanTypePage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P13_LoanTypePage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }

    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return driver.findElement(SUCCESS_TOAST).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public P13_LoanTypePage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("loan-type"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_TOAST)
        ));
        return this;
    }

    /** قيمة أول عمود في الصف (rowIndex 1-based). */
    public String getNameFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_FIRST_CELL_XPATH, rowIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    public boolean isNameInTable(String name) {
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
}
