package com.currency.exchange.servlet;

import com.currency.exchange.Utill.RequestUtil;
import com.currency.exchange.dto.reciept.CurrencyRequestForPostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.currency.exchange.dto.response.CurrencyResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.currency.exchange.mapper.CurrencyMapper;
import com.currency.exchange.model.Currency;
import com.currency.exchange.service.CurrencyService;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends BaseServlet {

    private ObjectMapper objectMapper;
    private CurrencyService currencyService;

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper)getServletContext().getAttribute("objectMapper");
        currencyService = (CurrencyService) getServletContext().getAttribute("currencyService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencyList = currencyService.findAllCurrencies();
        List<CurrencyResponseDto> currencyResponseDto = CurrencyMapper.INSTANCE.toCurrencyResponseDtoList(currencyList);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(RequestUtil.getWriter(resp), currencyResponseDto);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        CurrencyRequestForPostDto currencyPostDto = new CurrencyRequestForPostDto(name, code, sign);
        currencyPostDto.validate();
        Currency currencyEntity = CurrencyMapper.INSTANCE.toEntity(currencyPostDto);
        System.out.println(currencyEntity);
        System.out.println(currencyEntity.id());
        Currency currencyFromDB = currencyService.save(currencyEntity);
        CurrencyResponseDto currencyResponseDto = CurrencyMapper.INSTANCE.toCurrencyResponseDto(currencyFromDB);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), currencyResponseDto);

    }
}
