package com.bitcoin.validation;


import com.bitcoin.exception.BitcoinException;
import com.bitcoin.exception.ErrorCode;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BitcoinValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BitcoinValidator.class);

    private static final String INVALID_ADDRESS_ERROR_MSG = "Invalid Bitcoin address: [%s]";

    /**
     * Private constructor to prevent instantiation.
     */
    private BitcoinValidator() {
    }

    public static void validateBitcoinAddress(String bitcoinAddress) throws BitcoinException {
        if (bitcoinAddress == null) {
            throw new BitcoinException(ErrorCode.INVALID_BITCOIN_ADDRESS);
        }

        try {
            new Address(null, bitcoinAddress);
        } catch (AddressFormatException e) {
            LOGGER.info(String.format(INVALID_ADDRESS_ERROR_MSG, bitcoinAddress));
            throw new BitcoinException(ErrorCode.INVALID_BITCOIN_ADDRESS);
        }
    }
}
