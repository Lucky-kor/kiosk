package com.codestates.seb.burgerqueen.menu;
import com.codestates.seb.burgerqueen.menu.domain.Product;

import java.util.*;

public class InMemoryMenuRepository implements MenuRepository {
  private static Map<Integer, Product> menus = new HashMap<>();

  @Override
  public Product save(Product product) {
    // 고유한 id를 위해, 항상 현재 크기의 값을 넣습니다.
    int id = menus.size() + 1;
    menus.put(product.getId(), product);
    return product;
  }

  @Override
  public Map<Integer, Product> findAll() {
    return menus;
  }

  @Override
  public Product findById(int id) {
    return menus.get(id);
  }
}