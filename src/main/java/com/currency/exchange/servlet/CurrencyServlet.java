package com.currency.exchange.servlet;

import com.currency.exchange.dto.request.CurrencyRequestDto;
import com.currency.exchange.dto.response.CurrencyResponseDto;
import com.currency.exchange.mapper.CurrencyMapper;
import com.currency.exchange.model.Currency;
import com.currency.exchange.service.CurrencyService;
import com.currency.exchange.validator.CurrencyRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private CurrencyService currencyService;
    private CurrencyRequestValidator validator;

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        currencyService = (CurrencyService) getServletContext().getAttribute("currencyService");
        this.validator = new CurrencyRequestValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(req.getPathInfo());
        validator.validate(currencyRequestDto);
        Currency currency = currencyService.findSpecific(currencyRequestDto.code());
        CurrencyResponseDto currencyResponseDto = CurrencyMapper.INSTANCE.toCurrencyResponseDto(currency);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), currencyResponseDto);
    }
}
