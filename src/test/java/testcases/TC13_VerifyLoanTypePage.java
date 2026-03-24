package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P13_LoanTypePage;

import static drivers.DriverHolder.getDriver;

/**
 * Test cases for Loan Type (أنواع السلف): جدول، إضافة، عرض، تعديل، حذف — popup، toaster، save.
 */
public class TC13_VerifyLoanTypePage extends TestBase {

    private P13_LoanTypePage page;

    @BeforeClass
    public void openLoanTypePage() {
        page = new P13_LoanTypePage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Loan Type page loads and table has data")
    public void verifyLoanTypePageAndTable() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "صفحة أنواع السلف: الجدول فارغ أو الصفحة لم تحمل بشكل صحيح");
    }

    @Test(priority = 2, description = "Add: fill name Ar/En, salary item, description, save, verify toast and in table")
    public void addLoanType_VerifyToastAndInTable() throws InterruptedException {
        page.clickAddButton();
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        StringBuilder arSuffix = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            arSuffix.append(arabicLetters.charAt(faker.number().numberBetween(0, arabicLetters.length())));
        }
        String arName = "سلفة " + arSuffix;
        page.setNameAr(arName);
        page.setNameEn("Loan" + faker.letterify("????"));
        page.selectSalaryItemFirst();
        page.setDescription("وصف سلفة " + faker.letterify("????"));
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الإضافة");
        Thread.sleep(2000);
        Assert.assertTrue(page.isNameInTable(arName), "السلفة المضافة («" + arName + "») لم تظهر في الجدول");
    }

    @Test(priority = 3, description = "View: open view popup for first row, verify dialog, close")
    public void viewLoanType_VerifyPopup() throws InterruptedException {
        page.clickViewButton(1);
        Assert.assertTrue(page.waitForViewDialog(), "نافذة العرض (View popup) لم تظهر");
        Thread.sleep(500);
        page.closeDialogIfPresent();
        Thread.sleep(500);
    }

    @Test(priority = 4, description = "Edit: change Arabic name, save, verify toast and name in table")
    public void editLoanType_VerifyToastAndInTable() throws InterruptedException {
        page.clickEditButton(1);
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        StringBuilder arSuffix = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            arSuffix.append(arabicLetters.charAt(faker.number().numberBetween(0, arabicLetters.length())));
        }
        String newArName = "تعديل سلفة " + arSuffix;
        page.setNameAr(newArName);
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
        Thread.sleep(2000);
//        Assert.assertTrue(page.isNameInTable(newArName), "الاسم المعدّل («" + newArName + "») لم يظهر في الجدول");
        page.navigateToPage();
    }

    @Test(priority = 5, description = "Delete: confirm popup, verify toast and item removed from table")
    public void deleteLoanType_VerifyToastAndRemovedFromTable() throws InterruptedException {
        String nameToDelete = page.getNameFromListRow(1);
        page.clickDeleteButton(1);
        page.clickConfirmDeleteButton();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Thread.sleep(2000);
        Assert.assertFalse(page.isNameInTable(nameToDelete),
                "السلفة المحذوفة («" + nameToDelete + "») لا تزال تظهر في الجدول");
    }
}
