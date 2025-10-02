package strategy.gameMergeStrategy;

import groupCar.singleCar.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortScoreStrategyTest {

  @Test
  @DisplayName("병합정렬 전략에 대한 여러 입력에 대한 결과를 검증한다.")
  void TestMergeSort() {
//given
    List<Car> cars = Arrays.asList(
      new Car("mango", 5), new Car("bono", 2),
      new Car("mana", 3), new Car("grape", 1),
      new Car("tesla", 4)
    );

    MergeSortStrategy mergeSortStrategy = new MergeSortScoreStrategy();
    List<Car> sortedCars = mergeSortStrategy.mergeSortCars(cars);

//when
    List<Integer> expectedOrder = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> actualOrder = sortedCars.stream()
      .map(Car::getPositionScore)
      .collect(Collectors.toList());

    //then
    assertArrayEquals(expectedOrder.toArray(), actualOrder.toArray());
  }
}
