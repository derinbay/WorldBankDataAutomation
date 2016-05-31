package matchers;

import org.apache.logging.log4j.Logger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.worldbank.URLUtil;
import org.worldbank.models.*;
import org.worldbank.models.users.Visitor;
import pages.CountryPages;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Taylan on 28.05.2016.
 */
public class Matchers {

    private static final Logger logger = getLogger(Matchers.class);

    public static <S extends Visitor>Matcher<S> shouldSeePageOpened(Page page) {
        return new BaseMatcher<S>() {
            String currentUrl;

            @Override
            public boolean matches(Object item) {
                Visitor visitor = (Visitor) item;
                currentUrl = visitor.browser().currentURL();

                return URLUtil.matches(page.url(), currentUrl);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Visitor should see the opened page as, ").appendValue(page.url());
            }

            @Override
            public void describeMismatch(Object object, Description description) {
                description.appendText("page opened as, ").appendValue(currentUrl);
            }
        };
    }

    public static <S extends DataList>Matcher<S> shouldDisplayRightDetails() {
        return new BaseMatcher<S>() {
            boolean page;
            String pageTitle;
            int i = 0;
            List<String> countryTexts;

            @Override
            public boolean matches(Object item) {
                DataList countriesList = (DataList) item;
                Browser browser = countriesList.browser();
                countryTexts = countriesList.getElementTexts();
                CountryPages countryPages = new CountryPages();
                List<CountryData> countryDatas = new ArrayList<>();

                for (i = 0; i < countriesList.size(); i++) {
                    browser.clickTo(countriesList.getElements().get(i));
                    browser.changePage(countryPages);
                    pageTitle = browser.findElement(By.xpath("//h1[contains(@class, 'page-title')]")).getText();

                    CountryData countryData = countryPages.getPageValues();
                    countryDatas.add(countryData);

                    page = pageTitle.equals(countryTexts.get(i));

                    if (page == false) {
                        return page;
                    }
                    browser.goBack();
                    countriesList.refresh();
                }

                return page;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Visitor should see the opened page as, ").appendValue(countryTexts.get(i));
            }

            @Override
            public void describeMismatch(Object object, Description description) {
                description.appendText("page opened as, ").appendValue(pageTitle);
            }
        };
    }
}
