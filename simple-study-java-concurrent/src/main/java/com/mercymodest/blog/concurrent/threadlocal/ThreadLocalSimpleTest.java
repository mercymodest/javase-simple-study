package com.mercymodest.blog.concurrent.threadlocal;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * {@link  ThreadLocal} 简单测试
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 04-11 7:41
 */
public class ThreadLocalSimpleTest {

    @Test
    public void testFirst() {
        final ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();
        integerThreadLocal.set(1);
        ThreadUtil.newSingleExecutor()
                .execute(() -> {
                    integerThreadLocal.set(2);
                    printThreadLocalValue(integerThreadLocal);
                });
        printThreadLocalValue(integerThreadLocal);
        // 确保所有线程都可以执行完成
        ThreadUtil.safeSleep(200);
    }

    @Test
    public void testThreadLocalOom() {
        ThreadLocal<byte[]> bytesThreadLocal = new ThreadLocal<>();
        bytesThreadLocal.set(new byte[1024 * 1024 * 100]);
        bytesThreadLocal = null;
        System.gc();
        System.out.println(Thread.currentThread().getName());
    }

    /**
     * 打印线程在{@link  ThreadLocal} 上存储的值
     *
     * @param threadLocal {@code threadLocal}
     */
    private void printThreadLocalValue(ThreadLocal<?> threadLocal) {
        if (Objects.isNull(threadLocal)) {
            // maybe logger warn logs
            return;
        }
        System.out.printf("%s --- threadLocal: %s value:%s %n", Thread.currentThread().getName(), threadLocal, threadLocal.get());
    }
}
