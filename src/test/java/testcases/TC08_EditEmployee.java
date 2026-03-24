package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P02_EmployeesDataPage;
import pages.P08_EditEmployeePage;

import static drivers.DriverHolder.getDriver;

public class TC08_EditEmployee extends TestBase {

    @Test(description = "Edit employee and verify success toast appears")
    public void testEditEmployeeWithRequiredData_P() throws InterruptedException {

        P02_EmployeesDataPage listPage = new P02_EmployeesDataPage(getDriver());
        listPage.navigateToPage().clickEditButton();

        P08_EditEmployeePage editPage = new P08_EditEmployeePage(getDriver());
        Thread.sleep(4000);

        editPage.setEmployeeNameAr("تم تعديل الاسم")
                .clickSave();

        Assert.assertTrue(editPage.waitForSuccessToast(), "رسالة النجاح (تم بنجاح) لم تظهر بعد الحفظ");
    }
}
