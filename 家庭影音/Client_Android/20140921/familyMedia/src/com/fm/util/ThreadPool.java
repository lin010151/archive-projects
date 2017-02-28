package com.fm.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**线程池，负责整个项目中所有线程的调配*/
public class ThreadPool 
{
	public static ExecutorService m_threadPool = null;		//线程池
	/**程序是否正在运行*/
	public static boolean isRuning = true; 
	public static int n = 0;
	
	/**获得全局单一的线程池对象*/
	public static ExecutorService getThreadPool()
	{
		if (m_threadPool == null)
		{
			m_threadPool = Executors.newFixedThreadPool(8);	//最多线程数为3 TODO
		}
		
		return m_threadPool;
	}
	
	
	/**关闭线程池的所有线程，包括死循环里的线程*/
	public static void shutduwn()
	{
		if (m_threadPool != null)
		{
			isRuning = false;
			m_threadPool.shutdownNow();
			
			m_threadPool = null;
		}

	}
}

/*ExecutorService.shutdown()将使之前通过Executor.execute()提交的任务运行结束后关闭线程池。
 * ExecutorService还提供了一个与ExecutorService.shutdown()对应的方法名为
 * ExecutorService.shutdownNow()该方法试图将结束已经提交的任务并结束线程池
 * (只有调用了interrupt才起作用，亦即任务体run的代码中有sleep())。
 * isTerminaed
 */