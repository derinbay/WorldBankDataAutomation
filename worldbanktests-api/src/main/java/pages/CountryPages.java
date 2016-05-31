package pages;

import org.openqa.selenium.By;
import org.worldbank.models.CountryData;
import org.worldbank.models.Page;
import org.worldbank.models.users.Visitor;

/**
 * Created by Taylan on 1.06.2016.
 */
public class CountryPages extends Page<Visitor> {

    public CountryData getPageValues() {
        String gdp = browser.findElement(By.xpath("//*[contains(text(), 'GDP at market prices')]/../../../following-sibling::div//*[@class='human-readable']")).getText();
        String population = browser.findElement(By.xpath("//*[contains(text(), 'Population, total')]/../../../following-sibling::div//*[@class='human-readable']")).getText();;
        String co2 = browser.findElement(By.xpath("//*[contains(text(), 'CO2 emissions')]/../following-sibling::div//*[contains(@class, 'wbapi-data-value')]//*[@class='field-content']")).getText();

        return new CountryData(gdp, population, co2);
    }
}
