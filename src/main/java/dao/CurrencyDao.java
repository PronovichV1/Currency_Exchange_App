package dao;

import java.util.Currency;
import java.util.Optional;

public interface CurrencyDao extends BaseDao<Currency>{
    Optional<Currency> findByCode(String code);
}
