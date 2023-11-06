package com.codestates.seb.burgerqueen.order;

import com.codestates.seb.burgerqueen.cart.CartHandler;
import com.codestates.seb.burgerqueen.cart.domain.CartItem;
import com.codestates.seb.burgerqueen.discount.Discount;

import java.util.List;
import java.util.Map;

public class OrderHandler {
  private final CartHandler cartHandler;
  private final Discount discount;

  public OrderHandler(CartHandler cartHandler, Discount discount) {
    this.cartHandler = cartHandler;
    this.discount = discount;
  }

  public int payment() {
    Map<Integer, CartItem> cartItems = cartHandler.getAllCartItems();

    discount.checkAllDiscountConditions();
    int totalPrice = cartHandler.calculateTotalPrice();
    return discount.discount(totalPrice);
  }
}
