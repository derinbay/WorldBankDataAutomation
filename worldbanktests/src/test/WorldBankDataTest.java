import org.junit.Test;
import org.worldbank.models.DataList;
import org.worldbank.models.users.Visitor;
import org.worldbank.test.BaseTest;
import pages.DataByCountryPage;
import pages.DataPage;
import pages.HomePage;

import static matchers.Matchers.shouldDisplayRightDetails;
import static matchers.Matchers.shouldSeePageOpened;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Taylan on 28.05.2016.
 */
public class WorldBankDataTest extends BaseTest {

    @Test
    public void shouldGetDatas() {
        HomePage homePage = new HomePage();
        Visitor visitor = Visitor.aVisitor().open(homePage);
        assertThat("When a visitor browses the worldbank website url, ", visitor, shouldSeePageOpened(homePage));

        DataPage dataPage = homePage.goToDataPage();
        assertThat("When a visitor clicks data on navigation menu, ", visitor, shouldSeePageOpened(dataPage));

        DataByCountryPage dataByCountryPage = dataPage.goToDataByCountryPage();
        assertThat("When a visitor filters page by country, ", visitor, shouldSeePageOpened(dataByCountryPage));

        DataList countriesList = dataByCountryPage.getCountries();
        assertThat("When a visitor clicks each country, ", countriesList, shouldDisplayRightDetails());
    }
}
