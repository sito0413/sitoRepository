package frameWork.base.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.base.core.authority.AuthorityCheckerTest;
import frameWork.base.core.fileSystem.FileSystemTest;
import frameWork.base.core.routing.TargetFilterTest;
import frameWork.base.core.viewCompiler.ViewCompilerTest;

@RunWith(Suite.class)
@SuiteClasses({
        AuthorityCheckerTest.class, TargetFilterTest.class, FileSystemTest.class, ViewCompilerTest.class
})
public class AllTests {
	
}
