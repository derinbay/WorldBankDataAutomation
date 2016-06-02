package org.worldbank.models.users;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.worldbank.URLUtil;
import org.worldbank.models.*;
import org.worldbank.test.TestContext;

import java.util.Set;

public class Visitor<V extends Visitor> implements Account<VisitorPool> {

    protected Browser browser;
    protected V origin;
    private VisitorPool<V> pool;

    public static Visitor aVisitor() {
        Visitor visitor = new Visitor();
        TestContext.get().addVisitor(visitor);

        return visitor;
    }

    @Override
    public void setPool(VisitorPool pool) {
        this.pool = pool;
    }

    public <P extends Page> V open(P page) {
        return open(page, null);
    }

    public <P extends Page> V open(P page, Set<Cookie> cookies) {
        this.browser = Browser.openThe(page, cookies).byMe(this);
        this.origin().browser = this.browser;
        return (V) this;
    }

    public <P extends Page<V>> V goTo(P page) {
        this.browser.goTo(page);
        return (V) this;
    }

    public V changePage(Page page) {
        this.browser().changePage(page);
        return (V) this;
    }

    public V goBack() {
        this.browser().goBack();
        return (V) this;
    }

    public V goForward() {
        this.browser().goForward();
        return (V) this;
    }

    public V refresh() {
        this.browser.refresh();
        return (V) this;
    }

    public V refreshCurrentUrl() {
        this.browser.refreshCurrentUrl();
        return (V) this;
    }

    public Browser browser() {
        return browser;
    }

    public Page nowLookingAt() {
        return browser.page();
    }

    public void closeBrowser() {
        browser.close();
    }

    public V click(WebElement webElement) {
        browser.clickTo(webElement);
        return (V) this;
    }

    public V origin() {
        if (origin == null) {
            return (V) this;
        }
        V orrigin = (V) origin.origin();
        return orrigin;
    }

    @Override
    public void free() {
        V theOrigin = this.origin();
        if (this.pool == null) {
            throw new IllegalStateException("VisitorPool must be set to Visitor on adding pool.");
        }
        this.pool.free(theOrigin);
    }

    public void loadElements() {
        browser.loadElements();
    }

    public boolean isRedirectedTo(Page page) {
        return URLUtil.matches(browser.currentURL(), page.url());
    }
}