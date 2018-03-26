package com.bitcoin.client.blockchain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains Blockchain's specific unspent output transaction structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainTransaction {

    @JsonProperty("tx_hash_big_endian")
    private String transactionHash;
    @JsonProperty
    private int value;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
