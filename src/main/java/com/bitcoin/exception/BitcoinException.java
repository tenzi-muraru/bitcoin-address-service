package com.bitcoin.exception;

/**
 * Exception to be used for BitcoinAddressService.
 */
public class BitcoinException extends Exception {

    private ErrorCode errorCode;

    public BitcoinException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
