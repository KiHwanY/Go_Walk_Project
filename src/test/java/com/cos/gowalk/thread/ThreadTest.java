package com.cos.gowalk.thread;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadTest {


    private static long count = 0;

//    @Test
//    public void threadNotSafe() throws Exception {
//        int maxCnt = 10;
//
//        for (int i = 0; i < maxCnt; i++) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//                count++;
//                System.out.println(count);
//            }).start();
//        }
//
//        Thread.sleep(100); // 모든 스레드가 종료될 때까지 잠깐 대기
//        System.out.println("Final count: " + count);
//        Assertions.assertThat(count).isEqualTo(maxCnt);
//    }

    @Test
    public void threadNotSafe() throws Exception {
        int maxCnt = 10;

        for (int i = 0; i < maxCnt; i++) {
            new Thread(this::plus).start();
        }

        Thread.sleep(100); // 모든 스레드가 종료될 때까지 잠깐 대기
        Assertions.assertThat(count).isEqualTo(maxCnt);
    }

    public synchronized void plus() { // synchronized 키워드 사용
        count++;
    }
}

