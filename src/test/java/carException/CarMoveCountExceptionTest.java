package carException;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.movingStrategy.AwakeMoveStrategy;
import strategy.movingStrategy.MovingStrategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarMoveCountExceptionTest {
  String countError = "abc";
  CarMoveCountException carMoveCountException = new CarMoveCountException(countError);
  MovingStrategy movingStrategy = new AwakeMoveStrategy();

  @Test
  @DisplayName("사용자가 입력한 자동차 이동횟수는 숫자여야 합니다.")
  void checkInputNumbersFromUsers() {
    //given
    String carName = "apple";

    //when & then
    assertThatThrownBy(() -> {
      if (! movingStrategy.equals(carName)) {
        throw new CarMoveCountException("사용자는 숫자만 입력가능합니다.");
      }
    }).isInstanceOf(CarMoveCountException.class)
      .hasMessageContaining("숫자만 입력가능");
  }
}

