package com.codestates.seb.burgerqueen.cart;

import com.codestates.seb.burgerqueen.cart.domain.CartItem;
import java.util.Map;

public class CartHandler {

  private final CartRepository cartRepository;

  // 생성자를 통한 의존성 주입
  public CartHandler(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  // 장바구니에 있는 모든 상품 조회
  public Map<Integer, CartItem> getAllCartItems() {
    return cartRepository.findAll();
  }

  // 장바구니에 상품 추가
  public void addProductToCart(CartItem cartItem) {
    cartRepository.addCartItem(cartItem);
  }

  // 장바구니에서 상품 삭제
  public void removeProductFromCart(int cartItemId) {
    cartRepository.deletCartItem(cartItemId);
  }

  // 장바구니 비우기
  public void emptyCart() {
    cartRepository.clearCart();
  }

  public int calculateTotalPrice() {
    Map<Integer, CartItem> cartItems = cartRepository.findAll();

    return cartItems.values().stream()
        .mapToInt(item -> (int) item.getProduct().getPrice() * item.getQuantity())
        .sum();
  }
}
