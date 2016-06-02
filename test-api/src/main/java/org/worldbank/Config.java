package org.worldbank;

import java.io.InputStream;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Config {

    public static final int WAITTIME_TIMEOUT = 60;
    public static final int WAITTIME_ELEMENTOCCURENCE = 15;
    public static final int WAITTIME_SMALL = 5;
    public static final int WAITTIME_TOOSMALL = 2;
    public static final String defaultBrowserName;
    public static final String websiteUrl;
    public static final String websiteSubUrl;
    public static final String screenshotPath;
    public static final Properties properties;
    public static final int failedTestRetryCount;
    private static final String environment;

    static {
        environment = getEnv();
        defaultBrowserName = getBrowser();
        properties = loadProperties(environment);
        screenshotPath = System.getProperty("user.dir") + "/screenshots/";
        failedTestRetryCount = getFailedTestRetryCount();
        websiteUrl = properties.getProperty("webdriver.websiteUrl");
        websiteSubUrl = properties.getProperty("webdriver.websiteSubUrl");
    }

    private static Properties loadProperties(String env) {
        String configFileName = env + "_config.properties";
        InputStream in = ClassLoader.getSystemResourceAsStream(configFileName);
        Properties properties = new Properties();

        try {
            properties.load(in);
        } catch (Exception e) {
            throw new IllegalStateException("Exception on loading {" + configFileName + "} conf file from classpath", e);
        }
        return properties;
    }

    private static String getEnv() {
        String env = System.getProperties().getProperty("selenium_env");

        if (isBlank(env)) {
            throw new IllegalArgumentException("No ENV option is set, please set -Dselenium_env in java");
        }

        return env;
    }

    private static String getBrowser() {
        String browser = System.getProperties().getProperty("browser");

        if (isBlank(browser)) {
            throw new IllegalArgumentException("No browser option is set, please set -Dbrowser in java");
        }

        return browser;
    }

    private static int getFailedTestRetryCount() {
        return Integer.parseInt(System.getProperties().getProperty("retry", "1"));
    }
}
