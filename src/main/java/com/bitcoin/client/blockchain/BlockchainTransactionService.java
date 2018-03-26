package com.bitcoin.client.blockchain;

import com.bitcoin.client.blockchain.converter.BlockchainTransactionConverter;
import com.bitcoin.client.blockchain.model.BlockchainUnspentOutputResponse;
import com.bitcoin.exception.BitcoinException;
import com.bitcoin.exception.ErrorCode;
import com.bitcoin.representation.Transaction;
import com.bitcoin.service.TransactionService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link TransactionService} interface using Blockchain's public API.
 */
public class BlockchainTransactionService implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainTransactionService.class);

    private static final String NO_TRANSACTIONS_FOUND = "No free outputs to spend";
    private static final String INVALID_BITCOIN_ADDRESS = "Invalid Bitcoin Address";

    private static final String UNSPENT_TRANSACTION_PATH = "/unspent";
    private static final String ACTIVE_QUERY_PARAM = "active";
    private static final String CORS_QUERY_PARAM = "cors";

    private Client blockchainClient;
    private final String hostname;

    public BlockchainTransactionService(Client blockchainClient, String hostname) {
        this.blockchainClient = blockchainClient;
        this.hostname = hostname;
    }

    @Override
    public List<Transaction> getUnspentOutputTransactions(String bitcoinAddress) throws BitcoinException {
        Response response = blockchainClient.target(getUnspentTransactionURL())
                .queryParam(ACTIVE_QUERY_PARAM, bitcoinAddress)
                .queryParam(CORS_QUERY_PARAM, true)
                .request()
                .get();

        if (response != null) {
            return handleResponse(response);
        }

        LOGGER.error("Unable to retrieve response from Blockchain.");
        throw new BitcoinException(ErrorCode.BLOCKCHAIN_CLIENT_ERROR);
    }

    private static List<Transaction> handleResponse(Response response) throws BitcoinException {
        switch (response.getStatus()) {
            case HttpStatus.SC_OK:
                return parseSuccessfulResponse(response);
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                return parseUnsuccessfulResponse(response);
            default:
                throw new BitcoinException(ErrorCode.BLOCKCHAIN_CLIENT_ERROR);
        }
    }

    private static List<Transaction> parseSuccessfulResponse(Response response) {
        BlockchainUnspentOutputResponse blockchainResponse = response.readEntity(BlockchainUnspentOutputResponse.class);

        return blockchainResponse.getUnspentOutputs().stream()
                .map(BlockchainTransactionConverter::convert)
                .collect(Collectors.toList());
    }

    private static List<Transaction> parseUnsuccessfulResponse(Response response) throws BitcoinException {
        String errorMessage = response.readEntity(String.class);

        switch (errorMessage) {
            case NO_TRANSACTIONS_FOUND:
                LOGGER.info("No unspent output transactions found for the provided Bitcoin address.");
                return Collections.emptyList();
            case INVALID_BITCOIN_ADDRESS:
                LOGGER.info("Invalid Bitcoin address.");
                throw new BitcoinException(ErrorCode.INVALID_BITCOIN_ADDRESS);
            default:
                LOGGER.info("Blockchain client returned 500 with message: [%s]" + errorMessage);
                throw new BitcoinException(ErrorCode.BLOCKCHAIN_CLIENT_ERROR);
        }
    }

    private String getUnspentTransactionURL() {
        return hostname + UNSPENT_TRANSACTION_PATH;
    }
}
