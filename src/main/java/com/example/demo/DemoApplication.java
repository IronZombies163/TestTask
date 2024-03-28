package com.example.demo;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoApplication {

    public static void main(String[] args) {
        final int NUM_ACCOUNTS = 4;
        List<Account> accounts = IntStream.range(0, NUM_ACCOUNTS)
                .mapToObj(i -> new Account("A" + (i + 1), 10000))
                .toList();

        Random random = new Random();
        int transactionCount = 0;
        ExecutorService executor = Executors.newFixedThreadPool(4);

        while (transactionCount < 30) {
            int randomQuantity = random.nextInt(1000) + 1;
            int fromAccountIndex = random.nextInt(NUM_ACCOUNTS);
            int toAccountIndex = random.nextInt(NUM_ACCOUNTS);

            if (fromAccountIndex != toAccountIndex) {
                Account fromAccount = accounts.get(fromAccountIndex);
                Account toAccount = accounts.get(toAccountIndex);

                executor.execute(() -> {
                    fromAccount.transfer(toAccount, randomQuantity);
                });

                transactionCount++;
            }

            try {
                Thread.sleep(random.nextInt(1001) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        Logger logger = LogManager.getLogger("BalanceLogger");
        for (Account account : accounts) {
            logger.info("Аккаунт ID: " + account.getId() + ", Баланс: " + account.getMoney());
        }
    }

}
