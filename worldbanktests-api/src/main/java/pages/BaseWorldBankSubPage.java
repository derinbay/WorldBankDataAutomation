package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.worldbank.models.Page;
import org.worldbank.models.users.Visitor;

/**
 * Created by taylanderinbay on 1.06.2016.
 */
public class BaseWorldBankSubPage extends Page<Visitor> {

    @FindBy(xpath = "//*[contains(@class, 'global-nav')]//a[text()='Home']")
    private WebElement homeTab;

    public HomePage goToHomePage() {
        browser.loadElements(this);
        browser.clickTo(homeTab);

        HomePage homePage = new HomePage();
        browser.changePage(homePage);

        return homePage;
    }
}
