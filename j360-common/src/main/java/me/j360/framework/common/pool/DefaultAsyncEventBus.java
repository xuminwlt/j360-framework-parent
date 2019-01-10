package me.j360.framework.common.pool;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.vip.vjtools.vjkit.concurrent.threadpool.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 说明：异步消息总线
 *
 * 使用案例
 *  1. Service
 *
 *  @Autowire
 *  DefaultAsyncEventBus
 *
 *  2. Listener
 *
 *  private DefaultAsyncEventBus eventBus;
    private Ks3Client client;
    private Ks3TaskService ks3TaskService;

 @Autowired
 public NotifyListener(Ks3EventBus eventBus, Ks3TaskService ks3TaskService) {
 this.eventBus = eventBus;
 this.ks3TaskService = ks3TaskService;
 }

 @PostConstruct
 public void init() {
 this.eventBus.register(this);
 }

 *
 */

@Slf4j
public class DefaultAsyncEventBus {

    private final AsyncEventBus eventBus;
    private final BlockingQueue blockingQueue;
    private final ThreadPoolExecutor executor;

    public DefaultAsyncEventBus() {
        this.blockingQueue = new LinkedBlockingQueue<>(100000);
        this.executor = new ThreadPoolExecutor(4, 10, 3, TimeUnit.SECONDS, blockingQueue, ThreadPoolUtil.buildThreadFactory("DefaultAsyncEventBus", true), new ThreadPoolExecutor.AbortPolicy());

        this.eventBus = new AsyncEventBus(executor, new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                log.error("异步消息队列异常: [subscribeMethod={}, event={} ]",context.getSubscriberMethod(), context.getEvent().toString(),exception);
            }
        });

    }

    public DefaultAsyncEventBus(int corePoolSize, int maxPoolSize) {
        this.blockingQueue = new LinkedBlockingQueue<>(100000);
        this.executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 3, TimeUnit.SECONDS, blockingQueue, ThreadPoolUtil.buildThreadFactory("DefaultAsyncEventBus", true), new ThreadPoolExecutor.AbortPolicy());

        this.eventBus = new AsyncEventBus(executor, new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                log.error("异步消息队列异常: [subscribeMethod={}, event={} ]",context.getSubscriberMethod(), context.getEvent().toString(),exception);
            }
        });

    }

    /**
     * 注册事件
     */
    public void register(Object object){
        eventBus.register(object);
    }

    /**
     * 执行事件
     * @param object
     */
    public void post(Object object){
        eventBus.post(object);
    }

    /**
     * 卸载事件
     * @param object
     */
    public void unRegister(Object object){
        eventBus.unregister(object);
    }


    @PreDestroy
    public void destroy() throws Exception {
        ThreadPoolUtil.gracefulShutdown(executor, 3000);
    }
}
