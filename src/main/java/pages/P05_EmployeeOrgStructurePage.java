package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class P05_EmployeeOrgStructurePage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'الوحدة')]");
    private static final By DATA_CELLS = By.cssSelector("div.nameAndCodeDisplay");

    public P05_EmployeeOrgStructurePage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Employee Org Structure page */
    public P05_EmployeeOrgStructurePage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/employee-org-structure");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (!text.isEmpty() && (text.contains("HR-") || text.contains("DEV"))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
