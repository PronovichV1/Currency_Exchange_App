package com.currency.exchange.servlet;

import com.currency.exchange.dto.reciept.ExchangeRateRequestDto;
import com.currency.exchange.exception.InvalidFormatException;
import com.currency.exchange.model.ExchangeRate;
import com.currency.exchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet{
    private ObjectMapper objectMapper;
    private ExchangeRateService exchangeRateService;

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        exchangeRateService = (ExchangeRateService) getServletContext().getAttribute("exchangeRateService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedCurrencies = req.getPathInfo();
        ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(requestedCurrencies);
        exchangeRateRequestDto.validate();
        ExchangeRate exchangeRate = exchangeRateService.findByCodePair(exchangeRateRequestDto);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter() ,exchangeRate);
    }
}
