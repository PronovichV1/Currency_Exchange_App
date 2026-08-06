package com.currency.exchange.servlet;

import com.currency.exchange.exception.ValidationException;
import com.currency.exchange.dto.request.ExchangeRateRequestDto;
import com.currency.exchange.model.ExchangeRate;
import com.currency.exchange.service.ExchangeRateService;
import com.currency.exchange.validator.ExchangeRateRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    private ObjectMapper objectMapper;
    private ExchangeRateService exchangeRateService;
    ExchangeRateRequestValidator validator;

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        exchangeRateService = (ExchangeRateService) getServletContext().getAttribute("exchangeRateService");
        this.validator = new ExchangeRateRequestValidator();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedCurrencies = req.getPathInfo();
        ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(requestedCurrencies);
        validator.validate(exchangeRateRequestDto);
        ExchangeRate exchangeRate = exchangeRateService.findByCodePair(exchangeRateRequestDto);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), exchangeRate);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedCurrencies = req.getPathInfo();
        BigDecimal rate = getRate(req);
        ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(requestedCurrencies);
        validator.validate(exchangeRateRequestDto);
        ExchangeRate exchangeRate = exchangeRateService.updateRate(exchangeRateRequestDto, rate);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), exchangeRate);
    }

    private static BigDecimal getRate(HttpServletRequest req) {
        BigDecimal result;
        String body;
        try (BufferedReader reader = req.getReader()) {
            body = reader.readLine();
            if (body == null || !body.startsWith("rate=")) {
                throw new ValidationException("Please enter rate");
            }
            String rateValue = body.split("=")[1];
            result = BigDecimal.valueOf(Double.valueOf(rateValue));
            return result;
        } catch (IOException e) {
            throw new ValidationException("Failed to read request body");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new ValidationException("Invalid rate parameter format");
        }
    }
}
