package matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.worldbank.CsvGenerator;
import org.worldbank.URLUtil;
import org.worldbank.models.Browser;
import org.worldbank.models.CountryData;
import org.worldbank.models.DataList;
import org.worldbank.models.Page;
import org.worldbank.models.users.Visitor;
import pages.CountryPages;
import pages.HICPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylan on 28.05.2016.
 */
public class Matchers {

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

    public static <S extends DataList>Matcher<S> shouldDisplayRightCountryDetailsAndLogThem() {
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

                    CountryData countryData = countryPages.getPageValues(pageTitle);
                    countryDatas.add(countryData);

                    page = pageTitle.equals(countryTexts.get(i));

                    if (page == false) {
                        return page;
                    }

                    browser.goBack();
                    countriesList.refresh();
                }

                browser.changePage(new HICPage());

                CsvGenerator.generateCsvFile(countryDatas);

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
