package org.worldbank.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by Taylan on 28.05.2016.
 */
public class StopOrCloseFixtures implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    TestContext.closeBrowsers();
                }
            }
        };
    }
}
