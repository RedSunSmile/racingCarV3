package groupCar.singleCar;


import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.carNameStrategy.CarNameStrategy;
import strategy.carNameStrategy.CarNameValidator;
import strategy.movingStrategy.AwakeMoveStrategy;
import strategy.movingStrategy.MovingStrategy;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CarTest {

  private MovingStrategy turnMove = new AwakeMoveStrategy();


  @Test
  @DisplayName("자동차 이름을 생성합니다.")
  public void plusCarName() {
    //given
    List<String> carNames = List.of("bongo", "mango", "apple");
    CarNameStrategy carNameStrategy = new CarNameValidator(1, 5);
    //when
    boolean isValid = carNames.stream()
      .allMatch(carNameStrategy::isValid);

    Car car = new Car(carNames.get(0), 0);

    List<Integer> lengthCarNames = carNames.stream()
      .map(String::length)
      .collect(Collectors.toList());

    //then
    assertThat(isValid).isTrue();
    assertThat(carNames.size()).isEqualTo(3);
    assertThat(lengthCarNames).containsExactly(5, 5, 5);
  }

  @Test
  @DisplayName("사용자가 자동차를 몇 번 이동할 것인지 생성합니다.")
  public void turnCountOfCarMove() {
    //given
    List<Integer> moveCount = List.of(1, 2, 3, 4, 5);
    List<Integer> overCount = List.of(6, 7, 8, 9);

    //when & then
    boolean distance = moveCount.stream()
      .allMatch(turnMove::isMove);

    for (Integer over : overCount) {
      AssertionsForClassTypes.assertThat(turnMove.isMove(over)).isFalse();
    }
    assertThat(distance).isTrue();
  }

  @Test
  @DisplayName("자동차의 전진은 난수중에 0부터 9사이 4이상부터 한칸 전진한다.")
  public void MovementCarOfNumbers() {
    //given
    List<Integer> speedPlus = List.of(4, 5, 6, 7, 8, 9);
    List<Integer> speedStay = List.of(0, 1, 2, 3);

    //when
    boolean possibleMove = speedPlus.stream()
      .allMatch(plus -> plus >= 4);

    boolean stayHere = speedStay.stream()
      .allMatch(stay -> stay <= 3);

    //then
    assertThat(possibleMove).isTrue();
    assertThat(stayHere).isTrue();
  }
}
