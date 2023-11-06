package com.codestates.seb.burgerqueen.menu.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetMenu implements Product {
  private int id;
  private String name;
  private List<Product> items; // 세트 메뉴에 포함된 상품 목록
  private ProductType productType;

  public SetMenu(int id, String name, ProductType productType, List<Product> items) {
    this.id = id;
    this.name = name;
    this.items = items != null ? items : new ArrayList<>();
    this.productType = productType;
  }

  public void menuAdd(Product menu) {
    if(menu.getType() != ProductType.SET) {
      items.add(menu);
    }
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    // 여기에 할인 로직을 추가할 수 있습니다.
    return (int) (items.stream().mapToDouble(Product::getPrice).sum() * 0.9);
  }

  @Override
  public ProductType getType() {
    return productType;
  }

  public int getKcal() {
    return items.stream().mapToInt(Product::getKcal).sum();
  }

  public List<Product> getItems() {
    return Collections.unmodifiableList(items);
  }
}
