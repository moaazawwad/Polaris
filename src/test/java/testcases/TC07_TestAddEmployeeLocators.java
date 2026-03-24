//package testcases;
//
//import org.testng.annotations.Test;
//import pages.P02_EmployeesDataPage;
//import pages.P07_AddEmployeePage;
//
//import static drivers.DriverHolder.getDriver;
//
//public class TC07_TestAddEmployeeLocators extends TestBase {
//
//    @Test(description = "Add employee - fill all data once then save")
//    public void addEmployeeFillAllDataOnce() throws InterruptedException {
//        // 1. التعريف والبدء
//        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
//        P07_AddEmployeePage addPage = new P07_AddEmployeePage(getDriver());
//
//        // 2. الانتقال لصفحة الإضافة
//        listPage.navigateToPage().clickAddButton();
//
//        // 3. ملء البيانات مقسمة لمجموعات منطقية
//        fillBasicAndPersonalData(addPage);
//        fillContactInformation(addPage);
//        fillAcademicInformation(addPage);
//        Thread.sleep(2000);
//        fillOfficialAndJobInformation(addPage);
//        fillDependentsInformation(addPage);
//        fillInsuranceInformation(addPage);
//
//        // 4. الحفظ والتحقق
//        addPage.clickSave();
//        // يمكنك إضافة Assertion هنا للتأكد من نجاح العملية
//    }
//
//    // --- Private Helper Methods لتنظيم الكود ---
//
//    private void fillBasicAndPersonalData(P07_AddEmployeePage addPage) throws InterruptedException {
//        Thread.sleep(4000);
//        addPage
//                .setEmployeeNameAr(faker.name().fullName() + " تجريبي")
//                .setEmployeeNameEn(faker.name().fullName() + " Test")
//                .setAttendanceCode(faker.number().digits(5))
//                .setImageUpload()
//                .setGenderToFirst()
//                .selectRandomBirthDate()
//                .setNationalityToFirst()
//                .setBirthCountryToFirst()
//                .setBirthCity(faker.address().city())
//                .setMaritalStatusToFirst()
//                .setReligionToFirst()
//                .setBloodGroupToFirst()
//                .setSpecialNeeds(false);
//    }
//
//    private void fillContactInformation(P07_AddEmployeePage addPage) {
//        addPage.clickContactInfoTab();
//        addPage
//                .setCountryToFirst()
//                .setCity(faker.address().city())
//                .setRegion("المنطقة الوسطى")
//                .setAddress(faker.address().streetAddress())
//                .setBuildingNumber(faker.address().buildingNumber())
//                .setMailBox("1234")
//                  .setPostalCode("12345")
//                .setEmail(faker.internet().emailAddress())
//                .setMobileCountryCodesToFirst()
//                .setMobile(faker.phoneNumber().cellPhone())
//                .setSecMobile("01033222222")
//                .setLandLineCountryCodeToFirst()
//                .setLandLine(faker.phoneNumber().phoneNumber())
//                .setOfficePhoneCountryCodeToFirst()
//                .setOfficePhone("112345679")
//                .setExtension("100");
//    }
//
//    private void fillAcademicInformation(P07_AddEmployeePage addPage) throws InterruptedException {
//        addPage.clickAcademicInfoTab();
//        Thread.sleep(1000);
//        addPage
//                .fillAcademicField("المستوى التعليمي", "educationLevel", "بكالوريوس", "text")
//                .fillAcademicField("اسم المؤهل", "qualificationName", "هندسة برمجيات", "text")
//                .fillAcademicField("المؤسسة التعليمية", "educationInstitution", "جامعة القاهرة", "text")
//                .fillAcademicField("التخصص", "specialization", "test", "text")
//                .fillAcademicField("التقدير", "grade", "test", "text")
//                .fillAcademicField("سنة التخرج", "graduationYear", "2020", "text");
//    }
//
//    private void fillOfficialAndJobInformation(P07_AddEmployeePage addPage) throws InterruptedException {
//        Thread.sleep(6000);
//
//        addPage.clickOfficialInfoTab();
//        addPage
//                .setNationalIdNumber(faker.number().digits(10))
//                .selectIdRandomDates()
//                .setNationalIdIssuingAuthority("جهة الاصدار")
//                .selectPassportRandomDates()
//                .setPassportNumber("A" + faker.number().digits(8))
//            .setPassportIssuingAuthority("جهة الاصدار");
//
//        addPage.clickJobInfoTab()
//                .setDepartmentToFirst()
//                .setDirectManagerToFirst()
//                .setEmploymentTypeToFirst()
//                .setJobLevelToFirst();
//    }
//
//    private void fillInsuranceInformation(P07_AddEmployeePage addPage) throws InterruptedException {
//        Thread.sleep(1000);
//
//        addPage.clickInsuranceTab()
//                .enableToggleSwitch()
//                .setRandomInsuranceSalary()
//                .selectRandomSubscriptionStartDate();
//    }
//    private void fillDependentsInformation(P07_AddEmployeePage addPage) throws InterruptedException {
//        addPage.clickDependentsTab();
//        Thread.sleep(1000); // للتأكد من تحميل التاب
//
//        addPage
//                .fillAcademicField("الاسم", "name", faker.name().fullName(), "text")
//                .fillAcademicField("الجنسية", "", "", "dropdown") // سيبتها فاضية حسب كودك الأصلي
//                .fillAcademicField("صلة القرابة", "", "", "dropdown")
//                .fillAcademicField("رقم الهاتف ", "phoneNumber", faker.number().digits(10), "text")
//                .selectTableRandomBirthDate(0);
//    }
//}

















package testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P02_EmployeesDataPage;
import pages.P07_AddEmployeePage;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Locale;

import static drivers.DriverHolder.getDriver;
import static pages.PageBase.longWait;

/**
 * Test suite for Add Employee functionality
 * Validates complete employee creation flow with all data sections
 *
 * @author QA Team
 * @version 1.0
 */
public class TC07_TestAddEmployeeLocators extends TestBase {

    private static final String ADD_EMPLOYEE_HEADER_XPATH = "//span[contains(text(),'إضافة موظف')]";
    private static final String PERSONAL_INFO_TAB_XPATH = "//*[contains(text(),'المعلومات الشخصية')]";
    private static final String SUCCESS_MESSAGE_XPATH = "//*[contains(text(),'تم') or contains(text(),'نجح')]";
    private static final String EMPLOYEES_DATA_URL = "employees-data";

    private static final int PAGE_LOAD_TIMEOUT = 4000;
    private static final int TAB_SWITCH_TIMEOUT = 3000;

    /**
     * Test Case: Add Employee - Complete Data Entry Flow
     *
     * Steps:
     * 1. Navigate to Add Employee page
     * 2. Fill Basic Information
     * 3. Fill Personal Information
     * 4. Fill Contact Information
     * 5. Fill Academic Information
     * 6. Fill Official Documents Information
     * 7. Fill Job Information
     * 8. Fill Dependents Information
     * 9. Fill Insurance Information
     * 10. Save and validate success
     *
     * @throws InterruptedException if thread sleep is interrupted
     */
//    @Test(description = "Add employee - Complete data entry in single flow without page reload")
//    public void testAddEmployeeWithCompleteData() throws InterruptedException {
//
//        // Step 1: Navigate to Add Employee page
//        navigateToAddEmployeePage();
//
//        // Step 2: Initialize Add Employee Page Object
//        P07_AddEmployeePage addPage = new P07_AddEmployeePage(getDriver());
//
//        // Step 3: Wait for page to be fully loaded
//        waitForAddEmployeePageLoad();
//
//        // Step 4: Fill all employee data sections
//        fillBasicInformation(addPage);
//        fillPersonalInformation(addPage);
//        fillContactInformation(addPage);
//        fillAcademicInformation(addPage);
//        fillOfficialDocuments(addPage);
//        fillJobInformation(addPage);
//        fillDependentsInformation(addPage);
//        fillInsuranceInformation(addPage);
//
//        // Step 5: Save and validate
//        saveAndValidateEmployee(addPage);
//    }

