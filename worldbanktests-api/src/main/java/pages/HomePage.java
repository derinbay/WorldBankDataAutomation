package pages;

import org.worldbank.Config;

/**
 * Created by Taylan on 28.05.2016.
 */
public class HomePage extends BaseWorldBankPage {

    private final static String PAGE_URL = Config.websiteUrl;

    public HomePage() {
         this.url = PAGE_URL;
    }
}
