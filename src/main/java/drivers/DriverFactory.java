package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver getNewInstance(String browserName) {
        ChromeOptions chromeOptions;
        DesiredCapabilities capabilities;
        Map<String, Object> prefs;
        ThreadLocal<RemoteWebDriver> driver = null;
        switch (browserName.toLowerCase()) {
            case "grid":
                driver = new ThreadLocal<>();
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("browserName", "firefox");
                try {
                    driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                return driver.get();
            case "chrome-headless":
                chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--no-proxy-server");
                chromeOptions.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(chromeOptions);
            case "firefox":
                return new FirefoxDriver();
            case "chrome-mobile":
                chromeOptions = new ChromeOptions();

                Map<String, Object> deviceMetrics = new HashMap<>();
                deviceMetrics.put("width", 375);
                deviceMetrics.put("height", 812);
                deviceMetrics.put("pixelRatio", 3.0);

                Map<String, Object> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceMetrics", deviceMetrics);
                mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)");

                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");

                return new ChromeDriver(chromeOptions);
            case "chrome-slow":
                chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");

                ChromeDriver slowDriver = new ChromeDriver(chromeOptions);

                // ✅ تفعيل Slow Network باستخدام CDP
                Map<String, Object> networkConditions = new HashMap<>();
                networkConditions.put("offline", false);
                networkConditions.put("latency", 300); // 300ms delay
                networkConditions.put("downloadThroughput", 500 * 1024 / 8); // 500 kbps
                networkConditions.put("uploadThroughput", 500 * 1024 / 8);

                slowDriver.executeCdpCommand("Network.enable", new HashMap<>());
                slowDriver.executeCdpCommand("Network.emulateNetworkConditions", networkConditions);

                System.out.println("🌐 Running Chrome with Slow Network emulation (CDP)");
                return slowDriver;
            case "firefox-headless":
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                firefoxBinary.addCommandLineOptions("--window-size=1280x720");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Users\\moaaz\\Downloads\\edgedriver_win32\\msedgedriver.exe");
                return new EdgeDriver();
            default:
                chromeOptions = new ChromeOptions();
                // TODO: handle browsers options
                prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_setting_values.notifications", 2);
                // Disable Chrome automation detection
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                // Images enabled (removed imagesEnabled=false to allow images to load)
                // Optionally add more obfuscation, like custom user agent
                chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                chromeOptions.addArguments("start-maximized");
                // Removed --incognito to allow cookies for proper session handling
                // Removed --disable-web-security to allow proper cookie handling
                chromeOptions.addArguments("--no-proxy-server");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-notifications");
                // Enable cookies and allow third-party cookies
                prefs.put("profile.default_content_setting_values.cookies", 1);
                prefs.put("profile.block_third_party_cookies", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                capabilities = new DesiredCapabilities();
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                chromeOptions.merge(capabilities);
                return new ChromeDriver(chromeOptions);
        }
    }


}