package com.currency.exchange.filter;

import com.currency.exchange.exception.CurrencyNotFoundException;
import com.currency.exchange.exception.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.currency.exchange.dto.response.ErrorResponseDto;
import com.currency.exchange.exception.DataBaseException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;


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

        try{
            chain.doFilter(servletRequest, servletResponse);
        }catch (DataBaseException dataBaseException){
            log.error("DB Error");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(dataBaseException.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        }catch (InvalidFormatException invalidFormatException){
            log.error(invalidFormatException.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(invalidFormatException.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        }catch (CurrencyNotFoundException currencyNotFoundException){
            log.error("Currency is not exist");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(currencyNotFoundException.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        }
    }


}
