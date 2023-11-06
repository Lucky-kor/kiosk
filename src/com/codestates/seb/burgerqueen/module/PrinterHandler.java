package com.codestates.seb.burgerqueen.module;

import com.codestates.seb.burgerqueen.menu.domain.Product;

public class PrinterHandler {
  public void print(String str) {
    System.out.println(str);
  }

  public void printf(String outputContent, Object... args) {
    String formattedString = String.format(outputContent, args);
    System.out.println(formattedString);
  }

  public void printProduct(Product product) {
    printf("(%d) %s %d kcal %d 원", product.getId(), product.getName(), product.getKcal(), product.getPrice());
  }
}
