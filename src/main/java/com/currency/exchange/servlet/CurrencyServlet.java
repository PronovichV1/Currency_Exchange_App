package com.currency.exchange.servlet;

import com.currency.exchange.Utill.RequestUtil;
import com.currency.exchange.dto.reciept.CurrencyRequestDto;
import com.currency.exchange.dto.response.CurrencyResponseDto;
import com.currency.exchange.mapper.CurrencyMapper;
import com.currency.exchange.model.Currency;
import com.currency.exchange.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
   private ObjectMapper objectMapper;
   private CurrencyService currencyService;

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        currencyService = (CurrencyService) getServletContext().getAttribute("currencyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(req.getPathInfo());
        currencyRequestDto.validate();
        Currency currency = currencyService.findSpecific(currencyRequestDto.code());
        CurrencyResponseDto currencyResponseDto = CurrencyMapper.INSTANCE.toCurrencyResponseDto(currency);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(RequestUtil.getWriter(resp), currencyResponseDto);
    }
}
