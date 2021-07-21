package org.executor.executor;

import java.util.*;
import java.util.concurrent.*;

import org.executor.testrunner.TestRunner;
import org.slf4j.*;

public class Executor {

	Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 用例执行方法，同时获取线程池中线程数量，排队任务数，执行线程数
	 */
	public void executor(List<Object> testcases) {

//		ExecutorService es = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable>());

		ExecutorService es = Executors.newFixedThreadPool(3);
		
		for (Object testcase : testcases) {

			Callable<Object> testrunner = new TestRunner(testcase.toString());

			es.submit(testrunner);

		}

		es.shutdown();

//		ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
//
//		logger.info("当前排队线程数：" + tpe.getQueue().size()); // 输出当前排队线程数
//
//		logger.info("当前活动线程数：" + tpe.getActiveCount()); // 输出当前活动线程数
//
//		logger.info("执行完成线程数：" + tpe.getCompletedTaskCount()); // 执行完成线程数
//
//		logger.info("总的线程数：" + tpe.getTaskCount()); // 总的线程数

	}

	public void stop() {

		ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();

		Thread[] activethreads = new Thread[(int) (threadgroup.activeCount() * 1.5)];

		threadgroup.enumerate(activethreads);

		for (int i = 0; i < activethreads.length; i++) {

			logger.info("当前线程堆栈：" + activethreads[i].getStackTrace());

		}

	}

}
