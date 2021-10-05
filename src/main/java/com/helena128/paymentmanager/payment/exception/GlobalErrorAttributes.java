package com.helena128.paymentmanager.payment.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(
                request, options);
        Throwable error = getError(request);
        if (error instanceof PaymentException) {
            map.put(ResponseConstants.STATUS.getValue(), HttpStatus.BAD_REQUEST);
        } else {
            map.put(ResponseConstants.STATUS.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put(ResponseConstants.MESSAGE.getValue(), error.getMessage());
        return map;
    }

}
