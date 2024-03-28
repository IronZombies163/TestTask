package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@AllArgsConstructor
public class Account {

    private String id;
    private int money;


    public synchronized void transfer(Account transferAccount, int quantity) {

        if (this.money >= quantity) {
            this.money -= quantity;
            transferAccount.money += quantity;
            Logger logger = LogManager.getLogger("TransactionLogger");
            logger.info("Перевод из " + this.id + " в " + transferAccount.id + ": " + quantity);
        }

    }
}
