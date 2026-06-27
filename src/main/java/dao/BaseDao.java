package dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

   Optional<T> save ();

   List<T> findAll();

}
