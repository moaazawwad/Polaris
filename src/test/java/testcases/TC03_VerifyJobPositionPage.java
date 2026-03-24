package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P03_JobPositionPage;

import static drivers.DriverHolder.getDriver;

public class TC03_VerifyJobPositionPage extends TestBase {

    private P03_JobPositionPage page;

    /** مرة واحدة قبل كل التيستات: فتح صفحة الوظائف. */
    @BeforeClass
    public void openJobPositionPage() {
        page = new P03_JobPositionPage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1 , description = "Verify Job Position page loads and table has data")
    public void verifyJobPositionTableHasData() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "Job Position table is empty - no data found");
    }

    /** Happy scenario: إضافة وظيفة (اسم الوظيفة فقط) → حفظ → رسالة نجاح → الرجوع للجدول والاسم يظهر. */
    @Test(priority = 2 , description = "Add: enter job name only, save, verify success toast and name in table")
    public void addJobPosition_NameOnly_SuccessAndShownInTable() {
        String jobName = "وظيفة تجريبي " + faker.number().digits(4);
        page.clickAddButton()
                .setJobName(jobName)
                .clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الإضافة");
        Assert.assertTrue(page.isJobNameInTable(jobName),
                "اسم الوظيفة المضاف لا يظهر في الجدول: " + jobName);
    }

    /** Happy scenario: كود الوظيفة في أول صف = كود الوظيفة في صفحة العرض بعد ضغط View */
    @Test(priority = 3 , description = "View: job code on list matches job code on view page")
    public void viewJobPosition_JobCodeMatches() {
        String codeOnList = page.getJobCodeFromListRow(1);
        page.clickViewButton(1);
        String codeOnViewPage = page.getJobCodeFromViewPage();
        Assert.assertEquals(codeOnViewPage, codeOnList, "كود الوظيفة في صفحة العرض لا يطابق كود الصف في القائمة");
        page = new P03_JobPositionPage(getDriver());
        page.navigateToPage();


    }

    /** Happy scenario: تعديل اسم الوظيفة (أول صف) → حفظ → رسالة نجاح → الاسم الجديد يظهر في الجدول. */
    @Test(priority = 4 ,description = "Edit: change job name, save, verify success toast and new name in table")
    public void editJobPosition_NameUpdatedAndSuccessToast() {
        String newName = "وظيفة تجريبي " + faker.number().digits(4);
        page.clickEditButton(1)
                .setJobName(newName)
                .clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
        String nameInFirstRow = page.getJobNameFromListRow(1);
        Assert.assertTrue(nameInFirstRow.contains(newName),
                "الاسم الجديد لا يظهر في الجدول. المتوقع يحتوي: " + newName + "، الفعلي: " + nameInFirstRow);
    }

    /** Happy scenario: حذف أول صف → تأكيد → رسالة نجاح → كود الوظيفة يختفي من الجدول. */
    @Test(priority =5 ,description = "Delete: remove first row, confirm, verify success and code not in table")
    public void deleteJobPosition_RowRemovedAndSuccessToast() throws InterruptedException {
        String codeToDelete = page.getJobCodeFromListRow(1);
        page.clickDeleteButton(1)
                .clickConfirmDeleteButton()
                .waitForDeleteSuccess();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Assert.assertFalse(page.isJobCodeInTable(codeToDelete),
                "كود الوظيفة المحذوفة لا يزال يظهر في الجدول: " + codeToDelete);
    }
}
