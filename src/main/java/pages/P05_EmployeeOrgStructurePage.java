package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class P05_EmployeeOrgStructurePage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'الوحدة')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");

    /** أول عمود = الوحدة / اسم الهيكل (من tr.p-selectable-row) */
    private static final String ROW_FIRST_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][1]";
    private static final String ROW_CELL_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//td[contains(@class,'ng-star-inserted')][%d]";
    /** كود الهيكل داخل الصف: div.text-gray-500.text-xs.font-medium (مثل HR-61037) */
    private static final String ROW_CODE_DIV_XPATH = "(//tr[contains(@class,'p-selectable-row')])[%d]//div[contains(@class,'text-gray-500') and contains(@class,'text-xs') and contains(@class,'font-medium')]";
    private static final String VIEW_BUTTON_XPATH = "(//button[@type='button']//img[@alt='عرض']/ancestor::button)";
    private static final String EDIT_BUTTON_XPATH = "(//button[@type='button']//img[@alt='تعديل']/ancestor::button)";
    private static final String DELETE_BUTTON_XPATH = "(//button[@type='button']//img[@alt='حذف']/ancestor::button)";
    private static final By CONFIRM_DELETE_BUTTON = By.cssSelector("button.swal2-confirm");

    /** في صفحة العرض: قيمة "كود الهيكل" داخل الكارد */
    private static final By VIEW_PAGE_STRUCTURE_CODE = By.xpath("//lib-card-simple-text[.//p[text()='كود الهيكل']]//p[contains(@class,'value')]");

    /** زر إضافة (صفحة الهيكل التنظيمي) */
    private static final By ADD_BUTTON = By.xpath("//button[.//img[@alt='إضافة']]");
    /** زر الـ icon (الدائرة +) يُضغط قبل فتح أسهم الشجرة */
    private static final By TREE_ADD_ICON_BUTTON = By.xpath("//button[.//img[@alt='icon' and contains(@src,'button-add')]]");
    /** شجرة اختيار القسم: عقدة مطوية (نضغط السهم لتوسيعها) */
    private static final By TREE_COLLAPSED_TOGGLER = By.xpath("//li[@role='treeitem' and @aria-expanded='false']//button[contains(@class,'p-tree-toggler')]");
    /** عقدة ورقة (أعمق مستوى، لا أبناء لها) */
    private static final By TREE_LEAF_NODE = By.xpath("//li[contains(@class,'p-treenode-leaf')]//div[contains(@class,'p-treenode-content') and contains(@class,'p-treenode-selectable')]");
    /** أي قسم في الشجرة (عقدة قابلة للاختيار) للضغط عليه بعد فتح الأسهم */
    private static final By TREE_SELECTABLE_NODE = By.xpath("//li[@role='treeitem']//div[contains(@class,'p-treenode-content') and contains(@class,'p-treenode-selectable')]");
    /** أول قسم في الشجرة وهو مقفول (مثل شركة) — نضغط عليه عشان نضيف تحته */
    private static final By TREE_FIRST_SECTION_CLOSED = By.xpath("(//li[@role='treeitem'])[1]//div[contains(@class,'p-treenode-content') and contains(@class,'p-treenode-selectable')]");

    private static final By STRUCTURE_NAME_INPUT = By.cssSelector("input[data-testid='name']");
    /** المدير المسؤول — dropdown */
    private static final By RESPONSIBLE_EMPLOYEE_DROPDOWN = By.cssSelector("[data-testid='ResponsibleEmployeeId']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='button']//img[@alt='حفظ']/ancestor::button");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-content')]//div[contains(@class,'p-toast-summary') and contains(text(),'تم بنجاح')]");
    /** رسالة النجاح بعد التعديل فقط: "تم تحديث بيانات القسم بنجاح" (summary) و "تم بنجاح" (detail) */
    private static final By SUCCESS_TOAST_AFTER_EDIT = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");
    /** رسالة النجاح بعد الإضافة: "تمت إضافة القسم بنجاح" */
    private static final By SUCCESS_TOAST_AFTER_ADD = By.xpath("//div[contains(@class,'p-toast-message-success')]//*[contains(@class,'p-toast-summary') or contains(@class,'p-toast-detail')][contains(.,'بنجاح')]");

    public P05_EmployeeOrgStructurePage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Employee Org Structure page */
    public P05_EmployeeOrgStructurePage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/employee-org-structure");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** قيمة أول عمود (الوحدة) من الصف (rowIndex 1-based). */
    public String getUnitNameFromListRow(int rowIndex) {
        By cell = By.xpath(String.format(ROW_FIRST_CELL_XPATH, rowIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    public P05_EmployeeOrgStructurePage clickViewButton(int rowIndex) {
        By btnBy = By.xpath(VIEW_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** كود الهيكل المعروض في صفحة العرض. */
    public String getStructureCodeFromViewPage() {
        WebElement el = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(VIEW_PAGE_STRUCTURE_CODE));
        return el.getText().trim();
    }

    /** كود الهيكل من الصف (من الـ div الذي يعرض الكود مثل HR-61037، rowIndex 1-based). */
    public String getStructureCodeFromListRow(int rowIndex) {
        By codeDiv = By.xpath(String.format(ROW_CODE_DIV_XPATH, rowIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(codeDiv)).getText().trim();
    }

    /** ضغط زر إضافة ثم انتظار ظهور شجرة اختيار القسم. */
    public P05_EmployeeOrgStructurePage clickAddButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(ADD_BUTTON));
        scrollToElement(btn);
        btn.click();
        By treePresent = By.xpath("//li[@role='treeitem']");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(treePresent));
        return this;
    }

    /** ضغط زر الـ icon (الدائرة +) قبل اختيار القسم. */
    public P05_EmployeeOrgStructurePage clickTreeAddIconButton() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(TREE_ADD_ICON_BUTTON));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    /** الضغط على أول قسم في الشجرة وهو مقفول (مثل شركة) عشان نضيف تحته — بدون فتح الأسهم. */
    public P05_EmployeeOrgStructurePage clickFirstSectionWhileCollapsed() {
        WebElement firstSection = longWait(driver).until(ExpectedConditions.elementToBeClickable(TREE_FIRST_SECTION_CLOSED));
        scrollToElement(firstSection);
        firstSection.click();
        return this;
    }

    /**
     * توسيع كل العقد المطوية في الشجرة (نضغط السهم حتى لا يبقى أي عقدة مطوية)،
     * ثم الضغط على قسم منها لاستخدامه (آخر عقدة ظاهرة = أعمق مستوى).
     */
    public P05_EmployeeOrgStructurePage expandTreeToDeepestThenSelect() {
        final int maxExpands = 50;
        for (int i = 0; i < maxExpands; i++) {
            List<WebElement> collapsed = driver.findElements(TREE_COLLAPSED_TOGGLER);
            if (collapsed.isEmpty()) break;
            WebElement first = longWait(driver).until(ExpectedConditions.elementToBeClickable(TREE_COLLAPSED_TOGGLER));
            scrollToElement(first);
            first.click();
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        List<WebElement> nodes = driver.findElements(TREE_SELECTABLE_NODE);
        if (!nodes.isEmpty()) {
            WebElement toSelect = nodes.get(nodes.size() - 1);
            scrollToElement(toSelect);
            toSelect.click();
        }
        return this;
    }

    /** ضغط زر عودة للرجوع للجدول. */
    public P05_EmployeeOrgStructurePage clickBackButton() {
//        By back = By.xpath("//button[contains(.,'عودة')] | //a[contains(.,'عودة')] | //*[contains(text(),'عودة')]/ancestor::button[1]");
//        WebElement btn = shortWait(driver).until(ExpectedConditions.elementToBeClickable(back));
//        scrollToElement(btn);
//        btn.click();
        driver.navigate().back();
        return this;
    }

    public P05_EmployeeOrgStructurePage clickEditButton(int rowIndex) {
        By btnBy = By.xpath(EDIT_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P05_EmployeeOrgStructurePage setStructureName(String name) {
        WebElement input = longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(STRUCTURE_NAME_INPUT));
        input.clear();
        input.sendKeys(name);
        return this;
    }

    public P05_EmployeeOrgStructurePage clickSave() {
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

    /** انتظار رسالة النجاح بعد التعديل فقط (toast: تم تحديث بيانات القسم بنجاح / تم بنجاح). */
    public boolean waitForSuccessToastAfterEdit() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST_AFTER_EDIT));
            return driver.findElement(SUCCESS_TOAST_AFTER_EDIT).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** انتظار رسالة النجاح بعد الإضافة (تمت إضافة القسم بنجاح). */
    public boolean waitForSuccessToastAfterAdd() {
        try {
            longWait(driver).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST_AFTER_ADD));
            return driver.findElement(SUCCESS_TOAST_AFTER_ADD).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** فتح dropdown المدير المسؤول واختيار أول عنصر في القائمة. */
    public P05_EmployeeOrgStructurePage selectResponsibleEmployeeFirst() {
        openDropdownAndSelectFirst(RESPONSIBLE_EMPLOYEE_DROPDOWN);
        return this;
    }

    private void openDropdownAndSelectFirst(By dropdownLocator) {
        WebDriverWait wait = shortWait(driver);
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("ul[role='listbox'] li:first-child")
        )).click();
    }

    /** اسم الوحدة من العمود الأول في الصف (بعد التعديل نتحقق من الجدول). */
    public String getUnitNameFromListRowColumn(int rowIndex, int colIndex) {
        By cell = By.xpath(String.format(ROW_CELL_XPATH, rowIndex, colIndex));
        return longWait(driver).until(ExpectedConditions.presenceOfElementLocated(cell)).getText().trim();
    }

    public P05_EmployeeOrgStructurePage clickDeleteButton(int rowIndex) {
        By btnBy = By.xpath(DELETE_BUTTON_XPATH + "[" + rowIndex + "]");
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(btnBy));
        scrollToElement(btn);
        btn.click();
        return this;
    }

    public P05_EmployeeOrgStructurePage clickConfirmDeleteButton() throws InterruptedException {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(CONFIRM_DELETE_BUTTON)).click();
        Thread.sleep(2000);
        return this;
    }

    public P05_EmployeeOrgStructurePage waitForDeleteSuccess() {
        longWait(driver).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("employee-org-structure"),
                ExpectedConditions.presenceOfElementLocated(SUCCESS_TOAST)
        ));
        return this;
    }

    /** هل النص (اسم الوحدة) موجود في الجدول. */
    public boolean isUnitNameInTable(String name) {
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
                if (!text.isEmpty()) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
