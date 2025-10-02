package playGame;

import groupCar.CarList;
import groupCar.singleCar.Car;
import io.ConsoleInputHandler;
import io.ConsoleOutputHandler;
import io.TestInputHandler;
import io.TestOutputHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RacingCarGameTest {
  private final List<Car> cars1 = new ArrayList<>();
  private final CarList carList = new CarList(cars1);

  @Test
  @DisplayName("자동차게임 실행후 출력 테스트를 한다.")
  public void testRun() {
    //given
    Map<String, Car> carMap = new HashMap<>();

    TestInputHandler inputHandler = new TestInputHandler(carMap, 5, 5);
    TestOutputHandler outputHandler = new TestOutputHandler(inputHandler);
    CarList carList = new CarList(new ArrayList<>());
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, carList);

    //when
    racingCarGame.run();

    //then
    List<String> outputs = outputHandler.getOutputs();
    assertThat(outputs).contains("Turn distance: 5", "Move count: 5", "Winners printed");
  }

  @Test
  @DisplayName("자동차 이름을 생성후 추가하는 테스트를 한다.")
  public void testOfCarNames() {
    //given
    List<Car> cars = Arrays.asList(new Car("tesla", 5), new Car("grape", 7));
    CarList carLisst = new CarList(cars);
    Map<String, Car> carMap = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler outputHandler = new ConsoleOutputHandler(inputHandler);
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, new CarList(cars));

    //when
    racingCarGame.addCarNames(cars);
    carMap = racingCarGame.makeCarNames(cars);

//then
    assertThat(carMap).isNotEmpty();
    assertThat(carMap).containsKeys("tesla", "grape");
  }

  @Test
  @DisplayName("자동차 입력회수만큼 회전기능을 테스트한다.")
  public void testTurnDistanceOfCar() {
    //given
    Car car = new Car("kakao", 5);

    //when
    Map<String, Car> carMap = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler outputHandler = new ConsoleOutputHandler(inputHandler);
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, carList);
    Map<String, Integer> numberMap = racingCarGame.makeTurnDistance(car.getName(), car.getPositionScore());

    //when
    assertThat(numberMap.get("kakao")).isEqualTo(5);
  }

  @Test
  @DisplayName("전진회수를 테스트한다.")
  public void testMovementOfCar() {
    //given
    List<Car> cars = Arrays.asList(new Car("tesla", 5), new Car("grape", 7));

    //when
    Map<String, Car> carMap1 = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap1);
    ConsoleOutputHandler outputHandler = new ConsoleOutputHandler(inputHandler);
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, carList);
    Map<String, Integer> carMap = racingCarGame.processMovementOfCar(carMap1);

    //then
    for (Integer moveCount : carMap.values()) {
      assertThat(moveCount).isGreaterThanOrEqualTo(0);
    }
  }

  @Test
  @DisplayName("우승목록을 생성하여 단독우승을 생성하여 테스트한다.")
  public void testWinnerListsOfSingle() {
    //given
    List<Car> cars = Arrays.asList(
      new Car("mango", 60),
      new Car("bom", 50),
      new Car("grape", 82),
      new Car("apple", 93),
      new Car("bono", 70)
    );

//when
    CarList carGroups = new CarList(new ArrayList<>(cars));
    List<Car> winners1 = carGroups.makeOnlyWinList(new ArrayList<>(cars));
    List<Car> winners2 = carGroups.makeWinMultipleList(new ArrayList<>(cars));
    Map<String, Car> carMap = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler outputHandler = new ConsoleOutputHandler(inputHandler);
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, carList);
    Map<String, Integer> winners = racingCarGame.winnersList(winners1, winners2);

    //then & 단독우승
    assertThat(winners).hasSize(1);
    assertThat(winners.containsKey("apple")).isTrue();
  }

  @Test
  @DisplayName("우승목록을 생성하여 공동우승 목록을 생성하여 테스트한다.")
  public void testWinnerListsOfBoth() {
    //given
    List<Car> cars = Arrays.asList(
      new Car("mango", 6),
      new Car("bom", 5),
      new Car("grape", 7),
      new Car("apple", 7),
      new Car("bono", 6)
    );

//when
    CarList carGroups = new CarList(new ArrayList<>(cars));
    List<Car> winners1 = carGroups.makeOnlyWinList(new ArrayList<>(cars));
    List<Car> winners2 = carGroups.makeWinMultipleList(new ArrayList<>(cars));
    Map<String, Car> carMap = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler outputHandler = new ConsoleOutputHandler(inputHandler);
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, carGroups);
    Map<String, Integer> winners = racingCarGame.winnersList(winners1, winners2);

    //then & 공동우승
    assertThat(winners).hasSize(2);
    assertThat(winners.containsKey("grape")).isTrue();
    assertThat(winners.containsKey("apple")).isTrue();
  }
}
