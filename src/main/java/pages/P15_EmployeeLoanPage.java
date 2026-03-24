package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * صفحة سلف الموظفين (Employee Loan) — CRUD + إضافة بطريقتين: مباشر (حقول قيمة السلفة/القسط/التاريخ)، من طلب (رقم الطلب فقط بعد الملاحظات).
 */
public class P15_EmployeeLoanPage extends PageBase {

    private static final String BASE_URL = "https://hrmicro.microtec-test.com/hr/payroll-wages/transactions/employee-loan";
    private static final By PAGE_HEADER = By.xpath("//*[contains(text(),'سلف') or contains(text(),'الموظف') or contains(text(),'الكود') or contains(text(),'المعاملات')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");

    private static final By LOAN_SOURCE_DROPDOWN = By.cssSelector("[data-testid='loanSource']");
    private static final By LOAN_REQUEST_ID_DROPDOWN = By.cssSelector("[data-testid='loanRequestId']");
    private static final By LOAN_TYPE_DROPDOWN = By.cssSelector("[data-testid='loanTypeId']");
    private static final By EMPLOYEE_ID_DROPDOWN = By.cssSelector("[data-testid='employeeId']");
    private static final By NOTES_TEXTAREA = By.cssSelector("textarea[placeholder='الملاحظات']");
    private static final By LOAN_AMOUNT_INPUT = By.cssSelector("input[data-testid='loanAmount']");
    private static final By INSTALLMENT_AMOUNT_INPUT = By.cssSelector("input[data-testid='installmentAmount']");
    private static final By DIALOG_CLOSE = By.cssSelector("[aria-label='Close'], .p-dialog-header-close, button.p-dialog-header-close");

    public P15_EmployeeLoanPage(WebDriver driver) {
        super(driver);
    }

