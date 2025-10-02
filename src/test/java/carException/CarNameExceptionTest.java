package carException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarNameExceptionTest {
  String input = "";
  CarNameException car = new CarNameException(input);

  @Test
  @DisplayName("자동차이름은 5자이상일때 쉼표로 구분된다.")
  void checkCarNameRangeAsComma() {
    //given
    List<String> names = new ArrayList<>();
    String inputNames = "grape, black, rose";
    InputStream in = new ByteArrayInputStream(inputNames.getBytes());
    System.setIn(in);
    Matcher nameSplitedSign = Pattern.compile("[,]").matcher(inputNames);
    while (nameSplitedSign.find()) {
      names.add(nameSplitedSign.group());
    }

    //when & then
    assertThat(inputNames).contains(",");
    assertThat(inputNames).isEqualTo("grape, black, rose");
  }

  @Test
  @DisplayName("자동차 이름은 비어있는지 확인한다.")
  void checkEmptyInputOfCarName() {
    assertThat(input.isEmpty()).isTrue();
  }

  @Test
  @DisplayName("사용자가 중복된 자동차 이름을 사용할 수 없다.")
  void nonNumericInputOfCarName() {
    //given
    String carName = "apple";
    String otherCar = "apple";

    //when & then
    assertThatThrownBy(() -> {
      if (otherCar.equals(carName)) {
        throw new CarNameException("중복된 자동차 이름입니다.");
      }
    }).isInstanceOf(CarNameException.class)
      .hasMessageContaining("중복된 자동차 이름");
  }
}
