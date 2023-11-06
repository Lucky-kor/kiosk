package com.codestates.seb.burgerqueen.cart;
import com.codestates.seb.burgerqueen.cart.domain.CartItem;

import java.util.*;

public class InMemoryCartRepository implements CartRepository{

  private static Map<Integer, CartItem> items = new HashMap<>();

  @Override
  public Map<Integer, CartItem> findAll() {
    return items;
  }

  @Override
  public void addCartItem(CartItem cartItem) {
    items.put(items.size(), cartItem);
  }

  @Override
  public void deletCartItem(int id) {
    items.remove(id);
  }

  @Override
  public void clearCart() {
    items.clear();
  }
}
