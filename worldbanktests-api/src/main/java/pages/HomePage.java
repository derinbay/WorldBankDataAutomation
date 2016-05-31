package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.worldbank.Config;
import org.worldbank.models.Page;
import org.worldbank.models.users.Visitor;

/**
 * Created by Taylan on 28.05.2016.
 */
public class HomePage extends Page<Visitor> {

    private final static String PAGE_URL = Config.websiteUrl;

    @FindBy(xpath = "//*[@id='hf_header_wrapper']//*[@class='_main_menu']//*[text()='Data']")
    private WebElement dataTab;

    public HomePage() {
         this.url = PAGE_URL;
    }

    public DataPage goToDataPage() {
        browser.loadElements();
        browser.clickTo(dataTab);

        DataPage dataPage = new DataPage();
        browser.changePage(dataPage);

        return dataPage;
    }
}
