package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.Utility;

import java.time.Duration;
public class P07_AddEmployeePage extends PageBase {

    public static final String DEFAULT_EMPLOYEE_IMAGE_PATH = "D:\\polaris\\automation project\\pics\\download.jpg";

    private final By saveButton = By.xpath("//button[.//span[contains(text(),'حفظ')]]");
    private final By cancelButton = By.xpath("//button[.//span[contains(text(),'إلغاء')]]");
    private final By tabPersonalInfo = By.xpath("//*[contains(text(),'المعلومات الشخصية')]");
    private final By tabContactInfo = By.xpath("//*[contains(text(),'معلومات جهة الاتصال')]");
    private final By tabAcademicInfo = By.xpath("//*[contains(text(),'المعلومات الأكاديمية')]");
    private final By tabOfficialInfo = By.xpath("//*[contains(text(),'المعلومات الرسمية')]");
    private final By tabJobInfo = By.xpath("//*[contains(text(),'المعلومات الوظيفية')]");
    private final By tabDependents = By.xpath("//*[contains(text(),'بيانات المرافقين')]");
    private final By tabInsurance = By.xpath("//*[contains(text(),'التأمينات الاجتماعية')]");

    private final By imageUploadInput = By.cssSelector("input[type='file']");
    private final By employeeNameAr = By.cssSelector("[data-testid='nameAr']");
    private final By employeeNameEn = By.cssSelector("[data-testid='nameEn']");
    private final By attendanceCode = By.cssSelector("[data-testid='attendanceCode']");

    private final By birthDate = By.xpath("//input[@id='icondisplay' and @placeholder='تاريخ الميلاد']");
    private final By birthCity = By.cssSelector("[data-testid='birthCity']");
    private final By specialNeedsCheckbox = By.xpath("//*[contains(text(),'ذوى الاجتياجات الخاصة') or contains(text(),'ذوى الاحتياجات')]/following::input[@type='checkbox'][1]");

    private final By genderDropdown = By.cssSelector("[data-testid='gender']");
    private final By nationalityDropdown = By.cssSelector("[data-testid='nationalityCode']");
    private final By birthCountryDropdown = By.cssSelector("[data-testid='birthCountryCode']");
    private final By maritalStatusDropdown = By.cssSelector("[data-testid='maritalStatus']");
    private final By religionDropdown = By.cssSelector("[data-testid='religion']");
    private final By bloodGroupDropdown = By.cssSelector("[data-testid='bloodGroup']");

    private final By countryDropdown = By.cssSelector("[data-testid='countryCode']");
    private final By city = By.cssSelector("[data-testid='city']");
    private final By region = By.cssSelector("[data-testid='region']");
    private final By address = By.cssSelector("[data-testid='address']");
    private final By buildingNumber = By.cssSelector("[data-testid='buildingNumber']");
    private final By mailBox = By.cssSelector("[data-testid='mailBox']");
    private final By postalCode = By.cssSelector("[data-testid='postalCode']");
    private final By email = By.cssSelector("[data-testid='email']");
    private final By mobileCountryCode1 = By.cssSelector("(//input[@data-testid='mobile']/preceding::span[@role='combobox'])[8]");
    private final By mobileCountryCode2 = By.cssSelector("p-dropdown[data-testid='phoneCountryCode']");
    private final By mobile = By.cssSelector("[data-testid='mobile']");
    private final By secMobile = By.cssSelector("[data-testid='phone']");
    private final By landLineCountryCode = By.cssSelector("[data-testid='landLineCountryCode']");
    private final By landLine = By.cssSelector("[data-testid='landLine']");
    private final By officePhoneCountryCode = By.cssSelector("[data-testid='officePhoneNumberCountryCode']");
    private final By officePhone = By.cssSelector("[data-testid='officePhoneNumber']");
    private final By extension = By.cssSelector("[data-testid='extension']");

    private final By nationalIdNumber = By.cssSelector("[data-testid='nationalIdNumber']");
    private final By nationalIdIssuingAuthority = By.cssSelector("[data-testid='nationalIdIssuingAuthority']");
    private final By passportNumber = By.cssSelector("[data-testid='passportNumber']");
    private final By passportIssuingAuthority = By.cssSelector("[data-testid='passportIssuingAuthority']");
    private final By insuranceSalaryInput = By.cssSelector("input[placeholder='الراتب التأميني']");
    private final By toggleSwitch = By.cssSelector("div[data-pc-name='inputswitch']");

