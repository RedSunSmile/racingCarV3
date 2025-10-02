package strategy.movingStrategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AwakeStopStrategyTest {
  private final MovingStrategy movingStrategy = new AwakeStopStrategy(1, 3);

  @Test
  @DisplayName("자동차 전진 이동 숫자값이 4미만일 경우 그대로 유지한다.")
  public void stayCarMove() {
    assertThat(movingStrategy.isMove(3)).isTrue();
    assertThat(movingStrategy.isMove(1)).isTrue();
  }
}
