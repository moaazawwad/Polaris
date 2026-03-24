package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class P11_SalaryTemplatePage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//*[contains(text(),'قائمة نماذج الرواتب') or contains(text(),'رقم النموذج')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    /** زر تعديل في الصف */
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    /** زر حذف في الصف */
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    /** زر إضافة (فتح نموذج إضافة وظيفة) */
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    /** تأكيد الحذف (نفس نمط الموظفين) */
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");

    /** نموذج الإضافة/التعديل: اسم النموذج عربي / إنجليزي / دورية الدفع / وصف */
    private static final By NAME_AR_INPUT = By.cssSelector("input[data-testid='nameAr']");
    private static final By NAME_EN_INPUT = By.cssSelector("input[data-testid='nameEn']");
    private static final By PAYMENT_FREQUENCY_DROPDOWN = By.cssSelector("[data-testid='paymentFrequency']");
    private static final By DESCRIPTION_INPUT = By.cssSelector("textarea[placeholder='الوصف']");

    public P11_SalaryTemplatePage(WebDriver driver) {
        super(driver);
    }

    public P11_SalaryTemplatePage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/payroll-wages/master-data/salary-template");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** ضغط زر عرض للصف (rowIndex 1-based). */
    public P11_SalaryTemplatePage clickViewButton(int rowIndex) {
        By btnBy = By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }


    /** ضغط زر تعديل للصف (rowIndex 1-based) ثم انتظار ظهور النموذج. */
    public P11_SalaryTemplatePage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_AR_INPUT));
        return this;
    }

    /** ضغط زر حذف للصف (rowIndex 1-based). */
    public P11_SalaryTemplatePage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** ضغط زر إضافة لفتح نموذج إضافة نموذج راتب. */
    public P11_SalaryTemplatePage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_AR_INPUT));
        return this;
    }

    /** تعبئة اسم النموذج باللغة العربية. */
    public P11_SalaryTemplatePage setNameAr(String nameAr) {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_AR_INPUT));
        input.clear();
        input.sendKeys(nameAr);
        return this;
    }

    /** تعبئة اسم النموذج باللغة الإنجليزية. */
    public P11_SalaryTemplatePage setNameEn(String nameEn) {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NAME_EN_INPUT));
        input.clear();
        input.sendKeys(nameEn);
        return this;
    }

    /** اختيار أول قيمة من دورية الدفع (paymentFrequency). */
    public P11_SalaryTemplatePage selectPaymentFrequencyFirst() {
        openDropdownAndSelectFirst(PAYMENT_FREQUENCY_DROPDOWN);
        return this;
    }

    /** تعبئة الوصف (Add و Edit). */
    public P11_SalaryTemplatePage setDescription(String description) {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION_INPUT));
        el.clear();
        el.sendKeys(description);
        return this;
    }

    /** ضغط تأكيد الحذف في الـ popup. */
    public P11_SalaryTemplatePage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }

    /** انتظار نجاح الحذف (العودة للقائمة أو ظهور toast). */
    public P11_SalaryTemplatePage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("salary-template"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_TOAST)
        ));
        return this;
    }

    /** قيمة أول عمود في الصف (اسم أو رقم النموذج، rowIndex 1-based). */
    public String getTemplateNameFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_FIRST_CELL_XPATH, rowIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    /** هل النص موجود في الجدول (للتحقق بعد الحذف أنه اختفى). */
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
    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return driver.findElement(SUCCESS_TOAST).isDisplayed();
        } catch (Exception e) {
            return false;
        }
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

    public P11_SalaryTemplatePage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }
}
