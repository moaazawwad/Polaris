package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class P06_EmployeeContractPage extends PageBase {

    /**
     * aria-label عمود «موقع العمل» في جدول العقد — فيه مسافة في النهاية كما رجعها الـ backend (خطأ من الديفولبر).
     * استخدم هذا الثابت بدل النص اليدوي عشان ما تتشالش المسافة بالغلط.
     */
    public static final String COLUMN_LABEL_WORK_LOCATION = "موقع العمل ";

    private static final String BASE_URL = "https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/employee-contract";
    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'كود العقد') or contains(text(),'العقد')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");

    // Action Buttons
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    private static final By FILTER_BUTTON = By.xpath("//button[@type='button']//img[contains(@src,'button-filter.svg')]/ancestor::button");
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";

    // Delete Confirmation
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");

    // Success / Toaster
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");
    private static final By SUCCESS_MESSAGE = By.xpath("//*[contains(text(),'تم الحذف') or contains(text(),'تم') or contains(text(),'نجح')]");

    // Search
    private static final By SEARCH_INPUT = By.xpath("//input[@placeholder='بحث' and contains(@class,'searchInputBorderBottom')]");

    // ——— Add / Edit Contract form (صفحة إضافة عقد) ———
    private static final String ADD_CONTRACT_URL = "https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/employee-contract/add";
    private static final By SAVE_BUTTON = By.xpath("//button[.//span[contains(text(),'حفظ')] or .//img[@alt='حفظ']]/ancestor::button | //button[@type='button']//img[@alt='حفظ']/ancestor::button");
    private static final By CANCEL_BUTTON = By.xpath("//button[.//span[contains(text(),'إلغاء')] or .//img[@alt='إلغاء']]/ancestor::button | //button[@type='button']//img[@alt='إلغاء']/ancestor::button");
    private static final By ADD_FORM_HEADER = By.xpath("//*[contains(text(),'بيانات العقد') or contains(text(),'إضافة') or contains(text(),'كود العقد')]");
    private static final By EMPLOYEE_DROPDOWN = By.cssSelector("p-dropdown[data-testid='employeeId']");
    private static final By RENEWAL_TYPE_DROPDOWN = By.cssSelector("p-dropdown[data-testid='renewalType']");
    private static final By CONTRACT_TYPE_DROPDOWN = By.cssSelector("p-dropdown[data-testid='contractType']");
    private static final By CONTRACT_DURATION_DROPDOWN = By.cssSelector("p-dropdown[data-testid='contractDurationId']");
    private static final By HAS_TRIAL_PERIOD_SWITCH = By.cssSelector("p-inputswitch[data-testid='hasTrialPeriod']");
    private static final By VACATION_DAYS_INPUT = By.cssSelector("input[data-testid='vacationDays']");
    private static final By VACATION_ACCRUAL_MONTHS_INPUT = By.cssSelector("input[data-testid='vacationAccrualMonths']");
    private static final By TICKETS_COUNT_INPUT = By.cssSelector("input[data-testid='ticketsCount']");
    private static final By TICKETS_ACCRUAL_MONTHS_INPUT = By.cssSelector("input[data-testid='ticketsAccrualMonths']");
    private static final By NOTICE_PERIOD_DAYS_INPUT = By.cssSelector("input[data-testid='noticePeriodDays']");

    // ——— تاب المعلومات الوظيفية / معلومات البدلات والرواتب ———
    private static final By TAB_JOB_INFO = By.xpath("//*[contains(text(),'المعلومات الوظيفية')]");
    private static final By TAB_ALLOWANCES = By.xpath("//*[contains(text(),'معلومات البدلات والرواتب')]");
    private static final By ASSIGN_SALARY_ITEMS_BUTTON = By.xpath("//button[.//span[contains(text(),'تعين')] or .//img[@alt='تعيين بنود الراتب']] | //p-button//button[contains(.,'تعين')]");

    // ——— Popup تعيين بنود الراتب ———
    private static final By POPUP_TITLE = By.xpath("//*[contains(text(),' تعيين بنود الراتب ')]");
    private static final By POPUP_SAVE_BUTTON = By.xpath("//div[contains(@class,'p-dialog')]//button[.//span[contains(text(),'حفظ')] or .//img[@alt='حفظ']] | //p-dialog//button[.//span[contains(text(),'حفظ')]]");

    public P06_EmployeeContractPage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Employee Contract list page */
    public P06_EmployeeContractPage navigateToPage() {
        driver.get(BASE_URL);
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public P06_EmployeeContractPage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P06_EmployeeContractPage clickFilterButton() {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(FILTER_BUTTON)).click();
        return this;
    }

    public P06_EmployeeContractPage clickViewButton() {
        return clickViewButton(1);
    }

    public P06_EmployeeContractPage clickViewButton(int rowIndex) {
        WebElement btn = driver.findElement(By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]"));
        scrollToElement(btn);
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(btn)).click();
        return this;
    }

    public P06_EmployeeContractPage clickEditButton() {
        return clickEditButton(1);
    }

    public P06_EmployeeContractPage clickEditButton(int rowIndex) {
        WebElement btn = driver.findElement(By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]"));
        scrollToElement(btn);
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(btn)).click();
        return this;
    }

    public P06_EmployeeContractPage clickDeleteButton() {
        return clickDeleteButton(1);
    }

    public P06_EmployeeContractPage clickDeleteButton(int rowIndex) {
        WebElement btn = driver.findElement(By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]"));
        scrollToElement(btn);
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(btn)).click();
        return this;
    }

    /** Click Confirm in delete confirmation dialog */
    public P06_EmployeeContractPage clickConfirmDeleteButton() {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        return this;
    }

    /** Wait until success toaster or message appears (e.g. after delete/save) */
    public boolean waitForSuccessToast() {
        try {
            longWait(driver).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST),
                    ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public P06_EmployeeContractPage typeSearch(String text) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        input.clear();
        input.sendKeys(text);
        return this;
    }

    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (!text.isEmpty() && (text.contains("HR-") || text.length() > 2)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // ——— Add Contract form ———
    /** الانتقال لصفحة إضافة عقد جديد */
    public P06_EmployeeContractPage navigateToAddPage() {
        driver.get(ADD_CONTRACT_URL);
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(ADD_FORM_HEADER),
                ExpectedConditions.presenceOfElementLocated(EMPLOYEE_DROPDOWN)
        ));
        return this;
    }

    public P06_EmployeeContractPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        btn.click();
        return this;
    }

    public P06_EmployeeContractPage clickCancel() {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CANCEL_BUTTON)).click();
        return this;
    }

    /** التحقق من ظهور نموذج إضافة العقد */
    public boolean isAddContractFormDisplayed() {
        try {
            return driver.findElement(EMPLOYEE_DROPDOWN).isDisplayed()
                    && driver.findElement(RENEWAL_TYPE_DROPDOWN).isDisplayed()
                    && driver.findElement(CONTRACT_TYPE_DROPDOWN).isDisplayed()
                    && driver.findElement(CONTRACT_DURATION_DROPDOWN).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public P06_EmployeeContractPage selectEmployeeByText(String optionText) {
        openDropdownAndSelectOptionByText("employeeId", optionText);
        return this;
    }

    public P06_EmployeeContractPage selectEmployeeByIndex(int index) {
        openDropdownAndSelectOptionByIndex("employeeId", index);
        return this;
    }

    public P06_EmployeeContractPage selectRenewalTypeByText(String optionText) {
        openDropdownAndSelectOptionByText("renewalType", optionText);
        return this;
    }

    public P06_EmployeeContractPage selectRenewalTypeByIndex(int index) {
        openDropdownAndSelectOptionByIndex("renewalType", index);
        return this;
    }

    public P06_EmployeeContractPage selectContractTypeByText(String optionText) {
        openDropdownAndSelectOptionByText("contractType", optionText);
        return this;
    }

    public P06_EmployeeContractPage selectContractTypeByIndex(int index) {
        openDropdownAndSelectOptionByIndex("contractType", index);
        return this;
    }

    public P06_EmployeeContractPage selectContractDurationByText(String optionText) {
        openDropdownAndSelectOptionByText("contractDurationId", optionText);
        return this;
    }

    public P06_EmployeeContractPage selectContractDurationByIndex(int index) {
        openDropdownAndSelectOptionByIndex("contractDurationId", index);
        return this;
    }

    /** نفس أسلوب باقي الصفحات: فتح الـ datepicker بالضغط على الـ input ثم اختيار السنة/الشهر/اليوم من الـ panel. */
    private WebElement getVisibleDatepickerPanel() {
        return driver.findElements(By.cssSelector(".p-datepicker")).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No visible datepicker"));
    }

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

    /** تاريخ العقد — أول تقويم (0). */
    public P06_EmployeeContractPage setContractDate(int year, int month, int day) {
        selectDateByCalendarIndex(0, year, month, day);
        return this;
    }

    public P06_EmployeeContractPage setContractDateToday() {
        LocalDate today = LocalDate.now();
        return setContractDate(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }

    /** تاريخ بداية العقد — ثاني تقويم (1). */
    public P06_EmployeeContractPage setContractStartDate(int year, int month, int day) {
        selectDateByCalendarIndex(1, year, month, day);
        return this;
    }

    public P06_EmployeeContractPage setContractStartDateToday() {
        LocalDate today = LocalDate.now();
        return setContractStartDate(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }

    /** تاريخ انتهاء العقد — ثالث تقويم (2). */
    public P06_EmployeeContractPage setContractEndDate(int year, int month, int day) {
        selectDateByCalendarIndex(2, year, month, day);
        return this;
    }

    public P06_EmployeeContractPage setContractEndDateToday() {
        LocalDate today = LocalDate.now();
        return setContractEndDate(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }

    public P06_EmployeeContractPage toggleTrialPeriod() {
        WebElement sw = shortWait(driver).until(ExpectedConditions.elementToBeClickable(HAS_TRIAL_PERIOD_SWITCH));
        sw.click();
        return this;
    }

    public P06_EmployeeContractPage setVacationDays(String value) {
        setFormInput(VACATION_DAYS_INPUT, value);
        return this;
    }

    public P06_EmployeeContractPage setVacationAccrualMonths(String value) {
        setFormInput(VACATION_ACCRUAL_MONTHS_INPUT, value);
        return this;
    }

    public P06_EmployeeContractPage setTicketsCount(String value) {
        setFormInput(TICKETS_COUNT_INPUT, value);
        return this;
    }

    public P06_EmployeeContractPage setTicketsAccrualMonths(String value) {
        setFormInput(TICKETS_ACCRUAL_MONTHS_INPUT, value);
        return this;
    }

    public P06_EmployeeContractPage setNoticePeriodDays(String value) {
        setFormInput(NOTICE_PERIOD_DAYS_INPUT, value);
        return this;
    }

    private void setFormInput(By by, String value) {
        WebElement el = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(by));
        el.clear();
        el.sendKeys(value != null ? value : "");
    }

    // ——— التابات والجدول (المعلومات الوظيفية / البدلات والرواتب) ———
    /** الضغط على تاب «المعلومات الوظيفية». */
    public P06_EmployeeContractPage clickJobInfoTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(TAB_JOB_INFO));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        tab.click();
        return this;
    }

    /** الضغط على تاب «معلومات البدلات والرواتب». */
    public P06_EmployeeContractPage clickAllowancesTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(TAB_ALLOWANCES));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        tab.click();
        return this;
    }

    /**
     * تعبئة حقل في جدول العقد (بعد اختيار التاب): دوس على القلم ثم إما dropdown (أول خيار) أو text.
     * @param columnLabel اسم العمود (aria-label) مثل: اسم الوظيفة، قسم، {@link #COLUMN_LABEL_WORK_LOCATION}، نموذج الراتب، طريقة الدفع
     * @param value للنوع text فقط؛ للنوع dropdown يُهمل (يُختار أول خيار)
     * @param fieldType "dropdown" أو "text"
     */
    public P06_EmployeeContractPage fillContractTableField(String columnLabel, String value, String fieldType) {
        if ("dropdown".equalsIgnoreCase(fieldType)) {
            WebElement cell = getVisibleCellByLabel(columnLabel);
            By dropdownInCell = By.xpath(".//div[contains(@class,'p-dropdown')] | .//p-dropdown");
            if (cell.findElements(dropdownInCell).isEmpty()) {
                clickPencilByColumnName(columnLabel);
                try { Thread.sleep(450); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            cell = getVisibleCellByLabel(columnLabel);
            List<WebElement> triggers = cell.findElements(By.cssSelector(".p-dropdown-trigger"));
            if (triggers.isEmpty()) {
                triggers = cell.findElements(By.cssSelector("span[role='combobox']"));
            }
            if (triggers.isEmpty()) {
                triggers = cell.findElements(By.xpath(".//*[contains(@class,'p-dropdown')]"));
            }
            if (!triggers.isEmpty()) {
                WebElement toClick = shortWait(driver).until(ExpectedConditions.elementToBeClickable(triggers.get(0)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", toClick);
            }
            try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            List<WebElement> options = driver.findElements(By.cssSelector("ul[role='listbox'] li, li.p-dropdown-item, p-dropdownitem li"));
            WebElement firstOption = options.stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .orElse(null);
            if (firstOption != null) {
                shortWait(driver).until(ExpectedConditions.elementToBeClickable(firstOption));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
            }
        } else if ("text".equalsIgnoreCase(fieldType)) {
            clickPencilByColumnName(columnLabel);
            typeInContractTableInput(columnLabel, value);
        }
        return this;
    }

    /** أول خلية ظاهرة لها aria-label يساوي columnLabel (لتجنب خلايا التابات المخفية). */
    private WebElement getVisibleCellByLabel(String columnLabel) {
        List<WebElement> cells = driver.findElements(By.xpath("//td[@aria-label='" + columnLabel + "']"));
        return cells.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No visible cell for column: " + columnLabel));
    }

    /** دوس على أيقونة القلم لفتح الخلية للتعديل حسب اسم العمود (الخلية الظاهرة فقط). */
    public void clickPencilByColumnName(String columnLabel) {
        WebElement cell = getVisibleCellByLabel(columnLabel);
        List<WebElement> pencils = cell.findElements(By.xpath(".//span[contains(@class,'fa-pencil')] | .//span[contains(@class,'icon_test')]"));
        if (pencils.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cell);
        } else {
            WebElement pencil = shortWait(driver).until(ExpectedConditions.elementToBeClickable(pencils.get(0)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", pencil);
        }
        try { Thread.sleep(350); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        shortWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@aria-label='" + columnLabel + "' and contains(@class,'p-cell-editing')]"))
        );
    }

    private void typeInContractTableInput(String columnLabel, String text) {
        By inputInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//input");
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(inputInCell));
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        new Actions(driver)
                .click(input)
                .pause(Duration.ofMillis(100))
                .sendKeys(text != null ? text : "")
                .sendKeys(Keys.TAB)
                .perform();
    }

    /** الضغط على زر «تعيين» لبنود الراتب لفتح الـ popup. */
    public P06_EmployeeContractPage clickAssignSalaryItemsButton() {
        WebElement btn = shortWait(driver).until(ExpectedConditions.elementToBeClickable(ASSIGN_SALARY_ITEMS_BUTTON));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", btn);
        shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(POPUP_TITLE));
        return this;
    }

    /** نافذة «تعيين بنود الراتب» الظاهرة — كل البحث يكون جواها عشان ميختلطش مع جداول الصفحة. */
    private WebElement getVisibleSalaryItemsDialog() {
        return shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'p-dialog')][.//*[contains(.,'تعيين بنود الراتب')]]")));
    }

    /**
     * داخل popup تعيين بنود الراتب: أول صف فقط — بند الراتب (dropdown أول خيار) → خلية قيمة بند الراتب → input داخل نفس الـ td → حفظ.
     * <p>الـ input مش بيظهر في الـ DOM إلا بعد ما تدوس على الـ td؛ عشان كده بنستخدم {@code valueTd.findElement(input)} مش XPath على كل الصفحة.</p>
     */
    public P06_EmployeeContractPage fillSalaryItemsPopupAndSave(String salaryItemValue) {
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement dialog = getVisibleSalaryItemsDialog();
        WebDriverWait wait = shortWait(driver);

        WebElement bandTd = dialog.findElement(By.xpath(
                ".//tr[td[@aria-label='بند الراتب']][1]//td[@aria-label='بند الراتب']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bandTd);
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        WebElement dataRow = dialog.findElement(By.xpath(".//tr[td[@aria-label='بند الراتب']][1]"));
        List<WebElement> dropdowns = dataRow.findElements(By.xpath(".//p-dropdown | .//*[contains(@class,'p-dropdown')]"));
        if (!dropdowns.isEmpty()) {
            WebElement dd = dropdowns.get(0);
            List<WebElement> triggers = dd.findElements(By.cssSelector(".p-dropdown-trigger"));
            if (triggers.isEmpty()) {
                triggers = dd.findElements(By.cssSelector("[role='combobox']"));
            }
            if (!triggers.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", triggers.get(0));
            }
            try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            WebElement firstOpt = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("ul[role='listbox'] li, li.p-dropdown-item")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOpt);
        }

        try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement valueTd = dialog.findElement(By.xpath(
                ".//tr[td[@aria-label='قيمة بند الراتب']][1]//td[@aria-label='قيمة بند الراتب']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", valueTd);
        wait.until(ExpectedConditions.attributeContains(valueTd, "class", "p-cell-editing"));
        WebElement valueInput = wait.until(d -> {
            try {
                WebElement in = valueTd.findElement(By.cssSelector("input"));
                return in.isDisplayed() ? in : null;
            } catch (Exception e) {
                return null;
            }
        });
        valueInput.clear();
        valueInput.sendKeys(salaryItemValue != null ? salaryItemValue : "0");

        WebElement saveInPopup = wait.until(ExpectedConditions.elementToBeClickable(POPUP_SAVE_BUTTON));
        saveInPopup.click();
        try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }
}
