package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P06_EmployeeContractPage;

import static drivers.DriverHolder.getDriver;

/**
 * عقود الموظفين (Employee Contract): تحميل الصفحة، جدول، عرض، إضافة، تعديل، حذف، تأكيد، توستر.
 */
public class TC06_VerifyEmployeeContractPage extends TestBase {

    private P06_EmployeeContractPage page;

    @BeforeClass
    public void openContractPage() {
        page = new P06_EmployeeContractPage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Employee Contract page loads and table has data")
    public void verifyEmployeeContractTableHasData() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "Employee Contract table is empty - no data found");
    }

    @Test(priority = 2, description = "View: open view for first row, verify page/dialog, then back to list")
    public void viewContract_OpenThenBack() throws InterruptedException {
        String urlBefore = getDriver().getCurrentUrl();
        page.clickViewButton(1);
        Thread.sleep(2000);
        String urlAfter = getDriver().getCurrentUrl();
        Assert.assertNotEquals(urlAfter, urlBefore, "النقر على عرض لم يغيّر الصفحة");
        page.navigateToPage();
        Thread.sleep(500);
    }

    @Test(priority = 3, description = "Add: form + تاب المعلومات الوظيفية + تاب البدلات والرواتب + تعيين بنود الراتب، ثم حفظ")
    public void addContract_MinimalData_VerifyToast() throws InterruptedException {
        page.clickAddButton();
        Thread.sleep(1500);
        Assert.assertTrue(page.isAddContractFormDisplayed(), "نموذج إضافة العقد لم يظهر");

        page.selectEmployeeByIndex(0)
                .setContractDate(2026, 1, 1)
                .setContractStartDate(2026, 1, 1)
                .setContractEndDate(2026, 12, 31)
                .selectRenewalTypeByIndex(0)
                .selectContractTypeByIndex(0)
                .selectContractDurationByIndex(0)
                .setVacationDays("21")
                .setVacationAccrualMonths("1")
                .setTicketsCount("2")
                .setTicketsAccrualMonths("1")
                .setNoticePeriodDays("30");

        Thread.sleep(500);
        page.clickJobInfoTab();
        Thread.sleep(600);
        page.fillContractTableField("اسم الوظيفة", null, "dropdown");
        Thread.sleep(400);
        page.fillContractTableField("قسم", null, "dropdown");
        Thread.sleep(600);
        page.fillContractTableField(P06_EmployeeContractPage.COLUMN_LABEL_WORK_LOCATION, null, "dropdown");

        Thread.sleep(500);
        page.clickAllowancesTab();
        Thread.sleep(600);
        page.fillContractTableField("نموذج الراتب", null, "dropdown");
        Thread.sleep(400);
        page.fillContractTableField("طريقة الدفع", null, "dropdown");
        Thread.sleep(400);
        page.clickAssignSalaryItemsButton();
        page.fillSalaryItemsPopupAndSave("2000");

        Thread.sleep(500);
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد إضافة العقد");
        Thread.sleep(1500);
    }

    @Test(priority = 4, description = "Edit: تعديل أول صف — تغيير حقل واحد فقط (فترة الإشعار) ثم حفظ وتوستر")
    public void editContract_ChangeOneField_VerifyToast() throws InterruptedException {
        page.navigateToPage();
        Thread.sleep(1200);
        page.clickEditButton(1);
        Thread.sleep(2000);
        Assert.assertTrue(page.isAddContractFormDisplayed(), "نموذج تعديل العقد لم يظهر");

        page.setNoticePeriodDays("45")
                .clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد تعديل العقد");
        Thread.sleep(1500);
    }

    @Test(priority = 5, description = "Delete: حذف أول صف — تأكيد الـ popup ثم توستر النجاح")
    public void deleteContract_ConfirmAndVerifyToast() throws InterruptedException {
        page.navigateToPage();
        Thread.sleep(1200);
        page.clickDeleteButton(1)
                .clickConfirmDeleteButton();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد الحذف");
        Thread.sleep(1500);
    }
}
