package com.bitcoin.exception;

/**
 * Contains the error code using for the exception handling based on {@link BitcoinException}.
 */
public enum ErrorCode {

    INVALID_BITCOIN_ADDRESS("Provided Bitcoin address is not a valid Base58 address."),
    BLOCKCHAIN_CLIENT_ERROR("Cannot retrieve unspent output transaction for the provide Bitcoin address.");

    private String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
