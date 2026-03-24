package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P04_WorkLocationPage;

import static drivers.DriverHolder.getDriver;

public class TC04_VerifyWorkLocationPage extends TestBase {

    private P04_WorkLocationPage page;

    /** مرة واحدة قبل كل التيستات: فتح صفحة مناطق العمل. */
    @BeforeClass
    public void openWorkLocationPage() {
        page = new P04_WorkLocationPage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Work Location page loads and table has data")
    public void verifyWorkLocationTableHasData() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "جدول مناطق العمل فارغ - لا توجد بيانات");
    }

    /** Happy scenario: إضافة منطقة عمل (الاسم + الموظف المسؤول) → حفظ → رسالة نجاح → الاسم يظهر في الجدول. */
    @Test(priority = 2, description = "Add: enter work location name and responsible employee, save, verify success toast and name in table")
    public void addWorkLocation_NameAndResponsible_SuccessAndShownInTable() throws InterruptedException {
        String workLocationName = "منطقة عمل " + faker.number().digits(4);
        page.clickAddButton()
                .setWorkLocationName(workLocationName)
                .setResponsibleEmployeeToFirst()
                .clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الإضافة");
        Assert.assertTrue(page.isWorkLocationNameInTable(workLocationName),
                "اسم منطقة العمل المضاف لا يظهر في الجدول: " + workLocationName);
    }

    /** Happy scenario: تعديل اسم منطقة العمل (أول صف) → حفظ → رسالة نجاح → الاسم الجديد يظهر في الجدول. */
    @Test(priority = 3, description = "Edit: change work location name, save, verify success toast and new name in table")
    public void editWorkLocation_NameUpdatedAndSuccessToast() throws InterruptedException {
        String newName = "منطقة عمل معدلة " + faker.number().digits(4);
        page.clickEditButton(1)
                .setWorkLocationName(newName)
                .clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
//        String nameInFirstRow = page.getWorkLocationNameFromListRow(1);
//        System.out.println("nameInFirstRow  " + nameInFirstRow);
        System.out.println("newName  " +newName );
        Assert.assertTrue(page.isWorkLocationNameInTable(newName));

//                Assert.assertTrue(nameInFirstRow.contains(newName),
//                "الاسم الجديد لا يظهر في الجدول. المتوقع يحتوي: " + newName + "، الفعلي: " + nameInFirstRow);
    }

    /** Happy scenario: حذف أول صف → تأكيد → رسالة نجاح → الاسم يختفي من الجدول. */
    @Test(priority = 4, description = "Delete: remove first row, confirm, verify success and name not in table")
    public void deleteWorkLocation_RowRemovedAndSuccessToast() throws InterruptedException {
        String nameToDelete = page.getWorkLocationNameFromListRow(1);
        page.clickDeleteButton(1)
                .clickConfirmDeleteButton()
                .waitForDeleteSuccess();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Assert.assertFalse(page.isWorkLocationNameInTable(nameToDelete),
                "اسم منطقة العمل المحذوفة لا يزال يظهر في الجدول: " + nameToDelete);
    }
}
