package io;


import carException.CarMoveCountException;
import groupCar.CarList;
import groupCar.singleCar.Car;

import java.util.*;

import static java.lang.System.in;

public class ConsoleInputHandler {
  private Scanner SCANNER = new Scanner(in);
  private final Map<String, Car> carMap;
  private final List<Car> carList;

  public ConsoleInputHandler(Map<String, Car> carMap) {
    this.carMap = carMap;
    this.carList = new ArrayList<>(carMap.values());
    this.SCANNER = new Scanner(in);
  }

  public String makeCarNameFromUser() {
    String input = SCANNER.nextLine();
    System.out.println("입력받음: " + input);
    return input;
  }

  public void runGame(String input) {
    if (input == null || input.isBlank()) {
      System.out.println("자동차 이름 입력이 비어있습니다. 다시 시작해주세요.");
      return;
    }
    String[] names = input.split(",");
    for (String name : names) {
      String trimmedName = name.trim();
      Car car = new Car(trimmedName, 0);
      car.validateNameLength(trimmedName);
      carMap.put(trimmedName, car);
    }
  }

  public int carTurnCountFromUser() {
    System.out.println("이동 횟수를 입력해주세요(최대5): ");
    int count = SCANNER.nextInt();
    SCANNER.nextLine();
    return count;
  }

  public int moveCountFromUser() {
    int move = 0;
    boolean valid = false;
    System.out.println("전진 회수를 입력해주세요(최소4~최대9): ");
    while (! valid) {
      try {
        String line = SCANNER.nextLine();
        move = Integer.parseInt(line);
        System.out.println("입력값: " + move);
        for (Car car : carMap.values()) {
          car.validateMoveCar();
        }
        valid = true;
      } catch (InputMismatchException e) {
        System.out.println("숫자만 입력");
        SCANNER.nextLine();
      } catch (CarMoveCountException e) {
        System.out.println(e.getMessage());
      }
    }
    return move;
  }
}


