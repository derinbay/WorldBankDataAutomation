package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.worldbank.models.CountryData;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * Created by Taylan on 1.06.2016.
 */
public class CountryPages extends BaseWorldBankSubPage {

    private static final Logger logger = getLogger(CountryPages.class);

    @FindBy(xpath = "//*[contains(text(), 'GDP at market prices')]/../../../following-sibling::div//*[@class='human-readable']")
    private WebElement gdpElement;

    @FindBy(xpath = "//*[contains(text(), 'Population, total')]/../../../following-sibling::div//*[@class='human-readable']")
    private WebElement populationElement;

    @FindBy(xpath = "//*[contains(text(), 'CO2 emissions')]/../following-sibling::div//*[contains(@class, 'wbapi-data-value')]//*[@class='field-content']")
    private WebElement co2Element;

    public CountryData getPageValues(String countryName) {
        String gdp = "-", population = "-", co2 = "-";
        browser.loadElements();

        if (browser.isWebElementPresent(gdpElement)) {
            gdp = gdpElement.getText();
            logger.info("GDP at market prices (current US$) for country {" + countryName + "} is " + gdp);
        }

        if (browser.isWebElementPresent(populationElement)) {
            population = populationElement.getText();
            logger.info("Population, total for country {" + countryName + "} is " + population);
        }

        if (browser.isWebElementPresent(co2Element)) {
            co2 = co2Element.getText();
            logger.info("CO2 emissions (metric tons per capita) for country {" + countryName + "} is " + co2);
        }

        return new CountryData(countryName, gdp, population, co2);
    }
}
