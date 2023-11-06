package com.codestates.seb.burgerqueen;

import com.codestates.seb.burgerqueen.cart.CartHandler;
import com.codestates.seb.burgerqueen.configuration.AppConfigurer;
import com.codestates.seb.burgerqueen.configuration.Initializer;
import com.codestates.seb.burgerqueen.menu.MenuHandler;
import com.codestates.seb.burgerqueen.module.InputHandler;
import com.codestates.seb.burgerqueen.module.PrinterHandler;
import com.codestates.seb.burgerqueen.order.OrderHandler;

public class BugerQueenApplicationStart {
  private static PrinterHandler printerHandler;
  private static InputHandler inputHandler;
  private static MenuHandler menuHandler;
  private static CartHandler cartHandler;
  private static OrderHandler orderHandler;

  public static void main(String[] args) {
    initApplication();
    BurgerQueenApplication burgerQueenApplication =
        new BurgerQueenApplication(printerHandler,
            inputHandler,
            menuHandler,
            cartHandler,
            orderHandler);
    burgerQueenApplication.run();
  }

  private static void initApplication() {
    Initializer initializer = new Initializer();
    AppConfigurer appConfigurer = new AppConfigurer();

    printerHandler = appConfigurer.printerHandler();
    inputHandler = appConfigurer.inputHandler();
    menuHandler = appConfigurer.menuHandler();
    cartHandler = appConfigurer.cartHandler();
    orderHandler = appConfigurer.orderHandler();
  }
}
