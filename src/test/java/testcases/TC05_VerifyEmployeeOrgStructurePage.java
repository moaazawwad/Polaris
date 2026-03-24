package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.P05_EmployeeOrgStructurePage;

import static drivers.DriverHolder.getDriver;

public class TC05_VerifyEmployeeOrgStructurePage extends TestBase {

    private P05_EmployeeOrgStructurePage page;

    @BeforeClass
    public void openOrgStructurePage() {
        page = new P05_EmployeeOrgStructurePage(getDriver());
        page.navigateToPage();
    }

    @Test(priority = 1, description = "Verify Employee Org Structure page loads and table has data")
    public void verifyEmployeeOrgStructureTableHasData() {
        boolean hasData = page.verifyTableHasData();
        Assert.assertTrue(hasData, "Employee Org Structure table is empty - no data found");
    }

    /** View: كود الهيكل من أول صف في الجدول = "كود الهيكل" في صفحة العرض، ثم عودة. */
    @Test(priority = 2, description = "View: structure code on list matches كود الهيكل on view page, then back")
    public void viewOrgStructure_CodeMatchesThenBack() throws InterruptedException {
        String codeOnList = page.getStructureCodeFromListRow(1);
        System.out.println(" codeOnList " + codeOnList);
        page.clickViewButton(1);
        Thread.sleep(2000);
        String structureCodeOnView = page.getStructureCodeFromViewPage();
        System.out.println(" structureCodeOnView " + structureCodeOnView);

        Assert.assertEquals(structureCodeOnView, codeOnList, "كود الهيكل في صفحة العرض لا يطابق الكود في القائمة");
        page.clickBackButton();
    }

    /** Edit: تعديل الاسم → حفظ → رسالة نجاح والاسم الجديد يظهر في الجدول. */
    @Test(priority = 3, description = "Edit: change structure name, save, verify success toast and new name in table")
    public void editOrgStructure_NameUpdatedAndSuccessToast() {
        String newName = "هيكل تجريبي " + faker.number().digits(4);
        System.out.println("newName " + newName);
        page.clickEditButton(1)
                .setStructureName(newName)
                .clickSave();

        Assert.assertTrue(page.waitForSuccessToastAfterEdit(), "رسالة النجاح (تم بنجاح) لم تظهر بعد التعديل");
        String nameInFirstRow = page.getUnitNameFromListRow(1);
        System.out.println("nameInFirstRow " + nameInFirstRow);

        Assert.assertTrue(nameInFirstRow.contains(newName),
                "الاسم الجديد لا يظهر في الجدول. المتوقع يحتوي: " + newName + "، الفعلي: " + nameInFirstRow);
    }

    /** Add: إضافة → اختيار قسم (شركة) → اسم عشوائي → المدير المسؤول أول عنصر → حفظ → رسالة النجاح. */
    @Test(priority = 4, description = "Add: add under first section, random name, select first manager, save, verify success toast")
    public void addOrgStructure_SelectFirstSectionClosed() {
        String newName = "هيكل تجريبي " + faker.number().digits(4);
        page.clickAddButton();
        page.clickTreeAddIconButton();
        page.clickFirstSectionWhileCollapsed();
        page.setStructureName(newName);
        page.selectResponsibleEmployeeFirst();
        page.clickSave();

        Assert.assertTrue(page.waitForSuccessToastAfterAdd(), "رسالة النجاح (تمت إضافة القسم بنجاح) لم تظهر بعد الإضافة");
    }

    /** Delete: حذف أول صف → تأكيد الـ Popup → رسالة النجاح. */
    @Test(priority = 5, description = "Delete: remove first row, confirm popup, verify success toast")
    public void deleteOrgStructure_ConfirmAndSuccessToast() throws InterruptedException {
        String unitToDelete = page.getUnitNameFromListRow(1);
        page.clickDeleteButton(1)
                .clickConfirmDeleteButton()
                .waitForDeleteSuccess();

        Assert.assertTrue(page.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحذف");
        Assert.assertFalse(page.isUnitNameInTable(unitToDelete),
                "الوحدة المحذوفة لا تزال تظهر في الجدول: " + unitToDelete);
    }
}
