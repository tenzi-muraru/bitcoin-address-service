package com.bitcoin.validation;

import com.bitcoin.exception.BitcoinException;
import com.bitcoin.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BitcoinValidatorTest {

    private static final String BITCOIN_ADDRESS = "1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq";

    @Test
    public void shouldValidateBitcoinAddress() throws Exception {
        BitcoinValidator.validateBitcoinAddress(BITCOIN_ADDRESS);
    }

    @DataProvider(name = "bitcoinAddressProvider")
    private Object[][] bitcoinAddressProvider() {
        return new Object[][]{
                {StringUtils.EMPTY},
                {null},
                {"1A8JiWcwvpY7tAopUkSnGuEYHmzGYfLLll"},
                {"00ooiWcwvpY7tAopUkSnGuEYHmzGYfZPiq"},
                {"1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq"},
        };
    }

    @Test(dataProvider = "bitcoinAddressProvider")
    public void shouldFailToValidateBitcoinAddress(String bitcoinAddress) {
        try {
            BitcoinValidator.validateBitcoinAddress(bitcoinAddress);
            fail("Exception expected to be thrown due to invalid bitcoin address.");
        } catch (BitcoinException e) {
            assertThat(e.getErrorCode(), is(ErrorCode.INVALID_BITCOIN_ADDRESS));
        }
    }

}