    private final By departmentDropdown = By.cssSelector("[data-testid='departmentId']");
    private final By directManagerDropdown = By.cssSelector("[data-testid='reportToId']");
    private final By employmentTypeDropdown = By.cssSelector("[data-testid='employmentTypeId']");
    private final By jobLevelDropdown = By.cssSelector("[data-testid='jobLevelId']");

        private final By academicAddButton = By.xpath("//app-educational-information-table//button[.//img[contains(@alt,'إضافة') or contains(@alt,'Add')]]");
    private final By academicPencilIcon = By.xpath("(//app-educational-information-table//span[contains(@class,'fa-pencil') or contains(@class,'icon_test')])[1]");
    private final By academicInputField = By.xpath("(//app-educational-information-table//p-celleditor//input | //app-educational-information-table//input[@type='text'])[1]");

    private final By nationalityDropdown_Dependents = By.xpath("(//p-dropdown[@data-testid='nationalityCode'])[2]");


    private final By dependentsAddButton = By.xpath("//app-dependent-information-table-add//button[.//img[contains(@alt,'إضافة') or contains(@alt,'Add')]]");
    private final By dependentsPencilIcon = By.xpath("(//app-dependent-information-table-add//span[contains(@class,'fa-pencil') or contains(@class,'icon_test')])[1]");
    private final By dependentsInputField = By.xpath("(//app-dependent-information-table-add//p-celleditor//input | //app-dependent-information-table-add//input[@type='text'])[1]");

    private final By listboxFirstOption = By.xpath("//ul[@role='listbox']//li[1]");
    private final By inouttt = By.xpath("//input[contains(@data-testid, 'educationLevel')]");
    private static final String BIRTH_DATE = "تاريخ الميلاد";
    private static final String ISSUE_DATE = "تاريخ الاصدار";
    private static final String EXPIRY_DATE = "تاريخ الانتهاء";
    private static final String SUBSCRIPTION_START_DATE = "تاريخ بدء الاشتراك";


