package com.codestates.seb.burgerqueen.cart;

import com.codestates.seb.burgerqueen.cart.domain.CartItem;

import java.util.Map;

public interface CartRepository {

  Map<Integer, CartItem> findAll();
  void addCartItem(CartItem cartItem);
  void deletCartItem(int cartItem);
  void clearCart();
}
