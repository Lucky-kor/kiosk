package com.codestates.seb.burgerqueen.menu.domain;

public interface Product {
  int getId();
  String getName();
  int getKcal();
  int getPrice();
  ProductType getType();
}
