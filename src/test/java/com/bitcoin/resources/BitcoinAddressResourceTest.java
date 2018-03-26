package com.bitcoin.resources;

import com.bitcoin.exception.BitcoinException;
import com.bitcoin.exception.ErrorCode;
import com.bitcoin.representation.Transaction;
import com.bitcoin.service.TransactionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BitcoinAddressResourceTest {

    private static final String BITCOIN_ADDRESS = "1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq";

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BitcoinAddressResource resource;

    @BeforeMethod
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testGetUnspentTransactions() throws Exception {
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());
        when(transactionService.getUnspentOutputTransactions(anyString())).thenReturn(transactions);

        List<Transaction> unspentTransactions = resource.getUnspentTransactions(BITCOIN_ADDRESS);

        assertThat(unspentTransactions.size(), is(transactions.size()));
        IntStream.range(0, transactions.size())
                .forEach(index -> assertThat(unspentTransactions.get(index).getIndex(), is(index + 1)));
    }

    @Test
    public void testGetUnspentTransactionsForInvalidBitcoinAddress() {
        testGetUnspentTransactionsWithError("Invalid Bitcoin address", ErrorCode.INVALID_BITCOIN_ADDRESS);
    }

    @Test
    public void testGetUnspentTransactionsWithInternalServiceError() throws Exception {
        when(transactionService.getUnspentOutputTransactions(anyString()))
                .thenThrow(new BitcoinException(ErrorCode.BLOCKCHAIN_CLIENT_ERROR));

        testGetUnspentTransactionsWithError(BITCOIN_ADDRESS, ErrorCode.BLOCKCHAIN_CLIENT_ERROR);
    }

    private void testGetUnspentTransactionsWithError(String bitcoinAddress, ErrorCode expectedErrorCode) {
        try {
            resource.getUnspentTransactions(bitcoinAddress);
            fail("Exception expected to be thrown.");
        } catch (BitcoinException e) {
            assertThat(e.getErrorCode(), is(expectedErrorCode));
        }
    }

}
