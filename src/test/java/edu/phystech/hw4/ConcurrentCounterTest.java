package edu.phystech.hw4;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kzlv4natoly
 */
class ConcurrentCounter {
    // Проще всего было использовать AtomicLong, но думаю в зажании подразумевалось использовать именно лок
    private long value = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            value += 1;
        }
    }

    public long getValue() {
        return value;
    }
}


public class ConcurrentCounterTest {

    @Test
    void justWorks() {
        ConcurrentCounter concurrentCounter = new ConcurrentCounter();
        IntStream.range(0, 100_000).forEach(i -> concurrentCounter.increment());
        Assertions.assertEquals(100_000, concurrentCounter.getValue());
    }


    @Test
    void worksWithConcurrentIncrement() throws InterruptedException {
        ConcurrentCounter concurrentCounter = new ConcurrentCounter();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableList = IntStream.range(0, 100_000).mapToObj(i -> (Callable<Integer>) () -> {
                concurrentCounter.increment();
                return 0;
        }).toList();
        executorService.invokeAll(callableList).forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {}
        });
        Assertions.assertEquals(100_000, concurrentCounter.getValue());
    }
}
