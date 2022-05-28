package com.github.mengweijin.quietly.config.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mengweijin
 * @date 2022/5/28
 */
public class ProjectThreadPoolFactory {

    private static final ConcurrentHashMap<Long, ThreadPoolExecutor> THREAD_POOL = new ConcurrentHashMap<>();

    public static synchronized ThreadPoolExecutor getThreadPool(Long projectId) {
        ThreadPoolExecutor executor = THREAD_POOL.get(projectId);
        if(executor == null) {
            executor = new ThreadPoolExecutor(
                    1,
                    1,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    (r) -> new Thread(r, "thread-pool-run-case-project-id-" + projectId),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
            THREAD_POOL.put(projectId, executor);
        }
        return executor;
    }
}
