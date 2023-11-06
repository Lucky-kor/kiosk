package com.codestates.seb.burgerqueen.module;

import java.util.Scanner;

public class InputHandler {
  private final Scanner scanner;

  public InputHandler(Scanner scanner) {
    this.scanner = scanner;
  }

  public String inputValue() {
    return scanner.nextLine().trim();
  }
}
