package groupCar;

import carException.CarNameException;
import groupCar.singleCar.Car;
import io.ConsoleInputHandler;
import strategy.gameMergeStrategy.MergeSortScoreStrategy;
import strategy.gameMergeStrategy.MergeSortStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class CarList {
  private PositionMeasure positionMeasure;
  private List<Car> cars;

  public CarList() {
    this.cars = new ArrayList<>();
    this.positionMeasure = new PositionMeasure(this);
  }

  public CarList(List<Car> cars) {
    if (cars == null) {
      this.cars = new ArrayList<>();
    }
    this.cars = cars;
    this.positionMeasure = new PositionMeasure(this);
  }

  public List<Car> getCarList() {
    return Collections.unmodifiableList(cars);
  }

  public CarList initialize(String input) {
    List<String> nameList = Arrays.stream(input.split(","))
      .map(String::trim)
      .collect(Collectors.toList());

    List<Car> cars = new ArrayList<>();
    for (String name : nameList) {
      Car car = new Car(name, 0);
      car.validateNameLength(name);
      cars.add(car);
    }
    return new CarList(cars);
  }

  //차략목록 반환
  public List<Car> printCarList(List<Car> cars) {
    return cars;
  }


  //자동차 이름 등록자 여러명 생성
  public List<String> getCarNames() {
    return cars.stream()
      .map(Car::getName)
      .flatMap(names -> Arrays.stream(names.split(",")))
      .map(String::trim)
      .collect(Collectors.toList()
      );
  }

  //이름예외처리
  public List<String> validateNameLength(List<String> names) {
    if (names == null || names.isEmpty()) {
      throw new IllegalArgumentException("자동차 이름 리스트가 비었습니다.");
    }
    for (String name : names) {
      if (name == null || name.length() < 1 || name.length() > 5) {
        throw new CarNameException("자동차 이름길이는 1이상부터 5이하까지 입력가능합니다.");
      }
    }
    return names;
  }

  //자동차목록 이름길이확인
  public List<Integer> getAllNameLength(List<Car> carList) {
    return carList.stream()
      .map(Car::getName)
      .map(String::length)
      .collect(Collectors.toList());
  }

  public void validateTurnCount(int count) {
    count = 5;
  }

  //각 자동차 전체 이동회수 전달, 자동차 5개이상목록일 경우부터 이동횟수 확인
  public int validateTurnCount() {
    Map<String, Car> carMap = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    int turn = 0;
    while (true) {
      try {
        turn = inputHandler.carTurnCountFromUser();
        if (turn < 1 || turn > 5) {
          System.out.println("1 이상 5 이하의 숫자를 입력하세요.");
          continue;
        }
        break;
      } catch (Exception e) {
        System.out.println("유효한 숫자를 입력하세요.");
      }
    }
    return turn;
  }

  //자동차목록에 대한 회전시도 숫자 확인
  public List<Integer> getPositionScores(List<Car> cars) {
    return cars.stream()
      .map(Car::getPositionScore)
      .collect(Collectors.toList());
  }

  //모든 자동차(5대미만) 자동차 회전시도회수 예외처리
  public List<Integer> validateTurnCountAllCars(int moveTurn) {
    validateTurnCount(moveTurn);
    List<Integer> validTurns = new ArrayList<>();
    for (Car car : cars) {
      car.validateTurnCount(moveTurn);
      validTurns.add(moveTurn);
    }
    return validTurns;
  }

  //모든 자동차(5대미만) 자동차 회전시도등록
  private List<Integer> turnCountsUnderFiveCars(int moveTurn) {
    List<Integer> validTurns = new ArrayList<>();
    for (Car car : cars) {
      car.validateTurnCount(moveTurn);
      validTurns.add(moveTurn);
    }
    return validTurns;
  }

  //전체 자동차목록 회전이동검증,5대씩 처리하기
  public List<Integer> validateAndGetScores(List<Integer> turn) {
    List<Integer> allTurnScores = new ArrayList<>();
    int batchSize = 5;
    for (int i = 0; i < Math.ceil((double) cars.size() / batchSize); i++) {
      int start = i * batchSize;
      int end = Math.min(start + batchSize, cars.size());

      List<Car> subSet = cars.subList(start, end);
      List<Integer> subTurn = turn.subList(start, end);

      for (int j = 0; j < subSet.size(); j++) {
        Car car = subSet.get(j);
        int turnCount = subTurn.get(j);
        car.performTurn(turnCount);
      }
      List<Integer> scores = getPositionScores(subSet);
      allTurnScores.addAll(scores);
    }
    return allTurnScores;
  }

  //우승자 목록 알려주기//단독우승
  public List<Car> makeOnlyWinList(List<Car> carsList) {
    for (Car car : carsList) {
      int score = car.makeRandomNumber();
      car.savePositionScore(score);
    }

    int maxScore = cars.stream()
      .mapToInt(Car::getPositionScore)
      .max()
      .orElse(Integer.MIN_VALUE);

    if (maxScore == Integer.MIN_VALUE) {
      // 점수 자체가 없는 경우 예외 처리용
      System.out.println("최종 단독 우승자: 단독 우승자가 없습니다.");
      return new ArrayList<>();
    }

    List<Car> winners = cars.stream()
      .filter(car1 -> car1.getPositionScore() == maxScore)
      .collect(Collectors.toList());
//    carsList.get(0).savePositionScore(maxScore);

    System.out.println(winners.stream()
      .map(car1 -> car1.getName() + " : " + car1.getPositionScore())
      .collect(Collectors.joining(", ")));
    return winners;
  }

  //우승자 목록 알려주기//복합우승
  public List<Car> makeWinMultipleList(List<Car> cars) {
    MergeSortStrategy mergeSortStrategy = new MergeSortScoreStrategy();
    List<Car> winners = makeOnlyWinList(cars);
    List<Car> sortedWinners = mergeSortStrategy.mergeSortCars(winners);
    return sortedWinners;
  }
}