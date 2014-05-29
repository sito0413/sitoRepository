package frameWork.base;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.base.barcode.BarcodeTest;
import frameWork.base.core.authority.AuthorityCheckerTest;
import frameWork.base.core.authority.AuthorityTest;
import frameWork.base.core.binder.BinderTest;
import frameWork.base.core.fileSystem.FileSystemTest;
import frameWork.base.core.routing.TargetFilterTest;

@RunWith(ParallelSuite.class)
@SuiteClasses({
        AuthorityCheckerTest.class, TargetFilterTest.class, FileSystemTest.class, BarcodeTest.class,
        AuthorityTest.class, BinderTest.class
})
public class AllTests {
	
}
