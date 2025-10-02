package strategy.movingStrategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomMoveStrategyTest {
  private final MovingStrategy movingStrategy = new RandomMoveStrategy(4, 9);

  @Test
  @DisplayName("자동차 전진 이동 숫자값이 4이상일 경우 true 반환한다.")
  public void possibleCarMoveNumbers() {

    assertThat(movingStrategy.isMove(4)).isTrue();
    assertThat(movingStrategy.isMove(9)).isTrue();
  }

  @Test
  @DisplayName("자동차 전진 이동 숫자값이 4미만일 경우 false 반환한다.")
  public void impossibleCarMoveNumbers() {
    assertThat(movingStrategy.isMove(3)).isFalse();
    assertThat(movingStrategy.isMove(0)).isFalse();
  }
}
