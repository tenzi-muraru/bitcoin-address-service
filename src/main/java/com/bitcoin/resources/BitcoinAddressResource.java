package com.bitcoin.resources;

import com.bitcoin.exception.BitcoinException;
import com.bitcoin.representation.Transaction;
import com.bitcoin.service.TransactionService;
import com.bitcoin.validation.BitcoinValidator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/address")
public class BitcoinAddressResource {

    private TransactionService transactionService;

    public BitcoinAddressResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    @Path("/{bitcoinAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getUnspentTransactions(@PathParam("bitcoinAddress") String bitcoinAddress) throws BitcoinException {
        BitcoinValidator.validateBitcoinAddress(bitcoinAddress);

        List<Transaction> transactions = transactionService.getUnspentOutputTransactions(bitcoinAddress);
        formatOutput(transactions);
        return transactions;
    }

    private static void formatOutput(List<Transaction> transactions) {
        transactions.forEach(transaction -> transaction.setIndex(transactions.indexOf(transaction) + 1));
    }
}
