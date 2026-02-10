package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pages.PageBase;

import static drivers.DriverHolder.getDriver;

public class Listener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // MyScreenRecorder.startRecording(context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // MyScreenRecorder.stopRecording();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        snap(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        snap(result, "FAIL");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        snap(result, "SKIPPED");
    }

    private void snap(ITestResult result, String status) {
        try {
            WebDriver driver = getDriver();
            String name = result.getTestClass().getRealClass().getSimpleName()
                    + "_" + result.getMethod().getMethodName()
                    + "_" + status;
            PageBase.captureScreenshot(driver, name);
        } catch (Throwable t) {
        }
    }
}
