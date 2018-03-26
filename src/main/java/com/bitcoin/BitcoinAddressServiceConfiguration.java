package com.bitcoin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BitcoinAddressServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("jerseyClient")
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();

    @NotEmpty
    @JsonProperty
    private String blockchainHostname;

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClientConfiguration;
    }

    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClientConfiguration) {
        this.jerseyClientConfiguration = jerseyClientConfiguration;
    }

    public String getBlockchainHostname() {
        return blockchainHostname;
    }
}
