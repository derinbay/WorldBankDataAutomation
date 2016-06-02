package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.worldbank.Config;

/**
 * Created by Taylan on 28.05.2016.
 */
public class DataPage extends BaseWorldBankSubPage {

    final static String PAGE_URL = Config.websiteSubUrl;

    @FindBy(xpath = "//*[@id='navigation']//*[contains(@class, 'country')]")
    private WebElement countryFilter;

    public DataPage() {
        this.url = PAGE_URL;
    }

    public DataByCountryPage goToDataByCountryPage() {
        browser.loadElements();
        browser.clickTo(countryFilter);

        DataByCountryPage dataByCountryPage = new DataByCountryPage();
        browser.changePage(dataByCountryPage);

        return dataByCountryPage;
    }
}
