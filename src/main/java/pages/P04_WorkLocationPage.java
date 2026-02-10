package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class P04_WorkLocationPage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'رمز منطقة العمل')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");

    public P04_WorkLocationPage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Work Location page */
    public P04_WorkLocationPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/work-location");
        longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
        return this;
    }

    public boolean verifyTableHasData() {
        try {
            longWait(driver).until(ExpectedConditions.presenceOfElementLocated(PAGE_HEADER));
            List<WebElement> cells = driver.findElements(DATA_CELLS);
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (!text.isEmpty() && text.contains("HR-")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
