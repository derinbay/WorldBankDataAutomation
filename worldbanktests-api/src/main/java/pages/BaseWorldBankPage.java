package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.worldbank.models.Page;
import org.worldbank.models.users.Visitor;

/**
 * Created by taylanderinbay on 1.06.2016.
 */
public class BaseWorldBankPage extends Page<Visitor> {

    @FindBy(xpath = "//*[@id='hf_header_wrapper']//*[@class='_main_menu']//*[text()='Home']")
    private WebElement homeTab;

    @FindBy(xpath = "//*[@id='hf_header_wrapper']//*[@class='_main_menu']//*[text()='Data']")
    private WebElement dataTab;

    public DataPage goToDataPage() {
        browser.loadElements();
        browser.clickTo(dataTab);

        DataPage dataPage = new DataPage();
        browser.changePage(dataPage);

        return dataPage;
    }
}
