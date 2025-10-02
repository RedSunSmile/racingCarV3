package strategy.movingStrategy;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AwakeMoveStrategyTest {
  private final MovingStrategy movingStrategy = new AwakeMoveStrategy();

  @Test
  @DisplayName("사용자는 자동차 이동을 최소1에서 최대 5번의 이동을 입력해야 한다.")
  void possibleTurnScopeOfCarMove() {

    assertThat(movingStrategy.isMove(1)).isTrue();
    assertThat(movingStrategy.isMove(5)).isTrue();
  }

  @Test
  @DisplayName("사용자는 자동차 이동에 대한 허용범위를 벗어난 숫자를 입력하지 말아야한다.")
  void numberAboveRangeOfCarMove() {
    assertThat(movingStrategy.isMove(15)).isFalse();
  }
}