    public P07_AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    /** Success toast: "تم بنجاح" (إضافة / تعديل) */
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'p-toast-message-content')]//div[contains(@class,'p-toast-summary') and contains(text(),'تم بنجاح')]");

    public P07_AddEmployeePage clickSave() {
        WebElement btn = longWait(driver).until(ExpectedConditions.elementToBeClickable(saveButton));
        btn.click();
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

    public P07_AddEmployeePage clickCancel() {
        WebElement btn = shortWait(driver).until(ExpectedConditions.elementToBeClickable(cancelButton));
        btn.click();
        return this;
    }

    public P07_AddEmployeePage clickPersonalInfoTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabPersonalInfo));
        tab.click();
        shortWait(driver).until(ExpectedConditions.presenceOfElementLocated(birthDate));
        return this;
    }

    public P07_AddEmployeePage clickContactInfoTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabContactInfo));
        tab.click();
        return this;
    }

    public P07_AddEmployeePage clickAcademicInfoTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabAcademicInfo));
        tab.click();
        return this;
    }

    public P07_AddEmployeePage clickOfficialInfoTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabOfficialInfo));
        tab.click();
        return this;
    }

    public P07_AddEmployeePage clickJobInfoTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabJobInfo));
        tab.click();
        return this;
    }

    public P07_AddEmployeePage clickDependentsTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabDependents));
        tab.click();
        return this;
    }

    public P07_AddEmployeePage clickInsuranceTab() {
        WebElement tab = shortWait(driver).until(ExpectedConditions.elementToBeClickable(tabInsurance));
        tab.click();
        return this;
    }


  public P07_AddEmployeePage setSecMobile(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(secMobile));
        input.clear();
        input.sendKeys(value);
        return this;
    }

    public P07_AddEmployeePage inoutttt(String value) {
        WebElement input = longWait(driver).until(ExpectedConditions.presenceOfElementLocated(inouttt));

        // 1. إجبار المتصفح على التركيز
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", input);

        // 2. إرسال مفتاح وهمي (مثل Ctrl) ثم الكتابة
        Actions action = new Actions(driver);
        action.sendKeys(Keys.CONTROL).perform(); // ضغطة سريعة للتنبيه

        input.clear();
        input.sendKeys(value);
        input.sendKeys(Keys.ENTER);

        return this;
    }
    public P07_AddEmployeePage setEmployeeNameEn(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(employeeNameEn));
        input.clear();
        input.sendKeys(value);
        return this;
    }
    public P07_AddEmployeePage setEmployeeNameAr(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(employeeNameAr));
        input.clear();
        input.sendKeys(value);
        return this;
    }
    public P07_AddEmployeePage setAttendanceCode(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(attendanceCode));
        input.clear();
        input.sendKeys(value);
        return this;
    }

    public P07_AddEmployeePage setImageUpload(String path) {
        WebElement input = shortWait(driver).until(ExpectedConditions.presenceOfElementLocated(imageUploadInput));
        input.sendKeys(path);
        return this;
    }

    public P07_AddEmployeePage setImageUpload() {
        return setImageUpload(DEFAULT_EMPLOYEE_IMAGE_PATH);
    }

    public P07_AddEmployeePage setBirthDate(String dateString) {
        String normalized = dateString.replace("/", "-");
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(birthDate));
        ((JavascriptExecutor) driver).executeScript(
            "var el = arguments[0]; el.value = arguments[1]; el.dispatchEvent(new Event('input', { bubbles: true })); el.dispatchEvent(new Event('change', { bubbles: true })); el.dispatchEvent(new Event('blur', { bubbles: true }));",
            input, normalized);
        return this;
    }

    public P07_AddEmployeePage setBirthDate(String day, String month, String year) {
        return setBirthDate(day + "-" + month + "-" + year);
    }

    public P07_AddEmployeePage setBirthCity(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(birthCity));
        input.clear();
        input.sendKeys(value);
        return this;
    }

    public P07_AddEmployeePage setSpecialNeeds(boolean checked) {
        WebElement el = shortWait(driver).until(ExpectedConditions.presenceOfElementLocated(specialNeedsCheckbox));
        if (el.isSelected() != checked) el.click();
        return this;
    }

    public P07_AddEmployeePage setGenderToFirst() {
        openDropdownAndSelectFirst(genderDropdown);
        return this;
    }

    public P07_AddEmployeePage setNationalityToFirst() {
        openDropdownAndSelectFirst(nationalityDropdown);
        return this;
    }

    public P07_AddEmployeePage setBirthCountryToFirst() {
        openDropdownAndSelectFirst(birthCountryDropdown);
        return this;
    }

    public P07_AddEmployeePage setMaritalStatusToFirst() {
        openDropdownAndSelectFirst(maritalStatusDropdown);
        return this;
    }

    public P07_AddEmployeePage setReligionToFirst() {
        openDropdownAndSelectFirst(religionDropdown);
        return this;
    }

    public P07_AddEmployeePage setBloodGroupToFirst() {
        openDropdownAndSelectFirst(bloodGroupDropdown);
        return this;
    }

    public P07_AddEmployeePage setCountryToFirst() {
        openDropdownAndSelectFirst(countryDropdown);
        return this;
    }

    public P07_AddEmployeePage setCity(String value) {
        typeInto(city, value);
        return this;
    }

    public P07_AddEmployeePage setRegion(String value) {
        typeInto(region, value);
        return this;
    }

    public P07_AddEmployeePage setAddress(String value) {
        typeInto(address, value);
        return this;
    }

    public P07_AddEmployeePage setBuildingNumber(String value) {
        typeInto(buildingNumber, value);
        return this;
    }

    public P07_AddEmployeePage setMailBox(String value) {
        typeInto(mailBox, value);
        return this;
    }

    public P07_AddEmployeePage setPostalCode(String value) {
        typeInto(postalCode, value);
        return this;
    }

    public P07_AddEmployeePage setEmail(String value) {
        typeInto(email, value);
        return this;
    }

    public P07_AddEmployeePage setMobileCountryCodesToFirst() throws InterruptedException {
        selectCountryByText("mobileCountryCode", "مصر");
        selectCountryByText("phoneCountryCode", "مصر");
//        openDropdownAndSelectFirst(mobileCountryCode1);
//        openDropdownAndSelectFirst(mobileCountryCode2);
        return this;
    }

    public P07_AddEmployeePage setMobile(String value) {
        typeInto(mobile, value);
        return this;
    }

    public P07_AddEmployeePage setLandLineCountryCodeToFirst() {
        openDropdownAndSelectFirst(landLineCountryCode);
        return this;
    }

    public P07_AddEmployeePage setLandLine(String value) {
        typeInto(landLine, value);
        return this;
    }

    public P07_AddEmployeePage setOfficePhoneCountryCodeToFirst() {
        openDropdownAndSelectFirst(officePhoneCountryCode);
        return this;
    }

    public P07_AddEmployeePage setOfficePhone(String value) {
        typeInto(officePhone, value);
        return this;
    }

    public P07_AddEmployeePage setExtension(String value) {
        typeInto(extension, value);
        return this;
    }

    public P07_AddEmployeePage setNationalIdNumber(String value) {
        typeInto(nationalIdNumber, value);
        return this;
    }

    public P07_AddEmployeePage setNationalIdIssuingAuthority(String value) {
        typeInto(nationalIdIssuingAuthority, value);
        return this;
    }

    public P07_AddEmployeePage setPassportNumber(String value) {
        typeInto(passportNumber, value);
        return this;
    }

    public P07_AddEmployeePage setPassportIssuingAuthority(String value) {
        typeInto(passportIssuingAuthority, value);
        return this;
    }

    public P07_AddEmployeePage clickAcademicAddButton() {
        WebElement btn = shortWait(driver).until(ExpectedConditions.elementToBeClickable(academicAddButton));
        btn.click();
        shortWait(driver).until(ExpectedConditions.presenceOfElementLocated(academicPencilIcon));
        return this;
    }

    public P07_AddEmployeePage clickAcademicPencilIcon() {
        WebElement icon = shortWait(driver).until(ExpectedConditions.elementToBeClickable(academicPencilIcon));
        icon.click();
        return this;
    }

    public P07_AddEmployeePage typeAcademicField(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(academicInputField));
        input.clear();
        input.sendKeys(value);
        return this;
    }

    public P07_AddEmployeePage clickDependentsAddButton() {
        WebElement btn = shortWait(driver).until(ExpectedConditions.elementToBeClickable(dependentsAddButton));
        btn.click();
        shortWait(driver).until(ExpectedConditions.presenceOfElementLocated(dependentsPencilIcon));
        return this;
    }

    public P07_AddEmployeePage clickDependentsPencilIcon() {
        WebElement icon = shortWait(driver).until(ExpectedConditions.elementToBeClickable(dependentsPencilIcon));
        icon.click();
        return this;
    }

    public P07_AddEmployeePage typeDependentsField(String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(dependentsInputField));
        input.clear();
        input.sendKeys(value);
        return this;
    }

    private void typeInto(By by, String value) {
        WebElement input = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(by));
        input.clear();
        input.sendKeys(value);
    }
    public P07_AddEmployeePage setDepartmentToFirst() {
        openDropdownAndSelectFirst(departmentDropdown);
        return this;
    }

    public P07_AddEmployeePage setDirectManagerToFirst() {
        openDropdownAndSelectFirst(directManagerDropdown);
        return this;
    }

    public P07_AddEmployeePage setEmploymentTypeToFirst() {
        openDropdownAndSelectFirst(employmentTypeDropdown);
        return this;
    }

    public P07_AddEmployeePage setJobLevelToFirst() {
        openDropdownAndSelectFirst(jobLevelDropdown);
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
    private void waitForOverlayToDisappear() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            By.cssSelector("div[style*='z-index: 9999999']")
                    ));
        } catch (Exception ignored) {}
    }    private void selectCountryByText(String testId, String countryName) {
        // 1. تحديد اللوكيتور بناءً على الـ data-testid
        By dropdownLocator = By.cssSelector("p-dropdown[data-testid='" + testId + "']");

        // 2. الانتظار حتى يظهر الـ Dropdown (بنفس طريقتك)
        WebElement dropdown = shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));

        // 3. التأكد إن الأيقونة (Trigger) ظهرت (نفس الـ Logic بتاعك)
        shortWait(driver).until(d -> {
            try {
                return dropdown.findElement(By.cssSelector("svg.p-dropdown-trigger-icon, .p-dropdown-trigger-icon")).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });

        // 4. الضغط على الـ Trigger لفتح القائمة
        WebElement trigger = dropdown.findElement(By.cssSelector("[role='combobox'], .p-dropdown-trigger"));
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(trigger));
        trigger.click();

        // 5. اختيار الدولة بالاسم بدل "First Option"
        // الـ XPath ده هيدور على الـ Item اللي جواه نص "مصر"
        By countryOption = By.xpath("//li[contains(@class, 'p-dropdown-item')]//span[contains(text(), '" + countryName + "')]");

        WebElement option = shortWait(driver).until(ExpectedConditions.elementToBeClickable(countryOption));
        option.click();
    }

