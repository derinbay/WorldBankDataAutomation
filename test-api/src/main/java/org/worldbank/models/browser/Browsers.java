package org.worldbank.models.browser;

import org.worldbank.models.Browser;
import org.worldbank.Config;

public class Browsers {

    public static String defaultBrowserName() {
        return Config.defaultBrowserName;
    }

    public static Browser run(BrowserType browserType) {
        Browser browser = null;

        switch (browserType) {
            case INTERNETEXPLORER:
                browser = new InternetExplorer();
                break;

            case HTMLUNIT:
                browser = new Unit();
                break;

            default:
            case FIREFOX:
                browser = new Firefox();
                break;
        }

        return browser;
    }

    public static Browser runDefault() {
        return run(BrowserType.byName(defaultBrowserName()));
    }
}
