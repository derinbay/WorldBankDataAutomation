package org.worldbank.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


public class ResponseStatusReporter implements TestRule {

    private static final Logger logger = LogManager.getLogger("PageStatusLogger");

    @Override
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;
                try {
                    base.evaluate();
                    return;
                } catch (Throwable t) {
                    caughtThrowable = t;
                }
                throw caughtThrowable;
            }
        };
    }
}