//
public P07_AddEmployeePage fillAcademicField(String columnLabel, String testId, String textToType, String fieldType) throws InterruptedException {

    switch (fieldType.toLowerCase()) {
        case "text":
            By inputLocator = By.cssSelector("input[data-testid='" + testId + "']");
            if (driver.findElements(inputLocator).isEmpty()) {
                clickPencilByColumnName(columnLabel);
            }
            typeInTableInput(testId, textToType);
            break;

        case "dropdown":
            // دوس على الـ pencil الأول
            By dropdownInCell = By.xpath("//td[@aria-label='" + columnLabel + "']//div[contains(@class, 'p-dropdown')]");

            if (driver.findElements(dropdownInCell).isEmpty()) {
                clickPencilByColumnName(columnLabel);
            }

            // انتظر الـ dropdown div يظهر
            WebElement dropdownDiv = shortWait(driver).until(
                    ExpectedConditions.elementToBeClickable(dropdownInCell)
            );

            // دوس على الـ dropdown عشان يفتح
            dropdownDiv.click();

            // انتظر الـ list تظهر واختار أول option
            WebElement firstOption = shortWait(driver).until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("li.p-dropdown-item, p-dropdownitem li"))
            );
            firstOption.click();
            break;
    }
    return this;
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

    public void selectDate(String placeholder, int index, int year, int month, int day) {
        List<WebElement> inputs = driver.findElements(
                By.cssSelector(String.format("input[placeholder='%s']", placeholder))
        );
        inputs.get(index).click();
        // ... باقي الكود
    }

    // ============== تاريخ الميلاد ==============
    public P07_AddEmployeePage selectBirthDate(int year, int month, int day) {
        Utility.selectDate(BIRTH_DATE, year, month, day);
        return this;
    }

    public P07_AddEmployeePage selectRandomBirthDate() {
        int[] date = Utility.generateRandomBirthDate();
        Utility.selectDate(BIRTH_DATE, date[0], date[1], date[2]);
        return this;
    }

    // ============== المستند الأول (مثلاً: الهوية) ==============
    public P07_AddEmployeePage selectIdIssueDate(int year, int month, int day) {
        Utility.selectDate(ISSUE_DATE, 0, year, month, day);
        return this;
    }

    public P07_AddEmployeePage selectIdExpiryDate(int year, int month, int day) {
        Utility.selectDate(EXPIRY_DATE, 0, year, month, day);
        return this;
    }

    // ============== المستند الثاني (مثلاً: جواز السفر) ==============
    public P07_AddEmployeePage selectPassportIssueDate(int year, int month, int day) {
        Utility.selectDate(ISSUE_DATE, 1, year, month, day);
        return this;
    }

    public P07_AddEmployeePage selectPassportExpiryDate(int year, int month, int day) {
        Utility.selectDate(EXPIRY_DATE, 1, year, month, day);
        return this;
    }

    // ============== Random Dates ==============
    public P07_AddEmployeePage selectIdRandomDates() {
        int[] issue = Utility.generateRandomPastDate(5);
        int[] expiry = Utility.generateRandomFutureDate(5);
        Utility.selectDate(ISSUE_DATE, 0, issue[0], issue[1], issue[2]);
        Utility.selectDate(EXPIRY_DATE, 0, expiry[0], expiry[1], expiry[2]);
        return this;
    }

    public P07_AddEmployeePage selectPassportRandomDates() {
        int[] issue = Utility.generateRandomPastDate(5);
        int[] expiry = Utility.generateRandomFutureDate(10);
        Utility.selectDate(ISSUE_DATE, 1, issue[0], issue[1], issue[2]);
        Utility.selectDate(EXPIRY_DATE, 1, expiry[0], expiry[1], expiry[2]);
        return this;
    }
