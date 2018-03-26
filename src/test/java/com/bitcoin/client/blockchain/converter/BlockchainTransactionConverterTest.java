package com.bitcoin.client.blockchain.converter;

import com.bitcoin.client.blockchain.model.BlockchainTransaction;
import com.bitcoin.representation.Transaction;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BlockchainTransactionConverterTest {

    @Test
    public void testConvert() {
        BlockchainTransaction blockchainTransaction = createBlockchainTransaction();

        Transaction transaction = BlockchainTransactionConverter.convert(blockchainTransaction);

        assertThat(transaction.getTransactionHash(), is(blockchainTransaction.getTransactionHash()));
        assertThat(transaction.getValue(), is(blockchainTransaction.getValue()));
        assertThat(transaction.getIndex(), is(0));
    }

    private BlockchainTransaction createBlockchainTransaction() {
        BlockchainTransaction transaction = new BlockchainTransaction();
        transaction.setTransactionHash("d2c36f19643361cedd05f1bd0c583740dbd54996c261d0b4e541a3700af1d38a");
        transaction.setValue(100);

        return transaction;
    }
}
