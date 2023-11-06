package com.codestates.seb.burgerqueen;

import com.codestates.seb.burgerqueen.cart.CartHandler;
import com.codestates.seb.burgerqueen.cart.domain.CartItem;
import com.codestates.seb.burgerqueen.menu.MenuHandler;
import com.codestates.seb.burgerqueen.menu.domain.Product;
import com.codestates.seb.burgerqueen.menu.domain.ProductType;
import com.codestates.seb.burgerqueen.menu.domain.SetMenu;
import com.codestates.seb.burgerqueen.module.DisplayMessages;
import com.codestates.seb.burgerqueen.module.InputHandler;
import com.codestates.seb.burgerqueen.module.PrinterHandler;
import com.codestates.seb.burgerqueen.order.OrderHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BurgerQueenApplication 클래스는 주문 서비스 애플리케이션의 메인 클래스입니다.
 * 사용자 인터페이스와 사용자 입력을 처리하며, 주문 프로세스를 관리합니다.
 */
public class BurgerQueenApplication {
  // 각 핸들러는 애플리케이션의 다른 부분과의 상호작용을 담당합니다.
  private final PrinterHandler printerHandler; // 출력을 담당하는 핸들러
  private final InputHandler inputHandler; // 사용자 입력을 처리하는 핸들러
  private final MenuHandler menuHandler; // 메뉴 관련 작업을 처리하는 핸들러
  private final CartHandler cartHandler; // 장바구니 관련 작업을 처리하는 핸들러
  private final OrderHandler orderHandler; // 주문 처리를 담당하는 핸들러

  /**
   * BurgerQueenApplication의 생성자입니다.
   * 모든 핸들러를 초기화하여 애플리케이션의 다른 부분들과 연결합니다.
   */
  public BurgerQueenApplication(PrinterHandler printerHandler, InputHandler inputHandler, MenuHandler menuHandler, CartHandler cartHandler, OrderHandler orderHandler) {
    this.printerHandler = printerHandler;
    this.inputHandler = inputHandler;
    this.menuHandler = menuHandler;
    this.cartHandler = cartHandler;
    this.orderHandler = orderHandler;
  }

  /**
   * 애플리케이션의 메인 실행 메서드입니다.
   * 사용자에게 타이틀을 표시하고, 주문이 완료될 때까지 메뉴를 표시하고 사용자의 입력을 처리합니다.
   */
  public void run() {
    displayTitle(); // 애플리케이션의 타이틀을 표시합니다.

    boolean orderCompletion = false; // 주문이 완료되었는지를 추적하는 플래그입니다.

    // 주문이 완료될 때까지 메뉴 표시와 사용자 입력 처리를 반복합니다.
    while (!orderCompletion) {
      displayMenu(); // 사용자에게 메뉴를 표시합니다.
      displayChoices(); // 사용자가 선택할 수 있는 옵션을 표시합니다.
      orderCompletion = processUserInput(); // 사용자의 입력을 처리하고 주문 완료 여부를 결정합니다.
    }
  }

  // 타이틀을 표시하는 메서드입니다.
  private void displayTitle() {
    printerHandler.print("🍔 BurgerQueen Order Service");
  }

  // 전체 메뉴를 표시하는 메서드입니다.
  private void displayMenu() {
    printerHandler.print("[🔻] 메뉴");
    printerHandler.print(DisplayMessages.SEPARATOR.getText());

    List<Product> menuItemList = menuHandler.getAllMenus(); // 모든 메뉴 항목을 가져옵니다.

    // 각 상품 유형별로 상품을 표시합니다.
    for (ProductType type : ProductType.values()) {
      displayProductsByType(type, menuItemList);
    }
  }

