package com.cos.gowalk.thread;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockingTest {

    private static long startTime = System.currentTimeMillis();
    private static int maxCnt = 1000;
    private static long count = 0;

    @Test
    public void threadNotSafe() throws Exception {
        for (int i = 0; i < maxCnt; i++) {
            new Thread(this::plus).start();
        }

        Thread.sleep(2000); // 모든 스레드가 종료될때 까지 잠깐 대기
        Assertions.assertThat(count).isEqualTo(maxCnt);
    }

    public synchronized void plus() {
        if (++count == maxCnt) {
            System.out.println(System.currentTimeMillis() - startTime);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
    }
}
