package com.codestates.seb.burgerqueen.configuration;

import com.codestates.seb.burgerqueen.menu.InMemoryMenuRepository;
import com.codestates.seb.burgerqueen.menu.MenuHandler;
import com.codestates.seb.burgerqueen.menu.MenuRepository;
import com.codestates.seb.burgerqueen.menu.domain.Product;
import com.codestates.seb.burgerqueen.menu.domain.ProductType;
import com.codestates.seb.burgerqueen.menu.domain.SetMenu;
import com.codestates.seb.burgerqueen.menu.domain.SingleMenu;

import java.util.*;
import java.util.stream.Collectors;

public class Initializer {
  private final AppConfigurer appConfigurer;

  public Initializer() {
    this.appConfigurer = new AppConfigurer();
    createMenu();
  }

  private void createMenu() {
    List<Product> productItemList = new ArrayList<>(List.of(
        new SingleMenu(1, "새우버거", 3500, 500, ProductType.HAMBURGER),
        new SingleMenu(2, "치킨버거", 4000, 600, ProductType.HAMBURGER),
        new SingleMenu(3, "감자튀김", 1000, 300, ProductType.SIDE),
        new SingleMenu(4, "어니언링", 1000, 300, ProductType.SIDE),
        new SingleMenu(5, "코카콜라", 1000, 200, ProductType.DRINK),
        new SingleMenu(6, "제로콜라", 1000, 0, ProductType.DRINK)
    ));

    productItemList.add(new SetMenu(7, "새우버거 세트", ProductType.SET, new ArrayList<>() {{
      add(productItemList.get(0)); // 새우버거
    }}));

    productItemList.add(new SetMenu(8, "새우버거 세트", ProductType.SET, new ArrayList<>() {{
      add(productItemList.get(1)); // 새우버거
    }}));

    MenuRepository menuRepository = new InMemoryMenuRepository();

//    for(Product product : productItemList) {
//      menuRepository.save(product);
//    }
    productItemList.stream().forEach(menuRepository::save);
  }
}
