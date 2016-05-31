package org.worldbank.models.browser;

import org.worldbank.models.Browser;
import org.worldbank.Config;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Firefox extends Browser {

    @Override
    protected void initInLocal() {
        webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
    }
}