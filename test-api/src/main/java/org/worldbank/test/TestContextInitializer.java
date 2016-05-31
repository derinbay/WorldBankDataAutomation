package org.worldbank.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestContextInitializer implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                TestContext.init(description.getClassName(), description.getMethodName());
                System.out.println("TEST STARTED: " + description.getClassName() + " - " + description.getMethodName());
                try {
                    base.evaluate();
                    return;
                } finally {

                }
            }
        };
    }
}
