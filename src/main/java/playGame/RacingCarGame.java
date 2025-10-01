package playGame;

import groupCar.CarList;
import groupCar.PositionMeasure;
import groupCar.singleCar.Car;
import io.ConsoleInputHandler;
import io.ConsoleOutputHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RacingCarGame implements Runnable {
  private final ConsoleInputHandler inputHandler;
  private final ConsoleOutputHandler outputHandler;
  private CarList carList;

  public RacingCarGame(ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler, CarList carList) {
    this.inputHandler = inputHandler;
    this.outputHandler = outputHandler;
    this.carList = carList;
  }

  public CarList getCarList() {
    return carList;
  }

  @Override
  public void run() {

    outputHandler.showGameStartComments();

    int turn = inputHandler.carTurnCountFromUser();
    int moveCount = inputHandler.moveCountFromUser();

    outputHandler.displayTurnDistance(turn);
    outputHandler.displayMoveCount(moveCount);

    PositionMeasure positionMeasure = new PositionMeasure(carList);

    for (int i = 0; i < turn; i++) {
      positionMeasure.moveAllCars();
      outputHandler.displayCarPosition(carList.getCarList());
    }

    List<Car> singleWinner = carList.makeOnlyWinList(carList.getCarList());
    List<Car> multipleWinners = carList.makeWinMultipleList(carList.getCarList());

//    // 7. 최종 우승자 출력
    outputHandler.printWinLists(singleWinner, multipleWinners);
  }

  public void addCarNames(List<Car> cars) {
    Map<String, Car> carMap = new HashMap<>();
    for (Car car : cars) {
      carMap.put(car.getName(), car);
    }
  }

  public Map<String, Car> makeCarNames(List<Car> cars) {
   return cars.stream()
     .collect(Collectors.toMap(Car::getName, Function.identity()));
  }

  //입력회수만큼 회전기능 추가반복
  public Map<String, Integer> makeTurnDistance(String carName, int count) {
    Map<String, Integer> numberMap = new HashMap<>();
    numberMap.put(carName, count);
    return new HashMap<>(numberMap);
  }

  //전진회수 반복
  public Map<String, Integer> processMovementOfCar(Map<String, Car> carMap) {
    for (Car car : carMap.values()) {
      car.validateMoveCar();
    }
    Map<String, Integer> result = new HashMap<>();
    for (Map.Entry<String, Car> entry : carMap.entrySet()) {
      result.put(entry.getKey(), entry.getValue().getPositionScore());
    }
    return result;
  }

  public Map<String, Integer> winnersList(List<Car> winners1, List<Car> winners2) {
    Map<String, Integer> winnerMap = new HashMap<>();
    for (Car car : winners1) {
      winnerMap.put(car.getName(), car.getPositionScore());
    }
    for (Car car : winners2) {
      winnerMap.put(car.getName(), car.getPositionScore());
    }
    return winnerMap;
  }
}
