package com.currency.exchange.util;

import com.currency.exchange.dto.request.ExchangeRequestDto;
import jakarta.servlet.http.HttpServletRequest;


public class RequestUtil {

    public static ExchangeRequestDto getExchangeRequest(HttpServletRequest req) {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        return new ExchangeRequestDto(from, to, amount);
    }
}
