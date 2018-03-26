package com.bitcoin.client.blockchain.converter;

import com.bitcoin.client.blockchain.model.BlockchainTransaction;
import com.bitcoin.representation.Transaction;

public final class BlockchainTransactionConverter {

    /**
     * Private constructor to prevent instantiation.
     */
    private BlockchainTransactionConverter() {

    }

    public static Transaction convert(BlockchainTransaction blockchainTransaction) {
        Transaction transaction = new Transaction();
        transaction.setTransactionHash(blockchainTransaction.getTransactionHash());
        transaction.setValue(blockchainTransaction.getValue());

        return transaction;
    }

}
