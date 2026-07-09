package com.currency.exchange.servlet;

import com.currency.exchange.Utill.RequestUtil;
import com.currency.exchange.dto.response.ExchangeRateResponseDto;
import com.currency.exchange.mapper.ExchangeRateMapper;
import com.currency.exchange.model.ExchangeRate;
import com.currency.exchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRates extends BaseServlet {
    private ObjectMapper objectMapper;
    private ExchangeRateService exchangeRateService;

    @Override
    public void init() throws ServletException {
    objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    exchangeRateService = (ExchangeRateService) getServletContext().getAttribute("exchangeRateService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       List<ExchangeRate> exchangeRateList = exchangeRateService.findAll();
       List<ExchangeRateResponseDto> responseDtoList = ExchangeRateMapper.INSTANCE.toDtoList(exchangeRateList);
       resp.setStatus(HttpServletResponse.SC_OK);
       objectMapper.writeValue(RequestUtil.getWriter(resp), responseDtoList);
    }
}
