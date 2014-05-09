package frameWork.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.core.authority.AuthorityCheckerTest;
import frameWork.core.fileSystem.FileSystemTest;
import frameWork.core.targetFilter.TargetFilterTest;
import frameWork.core.viewCompiler.ViewCompilerTest;

@RunWith(Suite.class)
@SuiteClasses({
        AuthorityCheckerTest.class, TargetFilterTest.class, FileSystemTest.class, ViewCompilerTest.class
})
public class AllTests {
	
}
