package org.worldbank.models;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.worldbank.Config;
import org.worldbank.models.browser.Browsers;
import org.worldbank.models.users.Visitor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class Browser {

    private static final Logger logger = LogManager.getLogger(Browser.class);

    protected WebDriver webDriver;
    protected HtmlUnitDriver unitDriver;
    private Page page;
    private Visitor visitor;

    public static Browser openThe(Page page, Set<Cookie> cookies) {
        return Browsers.runDefault().open(page, cookies);
    }

    public void resizeWindow(int x, int y) {
        webDriver.manage().window().setSize(new Dimension(x, y));
    }

    protected abstract void initInLocal();

    public final Browser byMe(Visitor visitor) {
        this.visitor = visitor;
        return this;
    }

    private Browser open(Page page, Set<Cookie> cookies) {
        initInLocal();

        this.page = page;
        this.page.setBrowser(this);
        webDriver.get(page.url());
        if (webDriver != unitDriver) {
            waitForAjax();
        }

        if (cookies != null) {
            cookies.forEach((cookie) -> webDriver.manage().addCookie(cookie));
        }

        return this;
    }

    public void takeScreenshot(String scrFilename) {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(
                OutputType.FILE);
        File directory = new File(Config.screenshotPath);

        if (!directory.exists())
            directory.mkdir();

        File outputFile = new File(Config.screenshotPath, scrFilename + ".png");

        try {
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public final Browser changePage(Page page) {
        this.page = page;
        this.page.setBrowser(this);
        return this;
    }

    public <P extends Page> Browser goTo(P page) {
        this.page = page;
        this.page.setBrowser(this);
        webDriver.get(page.url());
        waitForAjax();
        return this;
    }

    public void refresh() {
        webDriver.get(page.url());
        waitForAjax();
    }

    public void refreshCurrentUrl() {
        webDriver.get(currentURL());
        waitForAjax();
    }

    public void goBack() {
        webDriver.navigate().back();
        waitForAjax();
    }

    public void goForward() {
        webDriver.navigate().forward();
    }

    public final Page page() {
        return this.page;
    }

    public final Visitor visitor() {
        return this.visitor;
    }

    public void loadElements(Object webComponent) {
        PageFactory.initElements(webDriver, webComponent);
    }

    public void loadElements() {
        PageFactory.initElements(webDriver, page);
    }

    public String currentURL() {
        return webDriver.getCurrentUrl();
    }

    public void close() {
        try {
            webDriver.quit();
        } catch (Exception ex) {
            logger.error("Exception on closing webdriver", ex);
        }
    }

    public WebElement findElement(By by) {
        return findElement(by, null);
    }

    private WebElement findElement(By by, WebElement element) {
        if (element != null) {
            return element.findElement(by);
        } else {
            return webDriver.findElement(by);
        }
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    public void waitForAjax() {
        try {
            WebDriverWait myWait = new WebDriverWait(webDriver, 5);
            ExpectedCondition<Boolean> conditionToCheck = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver input) {
                    JavascriptExecutor jsDriver = (JavascriptExecutor) webDriver;
                    boolean stillRunningAjax = (Boolean) jsDriver
                            .executeScript("return window.jQuery != undefined && jQuery.active != 0");
                    return !stillRunningAjax;
                }
            };

            myWait.until(conditionToCheck);
        } catch (Throwable ex) {
            logger.warn("Ajax Waiting Time Expired");
        }
    }

    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
            logger.error("Exception on selenium waiting", ex);
        }
    }

    public void scrollTo(WebElement element) {
        scrollTo(element.getLocation().x, element.getLocation().y - 200);
    }

    public void scrollTo(int x, int y) {
        js("scrollTo(" + x + "," + y + ");");
    }

    public Object js(String script) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Object response = js.executeScript(script);
        waitForAjax();

        return response;
    }

    public void clickTo(By by) {
        clickTo(visibilityWaitInSafe(1, by));
    }

    public void clickTo(WebElement element) {
        try {
            scrollTo(element);
            element.click();
            waitForAjax();
        } catch (WebDriverException ex) {
            logger.warn("WebDriver exception: ", ex);
            if (ex.getMessage().contains("Other element would receive the click")) {
                WebElement popup = visibilityWaitInSafe(Config.WAITTIME_TOOSMALL, By.id("fsrOverlay"));
                if (popup != null) {
                    popup.findElement(By.xpath(".//*[@class='fsrCloseBtn']")).click();
                }

                element.click();
                waitForAjax();
            } else {
                throw ex;
            }
        }
    }

    public WebElement visibilityWait(int timeoutInSeconds, By by) {
        return new FluentWait<>(webDriver).
                withTimeout(timeoutInSeconds, TimeUnit.SECONDS).
                pollingEvery(500, TimeUnit.MILLISECONDS).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class).
                until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement visibilityWaitInSafe(int timeInSeconds, By by) {
        WebElement element = null;
        try {
            element = visibilityWait(timeInSeconds, by);
        } catch (TimeoutException ex) {
            logger.warn("Ignore TimeoutException: " + ex.getMessage());
        }
        return element;
    }

    public boolean isWebElementPresent(WebElement webElement) {
        try {
            webDriver.manage().timeouts().implicitlyWait(Config.WAITTIME_TIMEOUT, TimeUnit.MILLISECONDS);
            webElement.isDisplayed();
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
}
