package com.codestates.seb.burgerqueen.menu;

import com.codestates.seb.burgerqueen.menu.domain.Product;

import java.util.Map;

public interface MenuRepository {
  Product save(Product product);
  Map<Integer, Product> findAll();
  Product findById(int id);
}
