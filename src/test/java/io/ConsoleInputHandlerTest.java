package io;

import groupCar.singleCar.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleInputHandlerTest {
  private Map<String, Car> carMap = new HashMap<>();
  private List<Car> carList = new ArrayList<>(carMap.values());

  @BeforeEach
  public void setUp() {
    carMap = new HashMap<>();
    carMap.put("tesla", new Car("tesla", 0));
    carMap.put("baba", new Car("baba", 0));
  }

  @Test
  @DisplayName("사용자가 자동차 이름을 입력하여 이름을 생성한다.")
  public void makeCarNameByUserInput() {
    //given
    String input = "mango, apple, bono\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    //when
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    String carName = inputHandler.makeCarNameFromUser();
    inputHandler.runGame(input);

    //then
    assertThat(carName).isEqualTo("mango, apple, bono");
    assertThat(carMap).containsKeys("mango", "apple", "bono");
  }

  @Test
  @DisplayName("자동차 이동 횟수를 생성한다.")
  public void checkTurnCountByUser() {
    //given
    InputStream testInput = new ByteArrayInputStream("5\n".getBytes());
    System.setIn(testInput);

    //when
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    int carTurnCount = inputHandler.carTurnCountFromUser();

    //then
    assertThat(carTurnCount).isEqualTo(5);
  }

  @ParameterizedTest
  @ValueSource(ints = {4, 5, 6, 7, 8, 9})
  @DisplayName("최소4~최대9 입력시 전진 여부 테스트")
  public void checkMoveCountFromUser(int input) {
    //given
    InputStream testInput = new ByteArrayInputStream((input + "\n").getBytes());
    System.setIn(testInput);

    //when
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    int moveCount = inputHandler.moveCountFromUser();

    //then
    assertThat(moveCount).isEqualTo(input);
  }
}
