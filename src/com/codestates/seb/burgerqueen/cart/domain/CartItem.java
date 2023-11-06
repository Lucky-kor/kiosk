package com.codestates.seb.burgerqueen.cart.domain;

import com.codestates.seb.burgerqueen.menu.domain.Product;

public class CartItem {
  private Product product;
  private int quantity;

  // 장바구니 아이템 생성자
  public CartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  // 상품 반환
  public Product getProduct() {
    return product;
  }

  // 수량 반환
  public int getQuantity() {
    return quantity;
  }

  // 수량 설정
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
