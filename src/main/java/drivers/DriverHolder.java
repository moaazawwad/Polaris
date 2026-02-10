package drivers;

import org.openqa.selenium.WebDriver;

public final class DriverHolder {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverHolder() {}

    public static WebDriver getDriver() {
        WebDriver d = DRIVER.get();
        if (d == null) {
            throw new IllegalStateException("WebDriver not set for current thread. Did you call setDriver() in @BeforeMethod?");
        }
        return d;
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void unload() {
        DRIVER.remove();
    }
}
