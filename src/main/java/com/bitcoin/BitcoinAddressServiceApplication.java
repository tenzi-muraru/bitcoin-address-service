package com.bitcoin;

import com.bitcoin.resources.BitcoinAddressResource;
import com.bitcoin.resources.BitcoinExceptionMapper;
import com.bitcoin.client.blockchain.BlockchainTransactionService;
import com.bitcoin.service.TransactionService;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class BitcoinAddressServiceApplication extends Application<BitcoinAddressServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BitcoinAddressServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "BitcoinAddressService";
    }

    @Override
    public void initialize(final Bootstrap<BitcoinAddressServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(final BitcoinAddressServiceConfiguration configuration,
                    final Environment environment) {
        // Init Blockchain API client.
        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());
        final TransactionService blockchainTransactionService = new BlockchainTransactionService(client,
                configuration.getBlockchainHostname());

        // Init and register resources.
        environment.jersey().register(new BitcoinAddressResource(blockchainTransactionService));

        // Register custom exception mapper.
        environment.jersey().register(new BitcoinExceptionMapper());
    }

}

