package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class DemoApplicationTests {

    @Test
    void testParallelTransactions() throws InterruptedException {
        Account account1 = new Account("B1", 10000);
        Account account2 = new Account("B2", 10000);

        int balance1 = account1.getMoney();
        int balance2 = account2.getMoney();

        Thread thread1 = new Thread(() -> account1.transfer(account2, 5000));
        Thread thread2 = new Thread(() -> account2.transfer(account1, 3000));

        thread1.run();
        thread2.run();

        thread1.join();
        thread2.join();

        assertEquals(balance2 + balance1, account1.getMoney() + account2.getMoney());
    }

    @Test
    void testTransfer() {
        Account account1 = new Account("F1", 10000);
        Account account2 = new Account("F2", 10000);
        account1.transfer(account2, 1000);
        assertEquals(11000, account2.getMoney());
        assertEquals(9000, account1.getMoney());
    }

    @Test
    public void testExceptionHandling() {
        Account account1 = new Account("id1", 10000);
        Account account2 = new Account("id2", 10000);

        try {
            account1.transfer(account2, 11000);
        } catch (IllegalStateException e) {
            assertEquals(10000, account1.getMoney());
            assertEquals(10000, account2.getMoney());
        }
    }
}
