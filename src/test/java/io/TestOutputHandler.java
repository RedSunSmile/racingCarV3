package io;

import groupCar.singleCar.Car;

import java.util.ArrayList;
import java.util.List;

public class TestOutputHandler extends ConsoleOutputHandler {
  private final List<String> outputs = new ArrayList<>();

  public TestOutputHandler(ConsoleInputHandler inputHandler) {
    super(inputHandler);
  }

  @Override
  public void displayTurnDistance(int turnCount) {
    outputs.add("Turn distance: " + turnCount);
  }

  @Override
  public void displayMoveCount(int moveCount) {
    outputs.add("Move count: " + moveCount);
  }

  @Override
  public void printWinLists(List<Car> singleWinner, List<Car> bothWinners) {
    outputs.add("Winners printed");
  }

  public List<String> getOutputs() {
    return outputs;
  }
}