  // 특정 상품 유형에 속하는 상품들을 표시하는 메서드입니다.
  private void displayProductsByType(ProductType type, List<Product> products) {
    printerHandler.print(type.name()); // 상품 유형의 이름을 표시합니다.
    // 해당 유형에 속하는 상품들만 필터링하여 출력합니다.
    products.stream()
        .filter(product -> product.getType() == type)
        .forEach(product -> printerHandler.printProduct(product));
  }
  // 주어진 상품 유형에 해당하는 메뉴를 표시하는 메서드입니다.
  private void displayTypeMenu(ProductType productType) {
    // 사용자에게 선택하라는 메시지와 함께 상품 유형을 출력합니다.
    printerHandler.print(productType.name() + "를 골라주세요");

    // 모든 메뉴 아이템을 가져옵니다.
    List<Product> menuItemList = menuHandler.getAllMenus();

    // 해당 상품 유형에 맞는 메뉴 아이템만을 순회하며 출력합니다.
    for (Product product : menuItemList) {
      if (product.getType() == productType) {
        // 상품 ID, 이름, 칼로리를 포맷에 맞춰 출력합니다.
        printerHandler.printf("(%d) %s %dkcal", product.getId(), product.getName(), product.getKcal());
      }
    }
  }

  // 사용자가 선택할 수 있는 옵션을 표시하는 메서드입니다.
  private void displayChoices() {
    printerHandler.print("");
    printerHandler.print(DisplayMessages.CART_DISPLAY.getText());
    printerHandler.print(DisplayMessages.ORDER_DISPLAY.getText());
    printerHandler.print(DisplayMessages.SEPARATOR.getText());
  }

  // 사용자의 입력을 처리하고 주문 완료 여부를 결정하는 메서드입니다.
  private boolean processUserInput() {
    String selectedInputValue = getValidSelectedInputValue(); // 유효한 입력 값을 받습니다.
    boolean orderCompletion = false; // 주문 완료 상태를 초기화합니다.

    // 사용자가 선택한 값에 따라 적절한 동작을 수행합니다.
    switch (selectedInputValue) {
      case "0":
        displayCart(); // 장바구니를 표시합니다.
        break;
      case "+":
        orderCompletion = orderMenuItem(); // 주문을 처리합니다.
        break;
      default:
        selectMenuItem(Integer.parseInt(selectedInputValue)); // 선택된 메뉴 아이템을 처리합니다.
    }

    return orderCompletion; // 주문 완료 상태를 반환합니다.
  }

  private void selectMenuItem(int menuItemId) {
    // 사용자가 선택한 ID에 해당하는 메뉴 아이템을 가져옵니다.
    Product foundMenuItem = getMenuItem(menuItemId);

    // 메뉴 아이템이 존재하는 경우, 해당 아이템을 처리합니다.
    if (existMenuItem(foundMenuItem)) {
      processSelectedMenuItem(foundMenuItem);
    } else {
      // 메뉴 아이템이 존재하지 않는 경우, 사용자에게 알립니다.
      printerHandler.print("해당 메뉴 상품이 존재하지 않습니다.");
    }
  }
  // 선택된 상품을 처리하는 메서드입니다. 세트 메뉴인 경우 사이드와 음료를 추가로 선택합니다.
  private void processSelectedMenuItem(Product product) {
    // 선택된 상품이 세트 메뉴인 경우
    if (product.getType() == ProductType.SET) {
      // 사이드 상품을 선택할 때까지 반복합니다.
      Product sideProduct = null;
      while (sideProduct == null || sideProduct.getType() != ProductType.SIDE) {
        displayTypeMenu(ProductType.SIDE); // 사이드 메뉴를 표시합니다.
        // 사용자의 입력을 받아 사이드 메뉴를 가져옵니다.
        sideProduct = menuHandler.getMenuById(Integer.parseInt(inputHandler.inputValue()));
        // 선택된 사이드 메뉴가 유효하지 않은 경우, 사용자에게 알립니다.
        if (sideProduct == null || sideProduct.getType() != ProductType.SIDE) {
          printerHandler.print("해당 사이드 메뉴가 존재하지 않습니다. 다시 선택해주세요.");
        }
      }

      // 음료 상품을 선택할 때까지 반복합니다.
      Product drinkProduct = null;
      while (drinkProduct == null || drinkProduct.getType() != ProductType.DRINK) {
        displayTypeMenu(ProductType.DRINK); // 음료 메뉴를 표시합니다.
        // 사용자의 입력을 받아 음료 메뉴를 가져옵니다.
        drinkProduct = menuHandler.getMenuById(Integer.parseInt(inputHandler.inputValue()));
        // 선택된 음료 메뉴가 유효하지 않은 경우, 사용자에게 알립니다.
        if (drinkProduct == null || drinkProduct.getType() != ProductType.DRINK) {
          printerHandler.print("해당 음료 메뉴가 존재하지 않습니다. 다시 선택해주세요.");
        }
      }

      // 세트 메뉴에 사이드와 음료를 추가합니다.
      SetMenu setMenu = (SetMenu) product;
      setMenu.menuAdd(sideProduct);
      setMenu.menuAdd(drinkProduct);
      // 완성된 세트 메뉴를 장바구니에 추가합니다.
      addProductToCart(setMenu);
    } else {
      // 세트 메뉴가 아닌 경우, 상품을 바로 장바구니에 추가합니다.
      addProductToCart(product);
    }
  }

