package org.worldbank.models.browser;

import org.worldbank.models.Browser;
import org.worldbank.Config;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class InternetExplorer extends Browser {

    @Override
    protected void initInLocal() {
        String fileLocation = ClassLoader.getSystemResource("IEDriverServer.exe").getFile();
        System.setProperty("webdriver.ie.driver", fileLocation);
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        capability.setBrowserName("internet explorer");
        capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        webDriver = new InternetExplorerDriver(capability);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
