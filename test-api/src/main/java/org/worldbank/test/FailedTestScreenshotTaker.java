package org.worldbank.test;

import org.worldbank.models.Browser;
import org.worldbank.models.users.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FailedTestScreenshotTaker extends TestWatcher {

    private static final Logger logger = LogManager.getLogger(FailedTestScreenshotTaker.class);

    @Override
    public void starting(Description desc) {
    }

    @Override
    public void finished(Description desc) {
    }

    @Override
    public void failed(Throwable e, Description d) {
        try {
            TestContext testContext = TestContext.get();
            if (testContext != null) {
                for (Visitor visitor : testContext.getUsers()) {
                    Browser browser = visitor.browser();
                    if (browser != null) {
                        Calendar currentDate = Calendar.getInstance();
                        SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyyy HHmmss");
                        String dateNow = formatter.format(currentDate.getTime());

                        browser.takeScreenshot(d.getClassName() + " - " + d.getMethodName() + " - " + dateNow + " - " + visitor.getClass().getSimpleName());
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Exception on taking screenshot", ex);
        }
    }
}

