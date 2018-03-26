package com.bitcoin.service;

import com.bitcoin.exception.BitcoinException;
import com.bitcoin.representation.Transaction;

import java.util.List;

public interface TransactionService {

    /**
     * Retrieve the unspent output transaction for the provided Bitcoin address.
     *
     * @param bitcoinAddress The given Bitcoin address.
     * @return a the list of all unspent output transactions.
     * @throws BitcoinException if errors occur while computing the transactions.
     */
    List<Transaction> getUnspentOutputTransactions(String bitcoinAddress) throws BitcoinException;

}