    public P15_EmployeeLoanPage navigateToPage() {
        driver.get(BASE_URL);
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                if (!cell.getText().trim().isEmpty()) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public P15_EmployeeLoanPage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(LOAN_SOURCE_DROPDOWN));
        return this;
    }

    public P15_EmployeeLoanPage clickViewButton(int rowIndex) {
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

    public P15_EmployeeLoanPage closeDialogIfPresent() {
        try {
            List<WebElement> closeBtns = driver.findElements(DIALOG_CLOSE);
            if (!closeBtns.isEmpty() && closeBtns.get(0).isDisplayed()) closeBtns.get(0).click();
        } catch (Exception ignored) { }
        return this;
    }

    public P15_EmployeeLoanPage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(LOAN_SOURCE_DROPDOWN));
        return this;
    }

    public P15_EmployeeLoanPage clickDeleteButton(int rowIndex) throws InterruptedException {
        Thread.sleep(2000);

        By btnBy = By.xpath("(//button[@type='button']//img[@alt='حذف']/ancestor::button)[1]");
        System.out.println(btnBy);
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        Thread.sleep(2000);
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** فتح dropdown بالضغط على الـ trigger ثم اختيار أول خيار — الـ overlay يظهر في body. */
    private void openDropdownAndSelectFirstByTrigger(String dataTestId) {
        WebDriverWait wait = shortWait(driver);
        By dropdownLocator = By.cssSelector("p-dropdown[data-testid='" + dataTestId + "']");
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        WebElement trigger = dropdown.findElement(By.cssSelector("[role='combobox'], .p-dropdown-trigger"));
        wait.until(ExpectedConditions.elementToBeClickable(trigger)).click();
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[role='listbox'], .p-dropdown-panel, [role='listbox']")));
        By firstOption = By.cssSelector("ul[role='listbox'] li:first-child, ul[role='listbox'] li, li.p-dropdown-item");
        WebElement option = longWait(driver).until(ExpectedConditions.elementToBeClickable(firstOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
    }

    /** اختيار «مباشر» من مصدر السلفة (أول خيار). */
    public P15_EmployeeLoanPage selectLoanSourceDirect() {
        openDropdownAndSelectFirstByTrigger("loanSource");
        return this;
    }

    /** اختيار «من طلب» من مصدر السلفة (خيار يحتوي النص). */
    public P15_EmployeeLoanPage selectLoanSourceFromRequest() {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(By.cssSelector("p-dropdown[data-testid='loanSource']")));
        WebElement dropdown = driver.findElement(By.cssSelector("p-dropdown[data-testid='loanSource']"));
        WebElement trigger = dropdown.findElement(By.cssSelector("[role='combobox'], .p-dropdown-trigger"));
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(trigger)).click();
        By optionFromRequest = By.xpath("//ul[@role='listbox']//li[contains(.,'من طلب') or contains(.,'طلب')]");
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(optionFromRequest)).click();
        return this;
    }

    public P15_EmployeeLoanPage selectLoanTypeFirst() {
        openDropdownAndSelectFirstByTrigger("loanTypeId");
        return this;
    }

    public P15_EmployeeLoanPage selectEmployeeFirst() {
        openDropdownAndSelectFirstByTrigger("employeeId");
        return this;
    }

    private WebElement getVisibleDatepickerPanel() {
        return driver.findElements(By.cssSelector(".p-datepicker")).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No visible datepicker"));
    }

    /** فتح التقويم بالضغط على الـ input حسب ترتيبه (0 = أول تقويم، 1 = ثاني تقويم) ثم اختيار التاريخ داخل الـ panel الظاهر. */
    private void selectDateByCalendarIndex(int calendarIndex, int year, int month, int day) {
        WebDriverWait wait = shortWait(driver);
        By calendarInputs = By.cssSelector("p-calendar input[role='combobox']");
        List<WebElement> inputs = driver.findElements(calendarInputs);
        if (inputs.size() <= calendarIndex) return;
        wait.until(ExpectedConditions.elementToBeClickable(inputs.get(calendarIndex))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-datepicker")));
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement panel = getVisibleDatepickerPanel();
        try {
            panel.findElement(By.cssSelector(".p-datepicker-year")).click();
        } catch (Exception e) {
            return;
        }
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-yearpicker")));
        int maxIterations = 30;
        while (maxIterations-- > 0) {
            panel = getVisibleDatepickerPanel();
            List<WebElement> years = panel.findElements(By.cssSelector(".p-yearpicker-year"));
            Optional<WebElement> target = years.stream()
                    .filter(y -> y.getText().trim().equals(String.valueOf(year)))
                    .findFirst();
            if (target.isPresent()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", target.get());
                break;
            }
            int minYear = years.isEmpty() ? year : years.stream()
                    .mapToInt(y -> Integer.parseInt(y.getText().trim()))
                    .min().orElse(year);
            if (year < minYear) {
                panel.findElement(By.cssSelector(".p-datepicker-prev")).click();
            } else {
                panel.findElement(By.cssSelector(".p-datepicker-next")).click();
            }
            try { Thread.sleep(350); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-monthpicker")));
        panel = getVisibleDatepickerPanel();
        List<WebElement> months = panel.findElements(By.cssSelector(".p-monthpicker-month"));
        if (month >= 1 && month <= months.size()) {
            months.get(month - 1).click();
        }
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        By daySelector = By.cssSelector(String.format("span[data-date='%d-%d-%d']", year, month, day));
        panel = getVisibleDatepickerPanel();
        WebElement dayEl = panel.findElement(daySelector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dayEl);
    }

    /** تاريخ السلفة — أول تقويم في الصفحة (الـ input ممكن يكون placeholder فاضي). */
    public P15_EmployeeLoanPage setLoanDateToday() {
        LocalDate today = LocalDate.now();
        selectDateByCalendarIndex(0, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        return this;
    }

    public P15_EmployeeLoanPage setNotes(String notes) {
        WebElement notesEl = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NOTES_TEXTAREA));
        notesEl.clear();
        notesEl.sendKeys(notes);
        return this;
    }

    public P15_EmployeeLoanPage setLoanAmount(int value) {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(LOAN_AMOUNT_INPUT));
        el.clear();
        el.sendKeys(String.valueOf(value));
        return this;
    }

    public P15_EmployeeLoanPage setInstallmentAmount(int value) {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(INSTALLMENT_AMOUNT_INPUT));
        el.clear();
        el.sendKeys(String.valueOf(value));
        return this;
    }

    /** تاريخ أول استحقاق — ثاني تقويم في الصفحة. */
    public P15_EmployeeLoanPage setFirstInstallmentDueDateToday() {
        LocalDate today = LocalDate.now();
        selectDateByCalendarIndex(1, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        return this;
    }

    /** اختيار أول رقم طلب (يظهر بعد اختيار «من طلب»). */
    public P15_EmployeeLoanPage selectLoanRequestFirst() {
        openDropdownAndSelectFirstByTrigger("loanRequestId");
        return this;
    }

    public P15_EmployeeLoanPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P15_EmployeeLoanPage clickConfirmDeleteButton() throws InterruptedException {
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
