package groupCar;

import carException.CarMoveCountException;
import groupCar.singleCar.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.randomNumberStrategy.FixedNumber;
import strategy.randomNumberStrategy.RandomNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionMeasureTest {
  private List<Car> carList = new ArrayList<>();
  private PositionMeasure positionMeasure;

  @BeforeEach
  void setUp() {
    RandomNumber fixedRandom = new FixedNumber(5);
    CarList cars = createCarList(3, fixedRandom);
    positionMeasure = new PositionMeasure(cars);
  }

  private CarList createCarList(int size, RandomNumber fixedRandom) {
    List<Car> cars = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      cars.add(new Car("Car" + i, fixedRandom));
    }
    return new CarList(cars);
  }

  @Test
  @DisplayName("자동차가 5대가 모일경우 각자동차당 전진점수 4-9에 해당하는 경우 한칸 전진하는 테스트를 한다.")
  void testThreeCarsFixedRandom() {
    RandomNumber fixedRandom = new FixedNumber(5);
    CarList carList = createCarList(5, fixedRandom);
    PositionMeasure positionMeasure = new PositionMeasure(carList);
    List<Integer> expectedScores = List.of(5, 5, 5, 5, 5);
    List<Integer> actualScores = positionMeasure.moveFirstFiveCars();
    assertEquals(expectedScores, actualScores);
  }

  @Test
  @DisplayName("전진하는 4-9에해당하는 조건을 벗어난 숫자일 경우 예외발생을 테스트한다")
  void testCarMoveWhenRandomNumber() {
    //given
    Car car = new Car("Test", new FixedNumber(10));

    //when & then
    assertThatExceptionOfType(CarMoveCountException.class)
      .isThrownBy(() -> car.validateMoveCar())
      .withMessageContaining("전진은 4이상부터 9이하까지 입력가능합니다.");
  }

  @Test
  @DisplayName("전진하는 4-9에 해당하지 않는 범위에 헤당하는 고정 랜덤값 3을 반환하는 생성기 테스트한다.")
  void impossibleCarMoveWhenRandomNumberIsNow() {
    Car car = new Car("Test", new FixedNumber(3));
    car.validateMoveCar();
    assertEquals(0, car.getPositionScore());
  }

  @Test
  @DisplayName("차목록에 대한 전진 숫자범위 4-9이면 한칸 전진, 1-3이면 전진없음을 테스트를 한다.")
  void testCarListMoveAllCars() {
    Car car1 = new Car("Car1", new FixedNumber(3));
    Car car2 = new Car("Car2", new FixedNumber(6));
    CarList carGroups = new CarList(Arrays.asList(car1, car2));
    PositionMeasure positionMeasure = new PositionMeasure(carGroups);
    positionMeasure.moveAllCars();

    assertEquals(0, car1.getPositionScore(), "car1은 전지하지않고 그대로 유지한다.");
    assertEquals(6, car2.getPositionScore(), "car2는 전진한다");
  }
}
