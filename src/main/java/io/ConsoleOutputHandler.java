package io;


import groupCar.singleCar.Car;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleOutputHandler {
  private final ConsoleInputHandler inputHandler;
  private Map<String, Car> carMap;

  public ConsoleOutputHandler(ConsoleInputHandler inputHandler) {
    this.inputHandler = inputHandler;

  }

  public void showGameStartComments() {
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println("자동차경주 게임 시작합니다.!");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
  }

  public String showCarName() {
    System.out.println("자동차 이름을 생성해주세요(콤마로 구분): ");
    String input = inputHandler.makeCarNameFromUser();
    return input;
  }

  public void displayCarPosition(List<Car> cars) {
    for (Car car : cars) {
      String name = car.getName();
      int score = car.getPositionScore();
      System.out.println(name + " : " + "-".repeat(score) + " (" + score + ")");
    }
    System.out.println();
  }

  public void displayTurnDistance(int turnCount) {
    System.out.println("이동 횟수: " + turnCount);
  }

  public void displayMoveCount(int moveCount) {
    System.out.println("전진 횟수: " + moveCount);
  }

  public void printWinLists(List<Car> singleWinner, List<Car> bothWinners) {
    if (singleWinner != null && singleWinner.size() == 1) {
      Car winner = singleWinner.get(0);
      System.out.println("최종 단독 우승자: " + winner.getName() + " : " + winner.getPositionScore());
    }
    if (bothWinners != null && bothWinners.size() > 1) {
      String result = bothWinners.stream()
        .map(car -> car.getName() + " : " + car.getPositionScore())
        .collect(Collectors.joining(", "));
      System.out.println("최종 공동 우승자: " + result);
    }
    if ((singleWinner == null || singleWinner.size() != 1) && (bothWinners == null || bothWinners.size() <= 1)) {
      System.out.println("우승자가 없습니다.");
    }
  }
}
