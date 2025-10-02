package groupCar;

import carException.CarNameException;
import carException.CarTurnCountException;
import groupCar.singleCar.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.gameMergeStrategy.MergeSortScoreStrategy;
import strategy.gameMergeStrategy.MergeSortStrategy;
import strategy.randomNumberStrategy.FixedNumber;
import strategy.randomNumberStrategy.RandomNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarListTest {
  private MergeSortStrategy mergeSortStrategy = new MergeSortScoreStrategy();

  @BeforeEach
  void setUp() {
    List<Car> carList = new ArrayList<>();
    CarList cars = new CarList(carList);
    RandomNumber fixedRandom = new FixedNumber(5);
    cars = createCarList(3, fixedRandom);
  }

  private CarList createCarList(int size, RandomNumber fixedRandom) {
    List<Car> cars = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      cars.add(new Car("Car" + i, fixedRandom));
    }
    return new CarList(cars);
  }

  @Test
  @DisplayName("자동차 게임을 시작하는 테스트를 한다.")
  public void testInitialize() {
//given
    String names = "kakao,momo, jojo";
    CarList carList = new CarList(new ArrayList<>());

    //when
    carList = carList.initialize(names);
    List<Car> cars = carList.getCarList();

    //then
    assertThat(cars).hasSize(3);
    assertThat(cars.get(0).getName()).isEqualTo("kakao");
    assertThat(cars.get(1).getName()).isEqualTo("momo");
    assertThat(cars.get(2).getName()).isEqualTo("jojo");
  }

  @Test
  @DisplayName("자동차가 5대묶음 자동차 회전시도처리 하는 테스트를 한다 ")
  void testMultiplyOfFiveCarsByFixedRandom() {
    List<Car> cars = new ArrayList<>();
    cars.add(new Car("car1", 1));
    cars.add(new Car("car2", 2));
    cars.add(new Car("car3", 3));
    cars.add(new Car("car4", 3));
    cars.add(new Car("car5", 0));

    CarList carList = new CarList(cars);
    List<Integer> expectedScores = List.of(1, 2, 3, 3, 1);//5대가 아니면 test실패

    List<Integer> actualScores = carList.validateAndGetScores(expectedScores);
    assertEquals(expectedScores, actualScores);
  }

  @Test
  @DisplayName("자동차 이름 리스트가 비었을 때 예외 발생")
  public void testEmptyNameListThrowsException() {
    List<String> emptyList = Collections.emptyList(); // 비어있는 리스트
    CarList carGroups = new CarList(new ArrayList<>());

    assertThatExceptionOfType(IllegalArgumentException.class)
      .isThrownBy(() -> carGroups.validateNameLength(emptyList))
      .withMessageContaining("자동차 이름 리스트가 비었습니다.");
  }

  @Test
  @DisplayName("자동차 이름 길이 초과 시 예외 발생")
  public void testNameLengthInvalidThrowsException() {
    List<String> invalidNames = Arrays.asList("mangoooo", "apple");
    CarList carGroups = new CarList(new ArrayList<>());

    assertThatExceptionOfType(CarNameException.class)
      .isThrownBy(() -> carGroups.validateNameLength(invalidNames))
      .withMessageContaining("자동차 이름길이는 1이상부터 5이하까지 입력가능합니다.");
  }

  @Test
  @DisplayName("자동차목록 이름길이를 1이상부터 5이하인지 테스트한다.")
  public void checkAllNameLengthOfCars() {
    List<Car> carList = new ArrayList<>();
    CarList cars = new CarList(carList);

    //when &then
    //전체이름길이 체크
    assertThat(cars.getAllNameLength(carList)).hasSizeGreaterThanOrEqualTo(0);
    assertThat(cars.getAllNameLength(carList)).hasSizeLessThanOrEqualTo(5);
  }

  @Test
  @DisplayName("자동차 이름 여러명일 경우 생성하고 콤마로 구분합니다.")
  public void makeMultipleCarNames() {
    //given
    Car car = new Car("bongo, mango, apple", 5);
    CarList cars = new CarList(List.of(car));

    //when
    List<Car> carList = cars.printCarList(List.of(car));
    List<String> carNames = cars.getCarNames();
    //then
    assertThat(carList).containsExactly(car);
    assertThat(carNames).containsExactlyInAnyOrder("bongo", "mango", "apple");
    assertThat(carNames.stream().noneMatch(name -> name.contains(","))).isTrue();
  }

  @Test
  @DisplayName("자동차가 5대미만일 경우 예외처리 테스트")
  public void isAllowedTurningCountOfCars() {
    //given
    Car car1 = new Car("Car1", new FixedNumber(1));
    Car car2 = new Car("Car2", new FixedNumber(6));
    Car car3 = new Car("Car3", new FixedNumber(2));
    Car car4 = new Car("Car4", new FixedNumber(4));
    Car car5 = new Car("Car5", new FixedNumber(3));
    Car car6 = new Car("Car6", new FixedNumber(5));

    CarList carList = new CarList(Arrays.asList(car1, car2, car3, car4, car5, car6));
    int validTurn = 3;
    int invalidTurn = 6;


    //예외 5초과 이동횟수는 예외발생
    assertThatExceptionOfType(CarTurnCountException.class)
      .isThrownBy(() -> carList.validateTurnCountAllCars(invalidTurn))
      .withMessageContaining("자동차 이동횟수는 1이상부터 5이하까지 입력가능합니다.");
  }

  @Test
  @DisplayName("단독 우승자를 생성한다.")
  public void checkWinList() {
    //given
    List<Car> cars = Arrays.asList(
      new Car("mango", 60),
      new Car("bom", 50),
      new Car("grape", 85),
      new Car("apple", 77),
      new Car("bono", 75)
    );

    List<Car> winners = makeWinList(cars);

    //then & 단독우승
    assertThat(winners).hasSize(1);
    assertThat(winners.get(0).getName()).isEqualTo("grape");
  }

  @Test
  @DisplayName("복수 우승자를 생성한다.")
  public void checkMultipleWinList() {
    //given
    List<Car> cars = Arrays.asList(
      new Car("mango", 60),
      new Car("bom", 50),
      new Car("grape", 92),
      new Car("apple", 93),
      new Car("bono", 94)
    );

//when
    List<Car> winners = makeWinMultipleList(cars);
    List<Car> sortedWinners = mergeSortStrategy.mergeSortCars(winners);

    //then & 복수우승
    assertThat(winners).hasSize(3);
    assertThat(winners.get(0).getName()).isEqualTo("grape");
    assertThat(winners.get(1).getName()).isEqualTo("apple");
    assertThat(winners.get(2).getName()).isEqualTo("bono");
  }

  //우승자 목록 알려주기//공동우승
  public List<Car> makeWinMultipleList(List<Car> cars) {
    List<Car> winners = makeWinList(cars);
    List<Car> sortedWinners = mergeSortStrategy.mergeSortCars(winners);
    return sortedWinners;
  }

  //단독우승
  private List<Car> makeWinList(List<Car> cars) {
    int maxScore = cars.stream()
      .mapToInt(Car::getPositionScore)
      .max()
      .orElse(Integer.MIN_VALUE);

    int winningScore = Math.min(maxScore, 90);

    List<Car> winners = cars.stream()
      .filter(car -> car.getPositionScore() >= winningScore)
      .collect(Collectors.toList());
    return winners;
  }
}
