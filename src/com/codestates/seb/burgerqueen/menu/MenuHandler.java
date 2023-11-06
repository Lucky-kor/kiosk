package com.codestates.seb.burgerqueen.menu;

import com.codestates.seb.burgerqueen.menu.domain.Product;

import java.util.ArrayList;

public class MenuHandler {

  private final MenuRepository menuRepository;

  public MenuHandler(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  public Product createMenu(Product product) {
    // 메뉴 생성 로직이 필요하다면 여기에 추가
    return menuRepository.save(product);
  }

  public Product getMenuById(int id) {
    // 특정 ID의 메뉴를 가져오는 로직이 필요하다면 여기에 추가
    return menuRepository.findById(id);
  }

  public ArrayList<Product> getAllMenus() {
    // 모든 메뉴를 가져오는 로직이 필요하다면 여기에 추가
    return new ArrayList<>(menuRepository.findAll().values());
  }
}