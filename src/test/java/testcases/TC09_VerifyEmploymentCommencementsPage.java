package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P09_EmploymentCommencementsPage;

import static drivers.DriverHolder.getDriver;

/**
 * CRUD tests for Employment Commencements: table data, View, Add, Edit, Delete.
 */
public class TC09_VerifyEmploymentCommencementsPage extends TestBase {

    private P09_EmploymentCommencementsPage page;

    @BeforeClass
    public void openEmploymentCommencementsPage() {
        page = new P09_EmploymentCommencementsPage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Employment Commencements table has data")
    public void verifyEmploymentCommencementsTableHasData() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "Employment Commencements table is empty - no data found");
    }

    @Test(priority = 2, description = "View: code from first row matches view page code, then back")
    public void viewCommencement_CodeMatchesThenBack() {
        String codeOnList = page.getCodeFromListRow(1);
        page.clickViewButton(1);
        String codeOnView = page.getCodeFromViewPage();
        Assert.assertEquals(codeOnView, codeOnList, "الكود في صفحة العرض لا يطابق الكود في القائمة");
        page.clickBackButton();
    }

    @Test(priority = 3, description = "Add: fill form, save, verify success toast")
    public void addCommencement_SaveAndSuccessToast() {
        page.clickAddButton();
        page.setCommencementDate();
        page.selectFirstEmployee();
        page.selectFirstCommencementType();
        page.setNotes("ملاحظة اختبار " + faker.number().digits(3));
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الإضافة");
    }

    @Test(priority = 4, description = "Edit: change notes, save, verify success toast")
    public void editCommencement_SaveAndSuccessToast() throws InterruptedException {
        String newNotes = "ملاحظة تعديل " + faker.number().digits(3);
        page.clickEditButton(1);
        Thread.sleep(500);
        page.setNotes(newNotes);
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
        page.clickBackButton();

    }

    @Test(priority = 5, description = "Delete: confirm popup, verify success toast and code removed from table")
    public void deleteCommencement_ConfirmAndSuccessToast() throws InterruptedException {
        String codeToDelete = page.getCodeFromListRow(1);
        page.clickDeleteButton(1);
        page.clickConfirmDeleteButton();
        page.waitForDeleteSuccess();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Assert.assertFalse(page.isCodeInTable(codeToDelete),
                "الكود المحذوف لا يزال يظهر في الجدول: " + codeToDelete);
    }
}
