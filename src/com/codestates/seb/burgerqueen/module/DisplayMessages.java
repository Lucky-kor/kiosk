package com.codestates.seb.burgerqueen.module;

public enum DisplayMessages {
  CART_DISPLAY("🧺 (0) 장바구니"),
  ORDER_DISPLAY("📦 (+) 주문하기"),
  MENU_SELECTION_PROMPT("[📣] 메뉴를 선택해 주세요: "),
  INVALID_MENU_SELECTION("잘못된 메뉴를 입력했습니다."),
  CART_TITLE("🧺 장바구니"),
  MENU_ITEM_NOT_FOUND("해당 메뉴 아이템이 존재하지 않습니다."),
  ITEM_ADDED_TO_CART("[📣] %s를(을) 장바구니에 담았습니다."),
  ENTER_TO_RETURN("이 전으로 돌아가려면 엔터를 누르세요."),
  TOTAL_PRICE("합계: %d원"),
  PRODUCT_FORMAT("%s %d원 %d개"),
  SIDE_DETAILS("사이드 내역: "),
  SEPARATOR("-".repeat(60)),
  ORDER_TITLE("[📣] 주문이 완료되었습니다. "),
  ORDER_DETAIL("[📣] 주문 내역은 다음과 같습니다. ");

  private final String text;

  DisplayMessages(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
