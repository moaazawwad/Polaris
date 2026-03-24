package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static drivers.DriverHolder.getDriver;

public class PageBase {

    WebDriver driver;

    // TODO: constructor to intailize webdriver
    public PageBase(WebDriver driver){

        this.driver=driver;
    }

    public static void hoverWebElement(WebDriver driver, WebElement element) {
        //Creating object of an Actions class
        Actions action = new Actions(getDriver());
        action.moveToElement(element).perform();
    }

    public static WebDriverWait longWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public static WebDriverWait shortWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // TODO: clear all browser data after each test
    public static void quitBrowser(WebDriver driver) {
        // clear browser localStorage , sessionStorage and delete All Cookies
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
        ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
        driver.manage().deleteAllCookies();
        driver.quit();
        // kill browser process on background
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("pkill -f chromedriver");
                Runtime.getRuntime().exec("pkill -f chrome");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // TODO: Capture Screenshot
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        try {
            // 1. حفظ الصورة كملف فعلي في resources/Screenshots
            File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String filePath = System.getProperty("user.dir") +
                    "/src/test/resources/Screenshots/" + screenshotName + "_" + System.currentTimeMillis() + ".png";
            FileHandler.copy(screenshotFile, new File(filePath));

            // 2. ربط الصورة بـ Allure كـ byte[]
            byte[] screenshotBytes = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshotBytes));

        } catch (WebDriverException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * فتح dropdown (p-dropdown) بالـ data-testid واختيار خيار يحتوي على النص المعطى.
     * مثال: openDropdownAndSelectOptionByText("loanSource", "من طلب");
     */
    protected void openDropdownAndSelectOptionByText(String dataTestId, String optionText) {
        WebDriverWait wait = shortWait(driver);
        By dropdownLocator = By.cssSelector("p-dropdown[data-testid='" + dataTestId + "']");
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        List<WebElement> triggerButtons = dropdown.findElements(By.cssSelector(".p-dropdown-trigger"));
        WebElement trigger = triggerButtons.isEmpty()
                ? dropdown.findElement(By.cssSelector("[role='combobox']"))
                : triggerButtons.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(trigger)).click();
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[role='listbox'], .p-dropdown-panel")));
        By optionByText = By.xpath("//ul[@role='listbox']//li[contains(.,'" + optionText.replace("'", "''") + "')]");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionByText));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
    }

    /**
     * فتح dropdown (p-dropdown) بالـ data-testid واختيار الخيار حسب الفهرس (0 = أول خيار، 1 = ثاني، ...).
     * مثال: openDropdownAndSelectOptionByIndex("loanTypeId", 0);
     */
    protected void openDropdownAndSelectOptionByIndex(String dataTestId, int index) {
        WebDriverWait wait = shortWait(driver);
        By dropdownLocator = By.cssSelector("p-dropdown[data-testid='" + dataTestId + "']");
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        List<WebElement> triggerButtons = dropdown.findElements(By.cssSelector(".p-dropdown-trigger"));
        WebElement trigger = triggerButtons.isEmpty()
                ? dropdown.findElement(By.cssSelector("[role='combobox']"))
                : triggerButtons.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(trigger)).click();
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[role='listbox'], .p-dropdown-panel")));
        By optionByIndex = By.xpath("(//ul[@role='listbox']//li)[" + (index + 1) + "]");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionByIndex));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
    }
}
