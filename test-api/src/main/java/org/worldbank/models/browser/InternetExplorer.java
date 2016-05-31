package org.worldbank.models.browser;

import org.worldbank.models.Browser;
import org.worldbank.Config;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class InternetExplorer extends Browser {

    @Override
    protected void initInLocal() {
        webDriver = new InternetExplorerDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
