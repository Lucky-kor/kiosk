package com.codestates.seb.burgerqueen.configuration;

import com.codestates.seb.burgerqueen.cart.CartHandler;
import com.codestates.seb.burgerqueen.cart.CartRepository;
import com.codestates.seb.burgerqueen.cart.InMemoryCartRepository;
import com.codestates.seb.burgerqueen.discount.Discount;
import com.codestates.seb.burgerqueen.discount.discountCondition.CozDiscountCondition;
import com.codestates.seb.burgerqueen.discount.discountCondition.DiscountCondition;
import com.codestates.seb.burgerqueen.discount.discountCondition.KidDiscountCondition;
import com.codestates.seb.burgerqueen.discount.discountPolicy.FixedAmountDiscountPolicy;
import com.codestates.seb.burgerqueen.discount.discountPolicy.FixedRateDiscountPolicy;
import com.codestates.seb.burgerqueen.menu.InMemoryMenuRepository;
import com.codestates.seb.burgerqueen.menu.MenuHandler;
import com.codestates.seb.burgerqueen.menu.MenuRepository;
import com.codestates.seb.burgerqueen.module.InputHandler;
import com.codestates.seb.burgerqueen.module.PrinterHandler;
import com.codestates.seb.burgerqueen.order.OrderHandler;

import java.util.Scanner;


public class AppConfigurer {

    public CartHandler cartHandler() {
        return new CartHandler(cartRepository());
    }

    public CartRepository cartRepository() {
        return new InMemoryCartRepository();
    }

    public MenuHandler menuHandler() {
        return new MenuHandler(menuRepository());
    }

    public MenuRepository menuRepository() {
        return new InMemoryMenuRepository();
    }

    public Discount discount() {
        return new Discount(
            new DiscountCondition[]{
                new CozDiscountCondition(new FixedRateDiscountPolicy(10)),
                new KidDiscountCondition(new FixedAmountDiscountPolicy(500))
            });
    }

    public OrderHandler orderHandler() {
        return new OrderHandler(cartHandler(), discount());
    }

    public PrinterHandler printerHandler() {
        return new PrinterHandler();
    }

    public InputHandler inputHandler() {
        return new InputHandler(new Scanner(System.in));
    }
}
