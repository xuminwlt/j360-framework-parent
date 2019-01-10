package me.j360.framework.common.pool;

import com.vip.vjtools.vjkit.concurrent.threadpool.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.common.pool.thread.PriorityCallable;
import me.j360.framework.common.pool.thread.PriorityRunnable;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 说明：并发优先级容器
 */


@Slf4j
public class DefaultPriorityExecutor {

    private static ThreadPoolExecutor executor;

    public DefaultPriorityExecutor() {

        PriorityBlockingQueue queue = new PriorityBlockingQueue(1000);
        RejectedExecutionHandler reh = new ThreadPoolExecutor.AbortPolicy();

        executor = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, queue, ThreadPoolUtil.buildThreadFactory("DefaultPriorityExecutor", true), reh);
    }

    //提交Future任务
    public static <T> CompletableFuture<T> supplySync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }


    //执行异步任务
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor);
    }


    //提交Runnable任务
    public static void execute(PriorityRunnable command) {
        executor.execute(command);
    }

    //普通submit
    public static <T> Future<T> submit(PriorityCallable<T> command) {
        return executor.submit(command);
    }


    public void destroy() throws Exception {
        ThreadPoolUtil.gracefulShutdown(executor, 3000);
    }
}
