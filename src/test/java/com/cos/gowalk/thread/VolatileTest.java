package com.cos.gowalk.thread;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VolatileTest {

    private static volatile long count = 0; // volatile 키워드 추가

    @Test
    public void threadNotSafe() throws Exception {
        int maxCnt = 1000;

        for (int i = 0; i < maxCnt; i++) {
            new Thread(() -> count++).start();
        }

        Thread.sleep(100); // 모든 스레드가 종료될때 까지 잠깐 대기
        Assertions.assertThat(count).isEqualTo(maxCnt);
    }

}