// ============== Table Cell Calendar Methods ==============

    public P07_AddEmployeePage selectTableBirthDate(int rowIndex, int year, int month, int day) {
        Utility.selectDateInTableCell("تاريخ الميلاد", rowIndex, year, month, day);
        return this;
    }

    public P07_AddEmployeePage selectTableRandomBirthDate(int rowIndex) {
        Utility.selectRandomBirthDateInTableCell("تاريخ الميلاد", rowIndex);
        return this;
    }
    public P07_AddEmployeePage selectSubscriptionStartDate(int year, int month, int day) {
        Utility.selectDate(SUBSCRIPTION_START_DATE, year, month, day);
        return this;
    }

    public P07_AddEmployeePage selectRandomSubscriptionStartDate() {
        int[] date = Utility.generateRandomPastDate(2);
        Utility.selectDate(SUBSCRIPTION_START_DATE, date[0], date[1], date[2]);
        return this;
    }
    // ============== Insurance Salary Input ==============

    public P07_AddEmployeePage setInsuranceSalary(int salary) {
        WebElement input = shortWait(driver).until(
                ExpectedConditions.elementToBeClickable(insuranceSalaryInput)
        );
        input.clear();
        input.sendKeys(String.valueOf(salary));
        return this;
    }

    public P07_AddEmployeePage setRandomInsuranceSalary() {
        int salary = Utility.generateRandomNumber(10000) + 3000; // 3000 - 13000
        return setInsuranceSalary(salary);
    }

    public P07_AddEmployeePage setRandomInsuranceSalary(int min, int max) {
        int salary = Utility.generateRandomNumber(max - min) + min;
        return setInsuranceSalary(salary);
    }

