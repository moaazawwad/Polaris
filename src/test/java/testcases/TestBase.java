package testcases;

import com.github.javafaker.Faker;
import common.MyScreenRecorder;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import util.AllureManager; // استيراد مدير الأرشفة

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static drivers.DriverHolder.getDriver;
import static drivers.DriverHolder.setDriver;

public class TestBase {

    protected Faker faker = new Faker();
    protected static Properties prop;
    private static FileInputStream readProperty;
    private String currentRunTimestamp;

    @BeforeSuite
    public void beforeSuite() throws Exception {
        // 1. أرشفة أي نتائج قديمة موجودة قبل بدء التست الجديد
        AllureManager.archivePreviousRun();

        // 2. تجهيز الـ Timestamp للـ Run الحالي
        currentRunTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        // 3. بدء تسجيل الشاشة
        MyScreenRecorder.startRecording("hr-polaris-rec");

        // 4. تحميل إعدادات المشروع (URL, etc.)
        setProjectDetails();
    }

    private void setProjectDetails() throws IOException {
        readProperty = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/properties/environment.properties");
        prop = new Properties();
        prop.load(readProperty);
    }

    @Parameters({"browser"})
    @BeforeTest
    public void openBrowser(@Optional String browser) throws AWTException, InterruptedException {
        // تشغيل المتصفح
        setDriver(DriverFactory.getNewInstance(browser));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        getDriver().get(prop.getProperty("url"));

        // تسجيل الدخول مرة واحدة لكل التستات في هذا الكلاس
        pages.P01_LoginPage loginPage = new pages.P01_LoginPage(getDriver());
        loginPage.loginWithDefaultUser();
    }

    @AfterTest
    public void tearDown() {
        try {
            if (getDriver() != null) {
//                getDriver().quit();
            }
        } catch (Exception ignore) {
            // تجاهل أي خطأ عند القفل
        } finally {
            drivers.DriverHolder.unload();
        }
    }

    @AfterSuite
    public void afterSuite() throws Exception {
        // 1. أرشفة نتائج الـ Run اللي لسه مخلص حالا بتوقيته
        AllureManager.archiveCurrentRun(currentRunTimestamp);

        // 2. إيقاف تسجيل الشاشة
        MyScreenRecorder.stopRecording();
    }
}