    /**
     * Simple test: Add employee with required + تاريخ الميلاد + الجنسية (داتا عشوائية).
     * يتأكد من رسالة النجاح وأن الموظف يظهر في الجدول.
     */
    @Test(description = "Add employee - required + birth date + nationality (random data)")
    public void testAddEmployeeRequiredOnly() throws InterruptedException {
        String nameAr = new com.github.javafaker.Faker(Locale.forLanguageTag("ar")).name().fullName() + " التجريبي";
        String nameEn = (faker.name().fullName() + " Test").replaceAll("[^a-zA-Z\\s]", "");
        String attendanceCode = faker.number().digits(5);

        navigateToAddEmployeePage();
        P07_AddEmployeePage addPage = new P07_AddEmployeePage(getDriver());
        waitForAddEmployeePageLoad();

        addPage
                .setEmployeeNameAr(nameAr)
                .setEmployeeNameEn(nameEn)
                .setAttendanceCode(attendanceCode)
//                .clickPersonalInfoTab()
                .selectRandomBirthDate()
                .setNationalityToFirst()
                .clickSave();

        Assert.assertTrue(addPage.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد إضافة الموظف");

        // التطبيق يحوّل تلقائياً لصفحة قائمة الموظفين بعد الإضافة
        longWait(getDriver()).until(ExpectedConditions.urlContains(EMPLOYEES_DATA_URL));

        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
        Assert.assertTrue(listPage.isEmployeeNameInTable(nameAr), "الموظف المضاف لا يظهر في جدول الموظفين");
    }

    /**
     * Navigate to Add Employee page from Employees Data list page
     */
    private void navigateToAddEmployeePage() {
        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
        listPage.navigateToPage().clickAddButton();
    }

    /**
     * Wait for Add Employee page elements to be loaded and clickable
     *
     * @throws InterruptedException if thread sleep is interrupted
     */
    private void waitForAddEmployeePageLoad() throws InterruptedException {
        longWait(getDriver()).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(ADD_EMPLOYEE_HEADER_XPATH))
        );
        longWait(getDriver()).until(
                ExpectedConditions.elementToBeClickable(By.xpath(PERSONAL_INFO_TAB_XPATH))
        );
        Thread.sleep(PAGE_LOAD_TIMEOUT);
    }

    /**
     * Fill Basic Information section
     * - Arabic Name
     * - English Name
     * - Attendance Code
     * - Profile Image
     *
     * @param addPage Add Employee page object
     */
    private void fillBasicInformation(P07_AddEmployeePage addPage) {
        addPage
                .setEmployeeNameAr("أحمد محمد التجريبي")
                .setEmployeeNameEn("Ahmed Mohamed Test")
                .setAttendanceCode("95464")
                .setImageUpload();
    }

    /**
     * Fill Personal Information section (المعلومات الشخصية)
     * - Gender
     * - Birth Date
     * - Nationality
     * - Birth Country & City
     * - Marital Status
     * - Religion
     * - Blood Group
     * - Special Needs
     *
     * @param addPage Add Employee page object
     */
    private void fillPersonalInformation(P07_AddEmployeePage addPage) {
        addPage
                .setGenderToFirst()
                .selectRandomBirthDate()
                .setNationalityToFirst()
                .setBirthCountryToFirst()
                .setBirthCity("القاهرة")
                .setMaritalStatusToFirst()
                .setReligionToFirst()
                .setBloodGroupToFirst()
                .setSpecialNeeds(false);
    }

    /**
     * Fill Contact Information section (معلومات جهة الاتصال)
     * - Address details (Country, City, Region, Street, Building)
     * - Postal information (Mail Box, Postal Code)
     * - Contact numbers (Mobile, Secondary Mobile, Landline, Office Phone)
     * - Email
     *
     * @param addPage Add Employee page object
     */
    private void fillContactInformation(P07_AddEmployeePage addPage) throws InterruptedException {
        addPage.clickContactInfoTab();

        addPage
                .setCountryToFirst()
                .setCity("الرياض")
                .setRegion("المنطقة الوسطى")
                .setAddress("شارع الملك فهد")
                .setBuildingNumber("10")
                .setMailBox("1234")
                .setPostalCode("12345")
                .setEmail("test@example.com")
                .setMobileCountryCodesToFirst()
                .setMobile("01033222222")
                .setSecMobile("01033222222")
                .setLandLineCountryCodeToFirst()
                .setLandLine("112345678")
                .setOfficePhoneCountryCodeToFirst()
                .setOfficePhone("112345679")
                .setExtension("100");
    }

    /**
     * Fill Academic Information section
     * - Education Level
     * - Qualification Name
     * - Education Institution
     * - Specialization
     * - Grade
     * - Graduation Year
     *
     * @param addPage Add Employee page object
     * @throws InterruptedException if thread sleep is interrupted
     */
    private void fillAcademicInformation(P07_AddEmployeePage addPage) throws InterruptedException {
        addPage.clickAcademicInfoTab();
        Thread.sleep(TAB_SWITCH_TIMEOUT);

        addPage
                .fillAcademicField("المستوى التعليمي", "educationLevel", "test", "text")
                .fillAcademicField("اسم المؤهل", "qualificationName", "test", "text")
                .fillAcademicField("المؤسسة التعليمية", "educationInstitution", "test", "text")
                .fillAcademicField("التخصص", "specialization", "test", "text")
                .fillAcademicField("التقدير", "grade", "test", "text")
                .fillAcademicField("سنة التخرج", "graduationYear", "2025", "text");
    }

    /**
     * Fill Official Documents section
     * - National ID (Number, Issue/Expiry Dates, Issuing Authority)
     * - Passport (Number, Issue/Expiry Dates, Issuing Authority)
     *
     * @param addPage Add Employee page object
     */
    private void fillOfficialDocuments(P07_AddEmployeePage addPage) {
        addPage.clickOfficialInfoTab();

        addPage
                .setNationalIdNumber("1234567890")
                .selectIdRandomDates()
                .selectPassportRandomDates()
                .setPassportNumber("A12345678")
                .setNationalIdIssuingAuthority("asdsad")
                .setPassportIssuingAuthority("asdasd");
    }

    /**
     * Fill Job Information section
     * - Department
     * - Direct Manager
     * - Employment Type
     * - Job Level
     *
     * @param addPage Add Employee page object
     */
    private void fillJobInformation(P07_AddEmployeePage addPage) {
        addPage.clickJobInfoTab()
                .setDepartmentToFirst()
                .setDirectManagerToFirst()
                .setEmploymentTypeToFirst()
                .setJobLevelToFirst();
    }

    /**
     * Fill Dependents Information section
     * - Name
     * - Nationality
     * - Relationship
     * - Phone Number
     * - Birth Date
     *
     * @param addPage Add Employee page object
     */
    private void fillDependentsInformation(P07_AddEmployeePage addPage) throws InterruptedException {
        addPage.clickDependentsTab();

        addPage
                .fillAcademicField("الاسم", "name", "test", "text")
                .fillAcademicField("الجنسية", "", "", "dropdown")
                .fillAcademicField("صلة القرابة", "", "", "dropdown")
                .fillAcademicField("رقم الهاتف ", "phoneNumber", "0102232322", "text")
                .selectTableRandomBirthDate(0);
    }

    /**
     * Fill Insurance Information section
     * - Enable Insurance
     * - Set Insurance Salary
     * - Set Subscription Start Date
     *
     * @param addPage Add Employee page object
     */
    private void fillInsuranceInformation(P07_AddEmployeePage addPage) {
        addPage.clickInsuranceTab()
                .enableToggleSwitch()
                .setRandomInsuranceSalary()
                .selectRandomSubscriptionStartDate();
    }

    /**
     * Save employee data and validate successful creation
     * Validates either redirect to employees list or success message display
     *
     * @param addPage Add Employee page object
     */
    private void saveAndValidateEmployee(P07_AddEmployeePage addPage) {
        addPage.clickSave();

        longWait(getDriver()).until(ExpectedConditions.or(
                ExpectedConditions.urlContains(EMPLOYEES_DATA_URL),
                ExpectedConditions.presenceOfElementLocated(By.xpath(SUCCESS_MESSAGE_XPATH))
        ));
    }
}
























