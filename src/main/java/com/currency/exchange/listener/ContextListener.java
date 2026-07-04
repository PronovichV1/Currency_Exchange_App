package com.currency.exchange.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.currency.exchange.dao.CurrencyDao;
import com.currency.exchange.dao.CurrencyDaoImpl;
import com.currency.exchange.dao.ExchangeRateDao;
import com.currency.exchange.dao.ExchangeRateDaoImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import com.currency.exchange.service.CurrencyService;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext sc = sce.getServletContext();
        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();

        CurrencyService currencyService = new CurrencyService(currencyDao);
      /// ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao, currencyDao);
      ///  ExchangeService exchangeService = new ExchangeService(exchangeRateDao);

        sc.setAttribute("objectMapper", objectMapper);
        sc.setAttribute("currencyService", currencyService);
     ///   sc.setAttribute("exchangeRateService", exchangeRateService);
     ///   sc.setAttribute("exchangeService", exchangeService);
    }
}
