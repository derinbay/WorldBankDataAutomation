package pages;

import org.openqa.selenium.By;
import org.worldbank.Config;
import org.worldbank.models.DataList;

/**
 * Created by taylanderinbay on 1.06.2016.
 */
public class HICPage extends BaseWorldBankSubPage {

    final static String PAGE_URL = Config.websiteSubUrl + "income-level/HIC";

    public HICPage() {
        this.url = PAGE_URL;
    }

    public DataList getCountries() {
        return new DataList(this, By.xpath("//*[@id='block-views-income_levels_countries-block_1']//a"));
    }
}
