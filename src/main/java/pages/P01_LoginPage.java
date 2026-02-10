package pages;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.Utility;

import java.io.IOException;
import java.util.List;

public class P01_LoginPage extends PageBase {

    // Locators (best practice: use stable IDs)
    private static final By EMAIL_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By SIGN_IN_BUTTON = By.id("kc-login");

    // Test data path (email & password in JSON)
    private static final String CREDENTIALS_PATH = System.getProperty("user.dir")
            + "/src/test/resources/data/credentials.json";

    public P01_LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Fluent: type in email field */
    public P01_LoginPage typeEmail(String email) {
        shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
        driver.findElement(EMAIL_INPUT).clear();
        driver.findElement(EMAIL_INPUT).sendKeys(email);
        return this;
    }

    /** Fluent: type in password field */
    public P01_LoginPage typePassword(String password) {
        driver.findElement(PASSWORD_INPUT).clear();
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        return this;
    }

    /** Fluent: click Sign In button */
    public P01_LoginPage clickSignIn() {
        driver.findElement(SIGN_IN_BUTTON).click();
        return this;
    }

    /**
     * Login to HR system
     */
    public void loginWithDefaultUser() {
        try {
            String email = (String) Utility.getSingleJsonData(CREDENTIALS_PATH, "email");
            String password = (String) Utility.getSingleJsonData(CREDENTIALS_PATH, "password");

            typeEmail(email).typePassword(password).clickSignIn();
            Thread.sleep(2000);
            // Wait for authentication
            longWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlContains("login-actions")));
            driver.get("https://hrmicro.microtec-test.com/hr/");
            Thread.sleep(7000);

            longWait(driver).until(ExpectedConditions.urlContains("hrmicro"));
            longWait(driver).until(d -> "complete".equals(((org.openqa.selenium.JavascriptExecutor) d).executeScript("return document.readyState")));

            // Ensure language is Arabic
            ensureArabicLanguage();

        } catch (IOException | ParseException e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check language and switch to Arabic if needed
     */
    private void ensureArabicLanguage() {
        try {
            // Find all profile buttons (might be 2, take the second one if exists)
            By profileButton = By.cssSelector("button.btn_profile");
            List<WebElement> profileButtons = driver.findElements(profileButton);
            
            if (profileButtons.isEmpty()) {
                return; // Profile button not found
            }

            // Take the second one if exists, otherwise take the first
            WebElement profileBtn = profileButtons.size() > 1 ? profileButtons.get(1) : profileButtons.get(0);
            
            shortWait(driver).until(ExpectedConditions.elementToBeClickable(profileBtn));
            profileBtn.click();
            By languageSection = By.xpath("//button[@class='profile_link']//p[contains(@class,'title') and (text()='اللغة' or text()='Language')]");
            shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(languageSection));

            // Find all language sections (might be 2, take the second one if exists)
            List<WebElement> languageSections = driver.findElements(languageSection);

            if (languageSections.isEmpty()) {
                return; // Language section not found, skip
            }

            // Take the second one if exists, otherwise take the first
            WebElement languageSectionElement = languageSections.size() > 1 ? languageSections.get(1) : languageSections.get(0);
            String languageText = languageSectionElement.getText().trim();

            // If language is "Language" (English), switch to Arabic
            if ("Language".equals(languageText)) {
                By langLinkButton = By.xpath("//button[@class='profile_link']//button[@class='lang_link']");
                shortWait(driver).until(ExpectedConditions.elementToBeClickable(langLinkButton));
                driver.findElement(langLinkButton).click();
                shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='profile_link']//p[contains(@class,'title') and text()='اللغة']")));
            }

        } catch (Exception e) {
            // If language check fails, continue anyway (not critical)
            System.out.println("⚠️ Could not verify/change language: " + e.getMessage());
        }
    }
}
