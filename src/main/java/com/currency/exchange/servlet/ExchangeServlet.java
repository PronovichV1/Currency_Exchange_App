package com.currency.exchange.servlet;

import com.currency.exchange.dto.request.ExchangeRequestDto;
import com.currency.exchange.dto.response.ExchangeResponseDto;
import com.currency.exchange.mapper.ExchangeMapper;
import com.currency.exchange.model.Exchange;
import com.currency.exchange.service.ExchangeService;
import com.currency.exchange.util.RequestUtil;
import com.currency.exchange.validator.ExchangeRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private ObjectMapper objectMapper;
    private ExchangeService exchangeService;
    private ExchangeRequestValidator validator;


    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        exchangeService = (ExchangeService) getServletContext().getAttribute("exchangeService");
        this.validator = new ExchangeRequestValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRequestDto exchangeRequestDto = RequestUtil.getExchangeRequest(req);
        validator.validate(exchangeRequestDto);
        Exchange exchangeResponse = exchangeService.findSpecific(exchangeRequestDto);
        resp.setStatus(HttpServletResponse.SC_OK);
        ExchangeResponseDto exchangeResponseDto = ExchangeMapper.INSTANCE.toDto(exchangeResponse);
        objectMapper.writeValue(resp.getWriter(), exchangeResponseDto);
    }
}
