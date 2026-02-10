package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class P03_JobPositionPage extends PageBase {

    private static final By PAGE_HEADER = By.xpath("//span[contains(text(),'كود الوظيفة')]");
    private static final By DATA_CELLS = By.xpath("//td[contains(@class,'ng-star-inserted')]");

    public P03_JobPositionPage(WebDriver driver) {
        super(driver);
    }

    /** Fluent: navigate to Job Position page */
    public P03_JobPositionPage navigateToPage() {
        driver.get("https://hrmicro.microtec-test.com/hr/employee-personnels/master-data/job-position");
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
