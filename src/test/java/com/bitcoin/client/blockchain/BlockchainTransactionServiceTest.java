package com.bitcoin.client.blockchain;

import com.bitcoin.client.blockchain.model.BlockchainTransaction;
import com.bitcoin.client.blockchain.model.BlockchainUnspentOutputResponse;
import com.bitcoin.exception.BitcoinException;
import com.bitcoin.exception.ErrorCode;
import com.bitcoin.representation.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BlockchainTransactionServiceTest {

    private static final String BITCOIN_ADDRESS = "1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq";
    private static final String NO_TRANSACTIONS_FOUND = "No free outputs to spend";
    private static final String INVALID_BITCOIN_ADDRESS = "Invalid Bitcoin Address";

    @Mock
    private WebTarget webTarget;
    @Mock
    private Invocation.Builder builder;
    @Mock
    private Response response;

    @Mock
    private Client client;
    @InjectMocks
    private BlockchainTransactionService blockchainTransactionService;

    @BeforeMethod
    public void setup() {
        initMocks(this);

        doReturn(webTarget).when(client).target(anyString());
        doReturn(webTarget).when(webTarget).queryParam(anyString(), anyObject());
        doReturn(builder).when(webTarget).request();
        doReturn(response).when(builder).get();
    }

    @Test
    public void testGetUnspentOutputTransactions() throws Exception {
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        BlockchainUnspentOutputResponse blockchainResponse = createBlockchainResponse(3);
        when(response.readEntity(BlockchainUnspentOutputResponse.class)).thenReturn(blockchainResponse);

        List<Transaction> transactions = blockchainTransactionService.getUnspentOutputTransactions(BITCOIN_ADDRESS);

        assertThat(transactions.size(), is(blockchainResponse.getUnspentOutputs().size()));
    }

    @Test
    public void testGetUnspentOutputTransactionsWithNoFreeOutputs() throws Exception {
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.readEntity(String.class)).thenReturn(NO_TRANSACTIONS_FOUND);

        List<Transaction> transactions = blockchainTransactionService.getUnspentOutputTransactions(BITCOIN_ADDRESS);

        assertThat(transactions.isEmpty(), is(true));
    }

    @Test
    public void testGetUnspentOutputTransactionsWithInvalidAddress() {
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.readEntity(String.class)).thenReturn(INVALID_BITCOIN_ADDRESS);

        try {
            blockchainTransactionService.getUnspentOutputTransactions(BITCOIN_ADDRESS);
            fail("Exception expected to be thrown due to invalid address.");
        } catch (BitcoinException e) {
            assertThat(e.getErrorCode(), is(ErrorCode.INVALID_BITCOIN_ADDRESS));
        }
    }

    @Test
    public void testGetUnspentOutputTransactionsWithInternalServiceError() {
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.readEntity(String.class)).thenReturn(StringUtils.EMPTY);

        try {
            blockchainTransactionService.getUnspentOutputTransactions(BITCOIN_ADDRESS);
            fail("Exception expected to be thrown due to internal service error.");
        } catch (BitcoinException e) {
            assertThat(e.getErrorCode(), is(ErrorCode.BLOCKCHAIN_CLIENT_ERROR));
        }
    }

    private BlockchainUnspentOutputResponse createBlockchainResponse(int transactionsCount) {
        List<BlockchainTransaction> unspentTransactions = new ArrayList<>();
        IntStream.range(0, transactionsCount)
                .forEach(i -> unspentTransactions.add(new BlockchainTransaction()));

        BlockchainUnspentOutputResponse response = new BlockchainUnspentOutputResponse();
        response.setUnspentOutputs(unspentTransactions);
        return response;
    }

}
