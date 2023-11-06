package com.codestates.seb.burgerqueen.menu.domain;

public class SingleMenu implements Product {
  private int productId;
  private String name;
  private int price;
  private int kcal;
  private ProductType type;



  // 매개변수가 있는 생성자
  public SingleMenu(int productId, String name, int price, int kcal, ProductType type) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.kcal = kcal;
    this.type = type;
  }

  // Menu 인터페이스 메서드 구현
  @Override
  public int getId() {
    return productId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPrice() {
    return price;
  }

  @Override
  public int getKcal() {
    return kcal;
  }

  @Override
  public ProductType getType() {
    return type;
  }
}

