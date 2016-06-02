import org.junit.Test;
import org.worldbank.models.DataList;
import org.worldbank.models.users.Visitor;
import org.worldbank.test.BaseTest;
import pages.DataByCountryPage;
import pages.DataPage;
import pages.HICPage;
import pages.HomePage;

import static matchers.Matchers.shouldDisplayRightCountryDetailsAndLogThem;
import static matchers.Matchers.shouldSeePageOpened;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.worldbank.models.users.Visitor.aVisitor;

/**
 * Created by Taylan on 28.05.2016.
 */
public class WorldBankDataTest extends BaseTest {

    @Test
    public void shouldNavigateWorldBankCountryDatas() {
        HomePage homePage = new HomePage();
        Visitor visitor = aVisitor().open(homePage);
        assertThat("When a visitor opens the worldbank website url, ", visitor, shouldSeePageOpened(homePage));

        DataPage dataPage = homePage.goToDataPage();
        assertThat("When a visitor clicks data on navigation menu, ", visitor, shouldSeePageOpened(dataPage));

        DataByCountryPage dataByCountryPage = dataPage.goToDataByCountryPage();
        assertThat("When a visitor filters page by country, ", visitor, shouldSeePageOpened(dataByCountryPage));

        HICPage hICPage = dataByCountryPage.goToHICPage();
        assertThat("When a visitor filters page by country, ", visitor, shouldSeePageOpened(hICPage));

        DataList countriesList = hICPage.getCountries();
        assertThat("When a visitor clicks to each country, ", countriesList, shouldDisplayRightCountryDetailsAndLogThem());

        assertThat("When a visitor filters page by country, ", visitor, shouldSeePageOpened(hICPage));

        hICPage.goToHomePage();
        assertThat("When a visitor opens the worldbank website url, ", visitor, shouldSeePageOpened(homePage));
    }
}
