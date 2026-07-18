package com.currency.exchange.Util;

import com.currency.exchange.dto.reciept.ExchangeRequestDto;
import com.currency.exchange.exception.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class RequestUtil {

    public static PrintWriter getWriter(HttpServletResponse response) throws IOException {
        return response.getWriter();
    }

    public static <T> T fromJson(HttpServletRequest request, ObjectMapper objectMapper, Class<T> type) throws IOException {
        return objectMapper.readValue(request.getReader(), type);
    }

    public static double getRate(HttpServletRequest req){
        double result;
        String body;
        try(BufferedReader reader = req.getReader()) {
            body = reader.readLine();
            if (body == null || !body.startsWith("rate=")){
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
