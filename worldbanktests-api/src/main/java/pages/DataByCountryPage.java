package pages;

import org.openqa.selenium.By;
import org.worldbank.Config;
import org.worldbank.models.DataList;
import org.worldbank.models.Page;
import org.worldbank.models.users.Visitor;

/**
 * Created by Taylan on 29.05.2016.
 */
public class DataByCountryPage extends Page<Visitor> {

    private final static String PAGE_URL = Config.websiteSubUrl + "country";

    public DataByCountryPage() {
        this.url = PAGE_URL;
    }

    public DataList getCountries() {
        return new DataList(this, By.xpath("//*[@id='block-views-countries-block_1']//a"));
    }
}
