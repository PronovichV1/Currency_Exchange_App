package com.currency.exchange.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.Filter;

import java.io.IOException;


@WebFilter(value = {
        "/currencies", "/currency/*", "/exchangeRates", "/exchangeRate/*", "/exchange"
})
public class EncodingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, SecurityException, ServletException {
      servletResponse.setContentType("application/json");
      servletResponse.setCharacterEncoding("UTF-8");
      servletRequest.setCharacterEncoding("UTF-8");
      filterChain.doFilter(servletRequest, servletResponse);
  }

}
