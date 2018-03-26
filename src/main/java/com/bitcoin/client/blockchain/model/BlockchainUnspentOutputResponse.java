package com.bitcoin.client.blockchain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Contains the structure of Blockchain's response returned when quering the public API for unspent output transactions.
 */
public class BlockchainUnspentOutputResponse {

    @JsonProperty("unspent_outputs")
    private List<BlockchainTransaction> unspentOutputs;

    public List<BlockchainTransaction> getUnspentOutputs() {
        return unspentOutputs;
    }

    public void setUnspentOutputs(List<BlockchainTransaction> unspentOutputs) {
        this.unspentOutputs = unspentOutputs;
    }
}
