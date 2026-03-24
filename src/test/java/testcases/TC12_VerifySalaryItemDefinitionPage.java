package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P12_SalaryItemDefinitionPage;

import static drivers.DriverHolder.getDriver;

/**
 * Test cases for Salary Item Definition. تنقل مرة واحدة ثم تشغيل الكيسات بدون ريفريش.
 */
public class TC12_VerifySalaryItemDefinitionPage extends TestBase {

    private P12_SalaryItemDefinitionPage page;

    @BeforeClass
    public void openSalaryItemDefinitionPage() {
        page = new P12_SalaryItemDefinitionPage(getDriver());
        page.navigateToPage();
    }

//    @Test(priority = 1, description = "Verify Salary Item Definition page loads and table has data")
//    public void verifySalaryItemDefinitionTableHasData() {
//        boolean hasData = page.verifyTableHasData();
//        Assert.assertTrue(hasData, "Salary Item Definition table is empty - no data found");
//    }

    @Test(priority = 2, description = "Add: add row, fill fields, save, verify toast and that item appears in table")
    public void addSalaryItem_VerifySuccessToastAndInTable() throws InterruptedException {
        Thread.sleep(3000);
        page.clickAddButton();
        Thread.sleep(2000);
        page.addNewLine();
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        StringBuilder arSuffix = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            arSuffix.append(arabicLetters.charAt(faker.number().numberBetween(0, arabicLetters.length())));
        }
        String arName = "بند " + arSuffix;
        page.fillAcademicField("اسم البند باللغة العربية", "", arName, "text");
        page.fillAcademicField("اسم البند باللغة الانجليزية", "", "Item" + faker.letterify("????"), "text");
        page.fillAcademicField("نوع بند الراتب", "", "", "dropdown");
        page.fillAcademicField("وحدة القياس الافتراضية", "", "", "dropdown");
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الإضافة");
        Thread.sleep(2000);
        Assert.assertTrue(page.isNameInTable(arName), "البند المضاف («" + arName + "») لم يظهر في الجدول");
    }

    @Test(priority = 3, description = "Edit: open first row, change Arabic name only, save, verify toast and name in table")
    public void editSalaryItem_ChangeArabicNameOnly() throws InterruptedException {
        Thread.sleep(1500);
        page.clickEditButton(1);
        Thread.sleep(1000);
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        StringBuilder arSuffix = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            arSuffix.append(arabicLetters.charAt(faker.number().numberBetween(0, arabicLetters.length())));
        }
        String newArName = "تعديل " + arSuffix;
        page.fillAcademicField("اسم البند باللغة العربية", "", newArName, "text");
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
        Thread.sleep(2000);
        Assert.assertTrue(page.isNameInTable(newArName), "الاسم المعدّل («" + newArName + "») لم يظهر في الجدول");
    }

    @Test(priority = 4, description = "Delete: click delete on first row, confirm, verify toast and item removed from table")
    public void deleteSalaryItem_VerifyToastAndRemovedFromTable() throws InterruptedException {
        Thread.sleep(1500);
        String nameToDelete = page.getTemplateNameFromListRow(1);
        page.clickDeleteButton(1);
        page.clickConfirmDeleteButton();
        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Thread.sleep(2000);
        Assert.assertFalse(page.isNameInTable(nameToDelete), "البند المحذوف («" + nameToDelete + "») لا يزال يظهر في الجدول");
    }
}
