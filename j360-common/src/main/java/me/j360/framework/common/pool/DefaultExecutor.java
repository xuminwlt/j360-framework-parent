package me.j360.framework.common.pool;


import com.vip.vjtools.vjkit.concurrent.threadpool.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 *
 * 说明：并发容器
 */

@Slf4j
public class DefaultExecutor  {

    private static ThreadPoolExecutor executor;

    public DefaultExecutor() {
        LinkedBlockingQueue queue = new LinkedBlockingQueue(1000);
        RejectedExecutionHandler reh = new ThreadPoolExecutor.AbortPolicy();

        this.executor = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, queue, ThreadPoolUtil.buildThreadFactory("DefaultExecutor", true), reh);
    }


    //提交Future任务
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public static <T> CompletableFuture<T> supplyDefultAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    //执行异步任务
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    public static CompletableFuture<Void> runSafeAsync(Runnable runnable) {
        return CompletableFuture.runAsync(ThreadPoolUtil.safeRunnable(runnable), executor);
    }

    //提交Runnable任务
    public static void execute(Runnable command) {
        executor.execute(command);
    }

    //普通submit
    public static <T> Future<T> submit(Callable<T> command) {
        return executor.submit(command);
    }

    public void destroy() throws Exception {
        ThreadPoolUtil.gracefulShutdown(this.executor, 3000);
    }
}
