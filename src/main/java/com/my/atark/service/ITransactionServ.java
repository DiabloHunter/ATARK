package com.my.atark.service;

import com.my.atark.domain.Transaction;
import com.my.atark.domain.TransactionType;
import com.my.atark.exceptions.TransactionServiceException;

import java.util.List;

public interface ITransactionServ {
    /**
     * Finds all transactions
     * @return List of transactions
     * @throws TransactionServiceException if unable to retrieve information for certain reasons
     */
    List<Transaction> findAllTransactions() throws TransactionServiceException;

    /**
     * Finds all transactions by transaction type (PAYMENT or REFUND)
     * @param type - transaction type
     * @return List of selected transactions
     * @throws TransactionServiceException if unable to retrieve information for certain reasons
     */
    List<Transaction> findAllTransactionsByType(TransactionType type) throws TransactionServiceException;
}
