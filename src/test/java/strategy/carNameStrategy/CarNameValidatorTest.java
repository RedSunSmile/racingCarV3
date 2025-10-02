package strategy.carNameStrategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarNameValidatorTest {

  private final CarNameStrategy carNameStrategy = new CarNameValidator(1, 5);

  @Test
  @DisplayName("사용자가 입력할수 있는 차이름은 최대 5자이하입니다.")
  void validMaxScopeOfCarName() {
    assertThat(carNameStrategy.isValid("5")).isTrue();
  }

  @Test
  @DisplayName("사용자가 입력가능한 차이름은 최소 1입니다.")
  void numberBelowRangeOfUserInput() {
    CarNameValidator carNameValidator = new CarNameValidator(1, 5);

    assertThat(carNameValidator.isValid(String.valueOf(1)));
    assertThat(carNameValidator.isValid(String.valueOf(5)));
  }
}
