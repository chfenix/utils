package cn.solwind.sample;

import cn.solwind.common.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 多线程相关
 */
public class MultiThreadSample {

    public static void main(String[] args) {
        Thread1();
    }

    /**
     * 多线程并发执行，等待全部执行完毕后再继续执行
     */
    private static void Thread1() {
        List<Java8StreamDTO> listSample = new ArrayList<>();
        // 注意List有线程安全问题，要使用synchronizedList
        List<Java8StreamDTO> listSync = Collections.synchronizedList(listSample);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int num = 100;     // 执行的任务数
        CountDownLatch latch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            final int j = i;
            executorService.execute(() -> {
                long l = System.currentTimeMillis();
                try {
                    Java8StreamDTO dto = new Java8StreamDTO();
                    dto.setId("" + j);
                    listSync.add(dto);
                    Thread.sleep(RandomUtil.getRandom(1000,2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + j + "执行完成");
                latch.countDown();
            });
        }
        System.out.println("全部线程开始执行");
        try {
            latch.await(28, TimeUnit.SECONDS);
            System.out.println("全部线程执行完毕" + listSync.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 注意线程池作用域，局部变量可以shutdown，全局变量不能shutdown
        executorService.shutdown();
    }

}
