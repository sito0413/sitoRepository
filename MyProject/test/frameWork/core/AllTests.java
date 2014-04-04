package frameWork.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.core.authority.AuthorityCheckerTest;
import frameWork.core.config.BaseTest;
import frameWork.core.targetFilter.TargetFilterTest;

@RunWith(Suite.class)
@SuiteClasses({
        AuthorityCheckerTest.class, TargetFilterTest.class, BaseTest.class
})
public class AllTests {
	
}
