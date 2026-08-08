package com.currency.exchange.servlet;

import com.currency.exchange.dto.request.ExchangeRatesRequestDto;
import com.currency.exchange.dto.response.ExchangeRateResponseDto;
import com.currency.exchange.mapper.ExchangeRateMapper;
import com.currency.exchange.model.ExchangeRate;
import com.currency.exchange.service.ExchangeRateService;
import com.currency.exchange.validator.ExchangeRatesRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private ExchangeRateService exchangeRateService;
    private ExchangeRatesRequestValidator validator;


    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        exchangeRateService = (ExchangeRateService) getServletContext().getAttribute("exchangeRateService");
        this.validator = new ExchangeRatesRequestValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ExchangeRate> exchangeRateList = exchangeRateService.findAll();
        List<ExchangeRateResponseDto> responseDtoList = ExchangeRateMapper.INSTANCE.toDtoList(exchangeRateList);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), responseDtoList);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBaseCode = getCleanParam(req, "baseCurrencyCode");
        String reqTargetCode = getCleanParam(req, "targetCurrencyCode");
        String reqRateString = req.getParameter("rate");
        ExchangeRatesRequestDto exchangeRatesRequestDto = new ExchangeRatesRequestDto(reqBaseCode, reqTargetCode, reqRateString);
        validator.validate(exchangeRatesRequestDto);
        ExchangeRate exchangeRateFromDb = exchangeRateService.save(exchangeRatesRequestDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        ExchangeRateResponseDto exchangeRateResponseDto = ExchangeRateMapper.INSTANCE.toDto(exchangeRateFromDb);
        objectMapper.writeValue(resp.getWriter(), exchangeRateResponseDto);
    }

    private String getCleanParam(HttpServletRequest req, String name){
        String parameter = req.getParameter(name);
        return parameter != null ? parameter.trim().toUpperCase() : null;
    }
}


