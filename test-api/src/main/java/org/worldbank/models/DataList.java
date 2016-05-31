package org.worldbank.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DataList extends WebComponent {

    private static final Logger logger = LogManager.getLogger(DataList.class);

    protected WebComponent container;
    protected By by;
    protected String xpath;
    protected List<WebElement> elements;
    private int selectedIndex;

    public DataList() {}

    public DataList(WebComponent container, By by) {
        this.browser = container.browser();
        this.container = container;
        this.by = by;

        elements = container.browser.findElements(by);
    }

    public void refresh() {
        elements = browser.findElements(by);
    }

    public int size() {
        return elements.size();
    }

    public WebElement getSelected() {
        return elements.get(selectedIndex);
    }

    public List<WebElement> getElements() {
        return elements;
    }

    public void click() {
        WebElement link = elements.get(selectedIndex).findElement(By.tagName("a"));
        browser.clickTo(link);
    }

    public WebElement findRowContains(String keyword) {
        for (WebElement element : elements) {
            if (element.getText().contains(keyword)) {
                return element;
            }
        }
        return null;
    }

    public List<String> getElementTexts() {
        List<String> elementTexts = new ArrayList<>();
        for (WebElement element : elements) {
            elementTexts.add(element.getText());
        }
        return elementTexts;
    }
}
