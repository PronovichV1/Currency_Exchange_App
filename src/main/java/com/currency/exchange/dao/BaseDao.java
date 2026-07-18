package com.currency.exchange.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
   Optional<T> save (T Entity);
   List<T> findAll();
}