// ============== Toggle Switch ==============

    public P07_AddEmployeePage clickToggleSwitch() {
        shortWait(driver).until(
                ExpectedConditions.elementToBeClickable(toggleSwitch)
        ).click();
        return this;
    }

    public P07_AddEmployeePage enableToggleSwitch() {
        WebElement toggle = shortWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(toggleSwitch)
        );

        // لو مش مفعّل - فعّله
        if (!toggle.getAttribute("class").contains("p-inputswitch-checked")) {
            toggle.click();
        }
        return this;
    }

    public P07_AddEmployeePage disableToggleSwitch() {
        WebElement toggle = shortWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(toggleSwitch)
        );

        // لو مفعّل - اقفله
        if (toggle.getAttribute("class").contains("p-inputswitch-checked")) {
            toggle.click();
        }
        return this;
    }
//    public P07_AddEmployeePage fillAcademicField(String columnLabel, String testId, String textToType) {
//
//// 1. الضغط على القلم لفتح الخلية
//
//        clickPencilByColumnName(columnLabel);
//
//
//
//// 2. الكتابة في الحقل اللي ظهر
//
//        typeInTableInput(testId, textToType);
//
//
//
//// 3. إرجاع الكلاس الحالي للسماح بالـ Chaining
//
//        return this;
//
//    }
//



    /**

     * ميثود لفتح الخلية للتعديل بناءً على اسم العمود

     * @param columnLabel اسم العمود (مثل: المستوى التعليمي، اسم المؤهل)

     */
//
//    public void clickPencilByColumnName(String columnLabel) {
//
//// بناء الـ XPath باستخدام اسم العمود اللي أنت حددته
//
//        String xpathExpression = String.format(
//
//                "//td[@aria-label='%s']//span[contains(@class, 'fa-pencil')]",
//
//                columnLabel
//
//        );
//
//
//
//// الانتظار حتى يكون القلم قابل للضغط
//
//        WebElement pencilIcon = shortWait(driver).until(
//
//                ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression))
//
//        );
//
//
//
//        pencilIcon.click();
//
//    }

    /**

     * ميثود للكتابة في الـ Input بناءً على الـ data-testid

     * @param testId المعرف الخاص بالحقل (مثل: educationLevel)

     * @param textToType النص المراد كتابته

     */

//    public void typeInTableInput(String testId, String textToType) {
//
//// 1. تحديد اللوكيتور باستخدام الـ data-testid
//
//        By inputLocator = By.cssSelector("input[data-testid='" + testId + "']");
//
//
//
//// 2. الانتظار حتى يظهر الـ Input ويكون قابل للتفاعل
//
//// استخدمنا shortWait بتاعك لضمان إن الـ Edit Mode اشتغل
//
//        WebElement inputElement = longWait(driver).until(
//
//                ExpectedConditions.elementToBeClickable(inputLocator)
//
//        );
//        ((JavascriptExecutor) driver).executeScript("arguments[0].focus(); arguments[0].click();", inputElement);// 3. مسح أي نص قديم والكتابة
//        inputElement.sendKeys(" ");
//        inputElement.sendKeys(Keys.BACK_SPACE);
//        inputElement.clear();
//
//        inputElement.sendKeys(textToType);
//
//
//
//// (اختياري) لو السيستم بيحتاج Enter عشان يحفظ الخلية:
//
//// inputElement.sendKeys(Keys.ENTER);
//
//    }











}
