package com.tma.sample.coffeeshop.service;

import com.tma.sample.coffeeshop.dto.ProductDTO;

import java.util.List;

public interface MenuService {


    boolean addProductToMenu(long storeId, long productId);

    void deleteProduct(long productId);






}
