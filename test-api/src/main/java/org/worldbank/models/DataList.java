package org.worldbank.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DataList extends WebComponent {

    protected WebComponent container;
    protected By by;
    protected List<WebElement> elements;
    private int selectedIndex;

    public DataList(WebComponent container, By by) {
        this.browser = container.browser();
        this.container = container;
        this.by = by;

        elements = container.browser.findElements(by);
    }

    public void refresh() {
        elements = browser.findElements(by);
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

    public int size() {
        return elements.size();
    }

    public List<String> getElementTexts() {
        List<String> elementTexts = new ArrayList<>();
        for (WebElement element : elements) {
            elementTexts.add(element.getText());
        }
        return elementTexts;
    }
}
