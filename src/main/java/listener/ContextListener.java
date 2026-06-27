package listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CurrencyDao;
import dao.CurrencyDaoImpl;
import dao.ExchangeRateDao;
import dao.ExchangeRateDaoImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import service.CurencyService;
import service.ExchangeRateService;
import service.ExchangeService;


public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext sc = sce.getServletContext();
        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();

        CurencyService curencyService = new CurencyService(currencyDao);
        ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao, currencyDao);
        ExchangeService exchangeService = new ExchangeService(exchangeRateDao);

        sc.setAttribute("objectMapper", objectMapper);
        sc.setAttribute("curencyService", curencyService);
        sc.setAttribute("exchangeRateService", exchangeRateService);
        sc.setAttribute("exchangeService", exchangeService);
    }
}
