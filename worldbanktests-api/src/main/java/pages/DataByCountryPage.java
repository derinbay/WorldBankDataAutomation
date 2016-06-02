package pages;

import org.openqa.selenium.By;
import org.worldbank.Config;

/**
 * Created by Taylan on 29.05.2016.
 */
public class DataByCountryPage extends BaseWorldBankSubPage {

    private final static String PAGE_URL = Config.websiteSubUrl + "country";

    public DataByCountryPage() {
        this.url = PAGE_URL;
    }

    public HICPage goToHICPage() {
        browser.clickTo(By.linkText("High income"));

        HICPage hicPage = new HICPage();
        browser.changePage(hicPage);

        return hicPage;
    }
}
