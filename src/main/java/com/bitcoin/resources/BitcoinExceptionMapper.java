package com.bitcoin.resources;

import com.bitcoin.exception.BitcoinException;
import com.bitcoin.exception.ErrorCode;
import io.dropwizard.jersey.errors.ErrorMessage;
import org.apache.commons.lang3.ObjectUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bitcoin.exception.ErrorCode.BLOCKCHAIN_CLIENT_ERROR;
import static com.bitcoin.exception.ErrorCode.INVALID_BITCOIN_ADDRESS;

/**
 * Custom exception mapper for the {@link BitcoinException} thrown by the Bitcoin address service.
 */
@Provider
public class BitcoinExceptionMapper implements ExceptionMapper<BitcoinException> {

    // Map containing the associations between the internal ErrorCode and its corresponding HTTP response code.
    private static final Map<ErrorCode, Response.Status> HTPP_ERROR_MAPPER = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>(INVALID_BITCOIN_ADDRESS, Status.BAD_REQUEST),
            new AbstractMap.SimpleEntry<>(BLOCKCHAIN_CLIENT_ERROR, Status.INTERNAL_SERVER_ERROR)
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));

    @Override
    public Response toResponse(BitcoinException e) {
        Response.Status status = ObjectUtils.defaultIfNull(HTPP_ERROR_MAPPER.get(e.getErrorCode()), Status.INTERNAL_SERVER_ERROR);

        return Response.status(status)
                .entity(new ErrorMessage(status.getStatusCode(), e.getErrorCode().getDescription()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
