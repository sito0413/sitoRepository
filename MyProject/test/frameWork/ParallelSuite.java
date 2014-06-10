package frameWork;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.Statement;

public class ParallelSuite extends Suite {

	public ParallelSuite(final Class<?> klass, final RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
	}

	public ParallelSuite(final RunnerBuilder builder, final Class<?>[] classes) throws InitializationError {
		super(builder, classes);
	}

	@Override
	protected Statement childrenInvoker(final RunNotifier notifier) {
		return new Statement() {
			@Override
			public void evaluate() {
				final ExecutorService es = Executors.newCachedThreadPool();
				final CompletionService<Object> completionService = new ExecutorCompletionService<>(es);
				for (final Runner each : getChildren()) {
					completionService.submit(new Callable<Object>() {
						@Override
						public Object call() {
							runChild(each, notifier);
							return null;
						}
					});
				}
				final int n = getChildren().size();
				try {
					for (int i = 0; i < n; i++) {
						try {
							completionService.take().get();
						}
						catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}
				finally {
					es.shutdown();
				}
			}
		};
	}
}