//
//package testcases;
//
//import org.openqa.selenium.By;
//import org.testng.annotations.Test;
//import pages.P02_EmployeesDataPage;
//import pages.P07_AddEmployeePage;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//import static drivers.DriverHolder.getDriver;
//import static pages.PageBase.longWait;
//
///**
// * Test Add Employee page - fill all data in one flow (no reload = no data loss).
// */
//public class TC07_TestAddEmployeeLocators extends TestBase {
////, invocationCount = 5
//    @Test(description = "Add employee - fill all data once then save")
//    public void addEmployeeFillAllDataOnce() throws InterruptedException {
//        // 1) Open Add form once (no navigation after this)
//        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
//        listPage.navigateToPage().clickAddButton();
//
//        P07_AddEmployeePage addPage = new P07_AddEmployeePage(getDriver());
//        longWait(getDriver()).until(ExpectedConditions.presenceOfElementLocated(
//            By.xpath("//span[contains(text(),'إضافة موظف')]")));
//        longWait(getDriver()).until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//*[contains(text(),'المعلومات الشخصية')]")));
//    Thread.sleep(3000);
////        // 2) Basic Information
//        addPage
//            .setEmployeeNameAr("أحمد محمد التجريبي")
//            .setEmployeeNameEn("Ahmed Mohamed Test")
//            .setAttendanceCode("95464")
//            .setImageUpload();
//
////         3) Personal Information (المعلومات الشخصية) – شغّال
//        addPage
//            .setGenderToFirst().
//                selectRandomBirthDate()
//            .setNationalityToFirst()
//            .setBirthCountryToFirst()
//            .setBirthCity("القاهرة")
//            .setMaritalStatusToFirst()
//            .setReligionToFirst()
//            .setBloodGroupToFirst()
//            .setSpecialNeeds(false);
//
//        // 4) Contact tab (معلومات جهة الاتصال) – شغّال
//        addPage.clickContactInfoTab();
//        addPage
//            .setCountryToFirst()
//            .setCity("الرياض")
//            .setRegion("المنطقة الوسطى")
//            .setAddress("شارع الملك فهد")
//            .setBuildingNumber("10")
//            .setMailBox("1234")
//            .setPostalCode("12345")
//            .setEmail("test@example.com")
//            .setMobileCountryCodesToFirst()
//            .setMobile("01033222222")
//            .setSecMobile("01033222222")
//            .setLandLineCountryCodeToFirst()
//            .setLandLine("112345678")
//            .setOfficePhoneCountryCodeToFirst()
//            .setOfficePhone("112345679")
//            .setExtension("100");
//
//                addPage.clickAcademicInfoTab();
//        Thread.sleep(2000);
//
//        addPage
//            .fillAcademicField("المستوى التعليمي", "educationLevel", "test", "text")
//            .fillAcademicField("اسم المؤهل", "qualificationName", "test", "text")
//            .fillAcademicField("المؤسسة التعليمية", "educationInstitution", "test", "text")
//            .fillAcademicField("التخصص", "specialization", "test", "text")
//            .fillAcademicField("التقدير", "grade", "test", "text")
//            .fillAcademicField("سنة التخرج", "graduationYear", "2025", "text");
//
//
//
////         5) Official tab – معلّق
//        addPage.clickOfficialInfoTab();
//        addPage
//            .setNationalIdNumber("1234567890")
//                .selectIdRandomDates()
//                .selectPassportRandomDates()
//            .setPassportNumber("A12345678")
//            .setNationalIdIssuingAuthority("asdsad")
//            .setPassportIssuingAuthority("asdasd");
//            addPage.clickJobInfoTab()
//                    .setDepartmentToFirst()
//                    .setDirectManagerToFirst()
//                    .setEmploymentTypeToFirst()
//                    .setJobLevelToFirst();
//
//        // 7) Dependents – معلّق
//        addPage.clickDependentsTab();
//        addPage
//            .fillAcademicField("الاسم", "name", "test", "text")
//                .fillAcademicField("الجنسية", "", "", "dropdown")
//                .fillAcademicField("صلة القرابة", "", "", "dropdown")
//            .fillAcademicField("رقم الهاتف ", "phoneNumber", "0102232322", "text")
//                .selectTableRandomBirthDate(0);
//
//
//        addPage.clickInsuranceTab()
//                .enableToggleSwitch()
//                .setRandomInsuranceSalary()
//                .selectRandomSubscriptionStartDate();
//
//
//
//        // 8) Save + assertion – معلّق
//        addPage.clickSave();
//        longWait(getDriver()).until(ExpectedConditions.or(
//            ExpectedConditions.urlContains("employees-data"),
//            ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'تم') or contains(text(),'نجح')]"))
//        ));
//    }
//}