  // 주어진 메뉴 항목 ID에 해당하는 상품을 반환하는 메서드입니다.
  private Product getMenuItem(int menuItemId) {
    // 모든 메뉴 항목을 가져옵니다.
    List<Product> menuItemList = menuHandler.getAllMenus();
    // 스트림을 사용하여 주어진 ID와 일치하는 첫 번째 메뉴 항목을 찾습니다.
    return menuItemList.stream()
        .filter(menuItem -> menuItem.getId() == menuItemId) // ID가 일치하는 항목을 필터링합니다.
        .findFirst() // 첫 번째 일치 항목을 찾습니다.
        .orElse(null); // 일치하는 항목이 없으면 null을 반환합니다.
  }

  // 장바구니에 담긴 모든 항목을 표시하고 총 금액을 계산하여 출력하는 메서드입니다.
  private void displayCart() {
    // 장바구니 제목을 출력합니다.
    printerHandler.print(DisplayMessages.CART_TITLE.getText());
    // 구분선을 출력합니다.
    printerHandler.print(DisplayMessages.SEPARATOR.getText());

    // 장바구니에 담긴 모든 항목을 가져옵니다.
    Map<Integer, CartItem> cartItems = cartHandler.getAllCartItems();
    // 모든 장바구니 항목에 대해 displayCartItem 메서드를 호출하여 출력합니다.
    cartItems.forEach((key, item) -> displayCartItem(item));

    // 장바구니의 총 금액을 계산합니다.
    int totalPrice = cartHandler.calculateTotalPrice();
    // 구분선을 출력합니다.
    printerHandler.print(DisplayMessages.SEPARATOR.getText());
    // 총 금액을 출력합니다.
    printerHandler.print(String.format(DisplayMessages.TOTAL_PRICE.getText(), totalPrice));
    // 사용자가 입력을 계속하기 위한 메시지를 출력합니다.
    printerHandler.print(DisplayMessages.ENTER_TO_RETURN.getText());
    // 사용자의 입력을 기다립니다.
    inputHandler.inputValue();
  }

  // 주어진 장바구니 항목의 상세 정보를 출력하는 메서드입니다.
  private void displayCartItem(CartItem item) {
    // 장바구니 항목에서 상품 정보를 가져옵니다.
    Product product = item.getProduct();

    // 상품 유형이 세트 메뉴인 경우
    if (product.getType() == ProductType.SET) {
      // 세트 메뉴를 캐스팅하고 사이드 아이템을 문자열로 가져옵니다.
      SetMenu setMenu = (SetMenu) product;
      String sideItems = getSideItemsAsString(setMenu);
      // 세트 메뉴의 이름, 가격, 사이드 아이템을 포맷에 맞춰 출력합니다.
      printerHandler.printf("%s %d원 (%s)", product.getName(), product.getPrice(), sideItems);
    } else {
      // 세트 메뉴가 아닌 경우, 상품의 이름, 가격, 수량을 포맷에 맞춰 출력합니다.
      printerHandler.printf("%s %d원 %d개", product.getName(), product.getPrice(), item.getQuantity());
    }
  }

  // 세트 메뉴에 포함된 사이드 아이템들의 이름을 쉼표로 구분된 문자열로 변환하는 메서드입니다.
  private String getSideItemsAsString(SetMenu setMenu) {
    // 세트 메뉴의 아이템들을 스트림으로 변환하고, 각 아이템의 이름을 매핑한 후 쉼표로 연결합니다.
    return setMenu.getItems().stream()
        .map(Product::getName) // 각 Product 객체의 getName 메서드를 호출하여 이름을 가져옵니다.
        .collect(Collectors.joining(", ")); // 이름들을 쉼표와 공백으로 구분하여 하나의 문자열로 결합합니다.
  }

