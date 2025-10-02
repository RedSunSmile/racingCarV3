package io;

import groupCar.singleCar.Car;

import java.util.Map;

public class TestInputHandler extends ConsoleInputHandler {
  private final int carTurnCount;
  private final int moveCount;

  public TestInputHandler(Map<String, Car> carMap, int carTurnCount, int moveCount) {
    super(carMap);
    this.carTurnCount = carTurnCount;
    this.moveCount = moveCount;
  }

  @Override
  public int carTurnCountFromUser() {
    return carTurnCount;
  }

  @Override
  public int moveCountFromUser() {
    return moveCount;
  }
}
