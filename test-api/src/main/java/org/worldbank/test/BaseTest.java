package org.worldbank.test;

import org.worldbank.Config;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

public abstract class BaseTest {

    @Rule
    public TestRule chain = RuleChain.outerRule(new TestContextInitializer())
            .around(new FailedTestRetry(Config.failedTestRetryCount))
            .around(new StopOrCloseFixtures())
            .around(new ResponseStatusReporter())
            .around(new FailedTestScreenshotTaker());
}