  // 사용자로부터 유효한 메뉴 선택 입력을 받을 때까지 반복적으로 요청하는 메서드입니다.
  private String getValidSelectedInputValue() {
    boolean isValidSelectedInputValue = false;
    String selectedInputValue = null;

    // 유효한 입력 값을 받을 때까지 반복합니다.
    while (!isValidSelectedInputValue) {
      printerHandler.print("[📣] 메뉴를 선택해 주세요: "); // 사용자에게 메뉴 선택을 요청하는 메시지를 출력합니다.
      selectedInputValue = inputHandler.inputValue(); // 사용자의 입력을 받습니다.
      isValidSelectedInputValue = validateSelectedInputValue(selectedInputValue); // 입력 값의 유효성을 검사합니다.

      // 입력 값이 유효하지 않으면 오류 메시지를 출력하고 반복합니다.
      if (!isValidSelectedInputValue) {
        printerHandler.print("잘못된 메뉴를 입력했습니다.");
      }
    }

    return selectedInputValue; // 유효한 입력 값을 반환합니다.
  }

  // 입력된 값이 메뉴 선택에 대한 유효한 값인지 확인하는 메서드입니다.
  private boolean validateSelectedInputValue(String selectedInputValue) {
    // 입력 값이 숫자이거나 '+' 기호인 경우에만 true를 반환합니다.
    return selectedInputValue.matches("^[0-9]\\d*$") || selectedInputValue.matches("^[+]$");
  }

  // 주어진 상품을 장바구니에 추가하는 메서드입니다.
  private void addProductToCart(Product product) {
    // 새로운 CartItem 객체를 생성하여 장바구니에 추가합니다.
    cartHandler.addProductToCart(new CartItem(product, 1));

    // 상품이 장바구니에 추가되었다는 메시지를 출력합니다.
    printerHandler.print(String.format(DisplayMessages.ITEM_ADDED_TO_CART.getText(), product.getName()));
  }

  // 주문을 처리하고, 사용자에게 추가 주문 여부를 확인하는 메서드입니다.
  private boolean orderMenuItem() {
    // 장바구니의 모든 항목을 가져옵니다.
    Map<Integer, CartItem> cartItems = cartHandler.getAllCartItems();
    // 장바구니의 총 금액을 계산합니다.
    int originalPrice = cartHandler.calculateTotalPrice();
    // 할인이 적용된 금액을 계산합니다.
    int discountPrice = orderHandler.payment();

    // 주문 관련 메시지를 출력합니다.
    printerHandler.print(DisplayMessages.ORDER_TITLE.getText());
    printerHandler.print(DisplayMessages.ORDER_DETAIL.getText());
    printerHandler.print(DisplayMessages.SEPARATOR.getText());
    // 장바구니의 모든 항목을 출력합니다.
    cartItems.forEach((key, item) -> displayCartItem(item));
    printerHandler.print(DisplayMessages.SEPARATOR.getText());
    // 총 금액과 할인 적용 금액을 출력합니다.
    printerHandler.printf("금액 합계      : %d원", originalPrice);
    printerHandler.printf("할인 적용 금액 : %d원", discountPrice);
    // 장바구니를 비웁니다.
    cartHandler.emptyCart();

    // 사용자에게 추가 주문 여부를 묻습니다.
    printerHandler.print("계속 주문하시겠습니까? (1)_예 (2)_아니오");

    // 사용자의 입력을 받아 처리합니다.
    while (true) { // 무한 루프로 변경하여 유효한 입력을 받을 때까지 반복합니다.
      String input = inputHandler.inputValue();
      if ("1".equals(input)) {
        return false; // 사용자가 추가 주문을 원하지 않으면 false를 반환합니다.
      } else if ("2".equals(input)) {
        return true; // 사용자가 추가 주문을 원하면 true를 반환합니다.
      } else {
        // 유효하지 않은 입력을 받은 경우 오류 메시지를 출력합니다.
        printerHandler.print("잘못된 입력입니다. 1 또는 2를 입력해주세요.");
      }
    }
  }

  // 주어진 상품이 실제로 존재하는지 확인하는 메서드입니다.
  private static boolean existMenuItem(Product product) {
    // 상품 객체가 null이 아니면 true, 그렇지 않으면 false를 반환합니다.
    return product != null;
  }

}
