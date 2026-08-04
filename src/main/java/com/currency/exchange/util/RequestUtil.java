package com.currency.exchange.util;

import com.currency.exchange.dto.request.ExchangeRequestDto;
import com.currency.exchange.exception.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class RequestUtil {

    public static double getRate(HttpServletRequest req) {
        double result;
        String body;
        try (BufferedReader reader = req.getReader()) {
            body = reader.readLine();
            if (body == null || !body.startsWith("rate=")) {
                throw new InvalidFormatException("Please enter rate");
            }
            String rateValue = body.split("=")[1];
            result = Double.parseDouble(rateValue);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ExchangeRequestDto getExchangeRequest(HttpServletRequest req) {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        return new ExchangeRequestDto(from, to, amount);
    }
}
