package com.bitcoin.representation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains the JSON structure of an unspent output transaction which is returned by BitcoinAddressService.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    @JsonProperty
    private int value;

    @JsonProperty("txHash")
    private String transactionHash;

    @JsonProperty("outputIdx")
    private int index;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
