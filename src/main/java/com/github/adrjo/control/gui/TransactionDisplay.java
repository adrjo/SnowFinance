package com.github.adrjo.control.gui;

import com.github.adrjo.util.Helper;
import com.github.adrjo.transactions.Transaction;

// Wrapper for transactions to include the transaction id for display in Transactions gui screen
public class TransactionDisplay {

    private int id;
    private Transaction transaction;

    public TransactionDisplay(int id, Transaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }

    public int getId() {
        return this.id;
    }

    public String getDesc() {
        return transaction.desc();
    }

    public String getDate() {
        return Helper.DATE_FORMAT.format(transaction.timestamp());
    }

    public double getAmount() {
        return transaction.amt();
    }
}
