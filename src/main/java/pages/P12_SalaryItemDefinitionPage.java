package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class P12_SalaryItemDefinitionPage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//*[contains(text(),'قائمة بنود الرواتب') or contains(text(),'الكود')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    /** زر حذف في الصف */
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    /** زر إضافة (فتح نموذج إضافة وظيفة) */
    private static final By ADD_BUTTON = By.xpath("//button[@type='button']//img[@alt='إضافة']/ancestor::button");
    /** تأكيد الحذف (نفس نمط الموظفين) */
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");
    private static final By NEW_LINE_BUTTON =
            By.xpath("//button[.//span[normalize-space()='accounting_Journal_addNew']]");    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");
    private static final By NAME_AR_INPUT = By.cssSelector("input[data-testid='nameAr']");
    private static final By NAME_EN_INPUT = By.cssSelector("input[data-testid='nameEn']");
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";


    public P12_SalaryItemDefinitionPage(WebDriver driver) {
        super(driver);
    }

    public P12_SalaryItemDefinitionPage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }

    /** انتظار نجاح الحذف (العودة للقائمة أو ظهور toast). */
    public P12_SalaryItemDefinitionPage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("salary-item-definition"),
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


    public P12_SalaryItemDefinitionPage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@aria-label='اسم البند باللغة العربية']")
        ));
        return this;
    }

    /** ضغط زر حذف للصف (rowIndex 1-based). */
    public P12_SalaryItemDefinitionPage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P12_SalaryItemDefinitionPage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);

        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(NEW_LINE_BUTTON));
        return this;
    }
    /** أول خطوة في صفحة الإضافة: ضغط زر Add row — عبر lib-button-micro[aria-label='Add row'] ثم الـ button بداخله. */
    public P12_SalaryItemDefinitionPage clickAddRowButton() {
        By addRowWrapper = By.xpath("//*[@aria-label='Add row']//button");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(addRowWrapper));
        try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(addRowWrapper));
        scrollToElement(btn);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center',inline:'center'}); arguments[0].click();", btn);
        try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }









    public P12_SalaryItemDefinitionPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/payroll-wages/master-data/salary-item-definition");
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
    public P12_SalaryItemDefinitionPage addNewLine() {
        WebElement tab = longWait(driver).until(ExpectedConditions.elementToBeClickable(NEW_LINE_BUTTON));
        scrollToElement(tab);

        try {
            tab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }

        By firstEditableCell = By.xpath("//td[@aria-label='اسم البند باللغة العربية']");
        longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(firstEditableCell));
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
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    public P12_SalaryItemDefinitionPage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        scrollToElement(btn);
        btn.click();
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

    /** نفس فكرة Add Employee: عمود (columnLabel)، اختياري testId، النص أو القيمة، نوع الحقل text أو dropdown. */
    public P12_SalaryItemDefinitionPage fillAcademicField(String columnLabel, String testId, String textToType, String fieldType) throws InterruptedException {
        switch (fieldType.toLowerCase()) {
            case "text":
                if (testId != null && !testId.isEmpty()) {
                    By inputLocator = By.cssSelector("input[data-testid='" + testId + "']");
                    if (driver.findElements(inputLocator).isEmpty()) {
                        clickPencilByColumnName(columnLabel);
                    }
                    typeInTableInput(testId, textToType);
                } else {
                    By inputInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//input");
                    List<WebElement> inputs = driver.findElements(inputInCell);
                    if (inputs.isEmpty() || !inputs.get(0).isDisplayed()) {
                        clickPencilByColumnName(columnLabel);
                    }
                    typeInCellByColumnLabel(columnLabel, textToType);
                }
                break;

            case "dropdown":
                By dropdownInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//div[contains(@class, 'p-dropdown')]");
                if (driver.findElements(dropdownInCell).isEmpty()) {
                    clickPencilByColumnName(columnLabel);
                }
                shortWait(driver).until(ExpectedConditions.presenceOfElementLocated(dropdownInCell));
                try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                By dropdownTrigger = By.xpath("//td[@aria-label='" + columnLabel + "']//div[contains(@class,'p-dropdown-trigger')] | //td[@aria-label='" + columnLabel + "']//span[@role='combobox'] | //td[@aria-label='" + columnLabel + "']//div[contains(@class,'p-dropdown')]");
                WebElement toClick = shortWait(driver).until(ExpectedConditions.elementToBeClickable(dropdownTrigger));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", toClick);
                WebElement firstOption = shortWait(driver).until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector("ul[role='listbox'] li, li.p-dropdown-item, p-dropdownitem li"))
                );
                firstOption.click();
                break;
        }
        return this;
    }

    /** الكتابة في الـ input داخل الخلية (عندما لا يوجد data-testid). */
    private void typeInCellByColumnLabel(String columnLabel, String textToType) throws InterruptedException {
        By inputInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//input");
        WebElement inputElement = longWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(inputInCell)
        );
        try { Thread.sleep(350); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        new Actions(driver)
                .click(inputElement)
                .pause(Duration.ofMillis(100))
                .sendKeys(textToType)
                .sendKeys(Keys.TAB)
                .perform();
    }    /**
     * ميثود لفتح الخلية للتعديل بناءً على اسم العمود
     * @param columnLabel اسم العمود (مثل: المستوى التعليمي، اسم المؤهل)
     */
    public void clickPencilByColumnName(String columnLabel) {
        String xpathExpression = String.format(
                "//td[@aria-label='%s']//span[contains(@class, 'fa-pencil')]",
                columnLabel
        );

        WebElement pencilIcon = shortWait(driver).until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression))
        );

        // جرب JavaScript click
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pencilIcon);

        // انتظر الـ cell تدخل edit mode
        String cellEditingXpath = String.format(
                "//td[@aria-label='%s' and contains(@class, 'p-cell-editing')]",
                columnLabel
        );

        try {
            shortWait(driver).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(cellEditingXpath))
            );
            System.out.println("Cell entered edit mode successfully");
        } catch (Exception e) {
            System.out.println("Cell did NOT enter edit mode!");
            // طبع الـ class الحالي للـ cell
            WebElement cell = driver.findElement(By.xpath("//td[@aria-label='" + columnLabel + "']"));
            System.out.println("Current cell class: " + cell.getAttribute("class"));
        }
    }

    public void typeInTableInput(String testId, String textToType) {
        By inputLocator = By.cssSelector("input[data-testid='" + testId + "']");

        // انتظر لحد ما الـ input يبقى visible و enabled
        WebElement inputElement = longWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(inputLocator)
        );

        // انتظر شوية بعد الظهور عشان الـ Angular change detection
        try { Thread.sleep(350); } catch (InterruptedException e) {}

        // استخدم Actions بدل sendKeys العادية
        new Actions(driver)
                .click(inputElement)
                .pause(Duration.ofMillis(100))
                .sendKeys(textToType)
                .sendKeys(Keys.TAB)
                .perform();
    }

    /**
     * تعبئة خلية نصية في جدول «إضافة بند جديد»: ضغط القلم ثم الكتابة.
     * (بدون تاب — الاعتماد على aria-label فقط)
     */
    public P12_SalaryItemDefinitionPage fillSalaryItemCellText(String columnLabel, String text) throws InterruptedException {
        clickPencilByColumnName(columnLabel);
        By inputInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//input");
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(inputInCell));
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        input.clear();
        input.sendKeys(text);
        new Actions(driver).sendKeys(Keys.TAB).perform();
        return this;
    }

    /**
     * تعبئة خلية dropdown في جدول «إضافة بند جديد»: ضغط القلم ثم اختيار أول عنصر.
     * (نوع بند الراتب، وحدة القياس الافتراضية)
     */
    public P12_SalaryItemDefinitionPage fillSalaryItemCellDropdown(String columnLabel) throws InterruptedException {
        By dropdownInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//div[contains(@class, 'p-dropdown')]");
        if (driver.findElements(dropdownInCell).isEmpty()) {
            clickPencilByColumnName(columnLabel);
        }
        WebElement dropdownDiv = shortWait(driver).until(ExpectedConditions.elementToBeClickable(dropdownInCell));
        dropdownDiv.click();
        WebElement firstOption = shortWait(driver).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("ul[role='listbox'] li:first-child"))
        );
        firstOption.click();
        return this;
    }

}
