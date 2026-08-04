package com.currency.exchange.util;

import com.currency.exchange.dto.request.ExchangeRequestDto;
import com.currency.exchange.exception.ExchangeRateNotFoundException;
import com.currency.exchange.exception.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class RequestUtil {

    public static ExchangeRequestDto getExchangeRequest(HttpServletRequest req) {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        return new ExchangeRequestDto(from, to, amount);
    }
}
