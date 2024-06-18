package edu.phystech.hw4.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kzlv4natoly
 */
public class CASTicketLock {
    private final AtomicInteger nextTicket = new AtomicInteger();
    private final AtomicInteger currentTicket = new AtomicInteger();
    private final Object lock = new Object();

    public void lock() {
        int my_ticket = -1;
        synchronized (lock) {
            my_ticket = nextTicket.getAndIncrement();
        }
        while (my_ticket != currentTicket.get()) {}
    }

    public void unlock() {
        this.currentTicket.getAndIncrement();
    }
}
