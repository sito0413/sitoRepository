package frameWork;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.base.HttpStatusTest;
import frameWork.base.UtilityCharacterTest;
import frameWork.base.UtilityUrlTest;
import frameWork.base.barcode.BarcodeTest;
import frameWork.base.core.authority.AuthorityCheckerTest;
import frameWork.base.core.authority.AuthorityTest;
import frameWork.base.core.binder.BinderTest;
import frameWork.base.core.event.queue.QueueTest;
import frameWork.base.core.event.timerEvent.TimerEventListTest;
import frameWork.base.core.event.timerEvent.TimerEventStackTest;
import frameWork.base.core.fileSystem.FileSystemTest;
import frameWork.base.core.routing.ResourceRoutTest;
import frameWork.base.core.routing.RoutTest;
import frameWork.base.core.routing.RouterTest;

@RunWith(ParallelSuite.class)
@SuiteClasses({
        AuthorityCheckerTest.class, FileSystemTest.class, BarcodeTest.class, AuthorityTest.class, BinderTest.class,
        HttpStatusTest.class, UtilityCharacterTest.class, UtilityUrlTest.class, TimerEventListTest.class,
        TimerEventStackTest.class, QueueTest.class, ResourceRoutTest.class, RoutTest.class, RouterTest.class
})
public class AllTests {
	
}
