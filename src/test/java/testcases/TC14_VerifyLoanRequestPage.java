package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P14_LoanRequestPage;

import static drivers.DriverHolder.getDriver;

/**
 * طلبات السلف (Loan Request): تحميل الجدول، إضافة، عرض، تعديل، حذف — toaster، confirmation popup.
 */
public class TC14_VerifyLoanRequestPage extends TestBase {

    private P14_LoanRequestPage page;

    @BeforeClass
    public void openLoanRequestPage() {
        page = new P14_LoanRequestPage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Loan Request page loads and table has data")
    public void verifyLoanRequestPageAndTable() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "صفحة طلبات السلف: الجدول فارغ أو الصفحة لم تحمل");
    }

    @Test(priority = 2, description = "Add: fill form (loan type, employee, date, amount, installments), save, verify toast")
    public void addLoanRequest_VerifyToast() throws InterruptedException {
        page.clickAddButton();
        page.fillAddForm();
        page.clickSave();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد إضافة طلب السلف");
        Thread.sleep(1000);
        page.navigateToPage();
    }

    @Test(priority = 3, description = "View: open view for first row, verify dialog, close")
    public void viewLoanRequest_VerifyPopup() throws InterruptedException {
        page.clickViewButton(1);
        Assert.assertTrue(page.waitForViewDialog(), "نافذة العرض لم تظهر");
        Thread.sleep(500);
        page.closeDialogIfPresent();
        Thread.sleep(500);
    }

    @Test(priority = 4, description = "Edit: open first row, save, verify toast and return to list")
    public void editLoanRequest_VerifyToast() throws InterruptedException {
        page.clickEditButton(1);
        Thread.sleep(1500);
        page.clickSave();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد التعديل");
        Thread.sleep(2000);
        page.navigateToPage();
    }

    @Test(priority = 5, description = "Delete: confirm popup, verify toast and row removed")
    public void deleteLoanRequest_VerifyToastAndRemoved() throws InterruptedException {
        String nameToDelete = page.getNameFromListRow(1);
        page.clickDeleteButton(1);
        page.clickConfirmDeleteButton();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح لم تظهر بعد الحذف");
        Thread.sleep(2000);
        Assert.assertFalse(page.isNameInTable(nameToDelete), "طلب السلف المحذوف لا يزال يظهر: " + nameToDelete);
    }
}
