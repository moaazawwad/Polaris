package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P15_EmployeeLoanPage;

import static drivers.DriverHolder.getDriver;

/**
 * سلف الموظفين (Employee Loan): جدول، إضافة (مباشر)، إضافة (من طلب)، عرض، تعديل، حذف — toaster، confirmation popup.
 */
public class TC15_VerifyEmployeeLoanPage extends TestBase {

    private P15_EmployeeLoanPage page;

    @BeforeClass
    public void openEmployeeLoanPage() {
        page = new P15_EmployeeLoanPage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Employee Loan page loads and table has data")
    public void verifyEmployeeLoanPageAndTable() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "صفحة سلف الموظفين: الجدول فارغ أو الصفحة لم تحمل");
    }
//
//    @Test(priority = 2, description = "Add (مباشر): كل خطوة تسلم اللي بعدها ثم حفظ")
//    public void addEmployeeLoan_Direct_VerifyToast() throws InterruptedException {
//        page.clickAddButton();
//        Thread.sleep(2000);
//        page.selectLoanSourceDirect()
//                .selectLoanTypeFirst()
//                .selectEmployeeFirst()
//                .setLoanDateToday()
//                .setNotes("ملاحظات سلف مباشر أي حاجة")
//                .setLoanAmount(200)
//                .setInstallmentAmount(100)
//                .setFirstInstallmentDueDateToday()
//                .clickSave();
//        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد إضافة سلف مباشر");
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 3, description = "Add (من طلب): كل خطوة تسلم اللي بعدها ثم حفظ")
//    public void addEmployeeLoan_FromRequest_VerifyToast() throws InterruptedException {
//        page.clickAddButton();
//        Thread.sleep(2000);
//        page.selectLoanSourceFromRequest()
//                .selectLoanRequestFirst()
////                .selectLoanTypeFirst()
////                .selectEmployeeFirst()
////                .setLoanDateToday()
////                .setNotes("ملاحظات سلف من طلب أي حاجة")
//                .clickSave();
//        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد إضافة سلف من طلب");
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 4, description = "View: open view for first row, verify dialog, close")
//    public void viewEmployeeLoan_VerifyPopup() throws InterruptedException {
//        page.clickViewButton(1);
//        Assert.assertTrue(page.waitForViewDialog(), "نافذة العرض لم تظهر");
//        Thread.sleep(500);
//        page.closeDialogIfPresent();
//        Thread.sleep(500);
//    }
//
//    @Test(priority = 5, description = "Edit: open first row, اختيار مباشر بدل من طلب، حفظ، التحقق من الـ toast")
//    public void editEmployeeLoan_VerifyToast() throws InterruptedException {
//        page.clickEditButton(1);
//        Thread.sleep(2000);
//        page.selectLoanSourceDirect();
//        Thread.sleep(2000);
//        page.clickSave();
//        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد التعديل");
//        Thread.sleep(2000);
//    }

    @Test(priority = 6, description = "Delete: confirm popup, verify toast and row removed")
    public void deleteEmployeeLoan_VerifyToastAndRemoved() throws InterruptedException {
        String nameToDelete = page.getNameFromListRow(1);
        page.clickDeleteButton(1);
        page.clickConfirmDeleteButton();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد الحذف");
        Thread.sleep(2000);
        Assert.assertFalse(page.isNameInTable(nameToDelete), "سلف الموظف المحذوفة لا تزال تظهر: " + nameToDelete);
    }
}
