package com.tma.sample.coffeeshop.repository;

import com.tma.sample.coffeeshop.model.Menu;
import com.tma.sample.coffeeshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface MenuRepository extends JpaRepository<Menu,Long> {

    @Query("SELECT m.product FROM Menu m WHERE m.store.id=:storeId order by m.product.name")
    List<Product> findProductsOfAStore(@Param("storeId") long storeId);

}
