package com.currency.exchange.filter;

import com.currency.exchange.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.currency.exchange.dto.response.ErrorResponseDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;


@WebFilter("/*")
public class ExceptionFilter implements Filter {
    private ObjectMapper objectMapper;
    private static Logger log = LoggerFactory.getLogger(ExceptionFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        objectMapper = (ObjectMapper) servletContext.getAttribute("objectMapper");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            chain.doFilter(servletRequest, servletResponse);
        } catch (DataBaseException e) {
            log.error(e.getMessage(), e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ValidationException e) {
            log.warn(e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getLocalizedMessage());
        } catch (CurrencyNotFoundException e) {
            log.warn("Currency does not exist");
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getLocalizedMessage());
        } catch (ExchangeRateNotFoundException e) {
            log.warn("Exchange rate does not exist");
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getLocalizedMessage());
        } catch (CurrencyAlreadyExistException e) {
            log.warn("Currency already exist");
            sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getLocalizedMessage());
        } catch (ExchangeRateAlreadyExistException e) {
            log.warn("Exchange rate already exist");
            sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, e.getLocalizedMessage());
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getLocalizedMessage());
        } catch (Exception e){
            log.error("Unexpected request error", e);
            sendErrorResponse(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Internal server error");
        }


    }


    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message);
        PrintWriter out = response.getWriter();
        objectMapper.writeValue(out, errorResponseDto);
    }
}
