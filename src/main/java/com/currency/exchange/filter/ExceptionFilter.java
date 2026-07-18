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
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        } catch (InvalidFormatException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        } catch (CurrencyNotFoundException e) {
            log.error("Currency is not exist");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        } catch (ExchangeRateNotFoundException e) {
            log.error("Exchange rate is not exist");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        } catch (CurrencyAlreadyExistException e) {
            log.error("Currency already exist");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        } catch (ExchangeRateAlreadyExistException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        } catch (NoSuchElementException e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, errorResponseDto);
        }

    }
}
