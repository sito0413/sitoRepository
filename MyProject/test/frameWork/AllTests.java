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
import frameWork.base.core.state.ContextAttributeMapTest;
import frameWork.base.core.state.ImpOfStateTest;
import frameWork.base.core.state.RequestAttributeMapTest;
import frameWork.base.core.state.ResponseTest;
import frameWork.base.core.state.SessionAttributeMapTest;
import frameWork.base.core.viewCompiler.ScopeTest;
import frameWork.base.core.viewCompiler.parser.ParserBufferTest;
import frameWork.base.core.viewCompiler.parser.ScriptletTest;
import frameWork.base.core.viewCompiler.parser.TextletTest;
import frameWork.base.core.viewCompiler.script.expression.ArrayConstructorScriptTest;
import frameWork.base.core.viewCompiler.script.expression.ConstructorScriptTest;
import frameWork.base.core.viewCompiler.script.expression.DeclarationScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.BlockScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.DoScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.ElseScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.ForScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.IfScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.SwitchScriptTest;
import frameWork.base.core.viewCompiler.script.syntax.WhileScriptTest;

@RunWith(ParallelSuite.class)
@SuiteClasses({
        AuthorityCheckerTest.class, FileSystemTest.class, BarcodeTest.class, AuthorityTest.class, BinderTest.class,
        HttpStatusTest.class, UtilityCharacterTest.class, UtilityUrlTest.class, TimerEventListTest.class,
        TimerEventStackTest.class, QueueTest.class, ResourceRoutTest.class, RoutTest.class, RouterTest.class,
        RequestAttributeMapTest.class, ContextAttributeMapTest.class, SessionAttributeMapTest.class,
        ResponseTest.class, ImpOfStateTest.class, ScopeTest.class, TextletTest.class, ScriptletTest.class,
        ParserBufferTest.class, IfScriptTest.class, ElseScriptTest.class, BlockScriptTest.class, DoScriptTest.class,
        WhileScriptTest.class, ForScriptTest.class, SwitchScriptTest.class, DeclarationScriptTest.class,
        ArrayConstructorScriptTest.class, ConstructorScriptTest.class
})
public class AllTests {
	
}
