package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P11_SalaryTemplatePage;

import static drivers.DriverHolder.getDriver;

/**
 * Test cases for Salary Template page (Add, Edit, Delete). تنقل مرة واحدة ثم تشغيل كل الكيسات بدون ريفريش.
 */
public class TC11_VerifySalaryTemplatePage extends TestBase {

    private P11_SalaryTemplatePage page;

    @BeforeClass
    public void openSalaryTemplatePage() {
        page = new P11_SalaryTemplatePage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 2, description = "Add: fill name Ar/En, description, payment frequency, save, verify success toast")
    public void addSalaryTemplate_VerifySuccessToast() {
        page.clickAddButton();
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        StringBuilder arSuffix = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            arSuffix.append(arabicLetters.charAt(faker.number().numberBetween(0, arabicLetters.length())));
        }
        page.setNameAr("نموذج راتب " + arSuffix);
        page.setNameEn("SalaryTemplate" + faker.letterify("????"));
        page.setDescription("وصف نموذج " + faker.letterify("????"));
        page.selectPaymentFrequencyFirst();
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الإضافة");
    }

    @Test(priority = 3, description = "Edit: change name Ar/En, description, payment frequency, save, verify success toast")
    public void editSalaryTemplate_VerifySuccessToast() {
        page.clickEditButton(1);
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        StringBuilder arSuffix = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            arSuffix.append(arabicLetters.charAt(faker.number().numberBetween(0, arabicLetters.length())));
        }
        page.setNameAr("تعديل نموذج " + arSuffix);
        page.setNameEn("EditTemplate" + faker.letterify("????"));
        page.setDescription("وصف تعديل " + faker.letterify("????"));
        page.selectPaymentFrequencyFirst();
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
    }

    @Test(priority = 4, description = "Delete: confirm popup, verify success toast and row removed")
    public void deleteSalaryTemplate_ConfirmAndVerify() throws InterruptedException {
        String nameToDelete = page.getTemplateNameFromListRow(1);
        page.clickDeleteButton(1);
        page.clickConfirmDeleteButton();
        page.waitForDeleteSuccess();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Assert.assertFalse(page.isNameInTable(nameToDelete),
                "النموذج المحذوف لا يزال يظهر في الجدول: " + nameToDelete);
    }
}
