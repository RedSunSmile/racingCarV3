package io;

import groupCar.CarList;
import groupCar.singleCar.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleOutputHandlerTest {

  @Test
  @DisplayName("자동차 이름 생성 테스트를 한다.")
  public void testCarNames() {
    // //given
    String input = "mango, apple, banana\n";
    InputStream testIn = new ByteArrayInputStream(input.getBytes());
    System.setIn(testIn);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Map<String, Car> carMap = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler output = new ConsoleOutputHandler(inputHandler);

//when
    output.showCarName();

//then
    assertThat(outContent.toString()).contains("mango, apple, banana");
    System.setOut(System.out);
  }

  @Test
  @DisplayName("자동차 회전이동을 출력하는 테스트를 한다.")
  public void testDisplayTurnDistanceOfScope() {
    //given
    InputStream testInput1 = new ByteArrayInputStream("1\n5\n".getBytes());
    System.setIn(testInput1);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Map<String, Car> carMap = new HashMap<>();
    carMap.put("testCar", new Car("test1", 1));
    carMap.put("testCar", new Car("test1", 5));

    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler output = new ConsoleOutputHandler(inputHandler);

    //when
    output.displayTurnDistance(1);
    output.displayTurnDistance(5);

    //then
    String outputString = outContent.toString().trim();
    assertThat(outputString).contains("1");
    assertThat(outContent.toString()).contains("5");
  }

  @Test
  @DisplayName("자동차 전진횟수 시도하는 테스트를 한다.")
  public void testDisplayTurnDistanceOfInput5() {
    //given
    System.setIn(new ByteArrayInputStream("4\n9\n".getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Map<String, Car> carMap = new HashMap<>();
    carMap.put("testCar", new Car("test1", 9));

    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap);
    ConsoleOutputHandler output = new ConsoleOutputHandler(inputHandler);

    //when
    output.displayMoveCount(9);

    //then
    assertThat(outContent.toString()).contains("9");
  }

  @Test
  @DisplayName("단독우승자를 출력하는 테스트를 한다.")
  public void testSingleWinList() {
    //given
    Map<String, Car> carMap1 = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap1);
    ConsoleOutputHandler output = new ConsoleOutputHandler(inputHandler);

    List<Car> cars = Arrays.asList(
      new Car("mango", 60),
      new Car("bom", 93),
      new Car("grape", 95),
      new Car("apple", 93),
      new Car("bono", 70)
    );

//when
    List<Car> singleWinner = new CarList(cars).makeOnlyWinList(new ArrayList<>(cars));
    List<Car> multipleWinners = new CarList(cars).makeWinMultipleList(new ArrayList<>(cars));

    Map<String, Car> singleWinnerMap = singleWinner.stream()
      .collect(Collectors.toMap(Car::getName, Function.identity()));

    output.printWinLists(singleWinner, multipleWinners);
    output.displayCarPosition(cars);

    //then
    assertThat(singleWinnerMap).hasSize(1);
    assertThat(singleWinnerMap.containsKey("grape")).isTrue();
  }

  @Test
  @DisplayName("공동우승자를 출력하는 테스트를 한다.")
  public void testSMultipleWinLists() {
    Map<String, Car> carMap1 = new HashMap<>();
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(carMap1);
    ConsoleOutputHandler output = new ConsoleOutputHandler(inputHandler);

    List<Car> cars = Arrays.asList(
      new Car("mango", 60),
      new Car("bom", 93),
      new Car("grape", 95),
      new Car("apple", 95),
      new Car("bono", 70)
    );

//when
    List<Car> singleWinner = new CarList(cars).makeOnlyWinList(new ArrayList<>(cars));
    List<Car> multipleWinners = new CarList(cars).makeWinMultipleList(new ArrayList<>(cars));

    Map<String, Car> multipleWinnerMap = multipleWinners.stream()
      .collect(Collectors.toMap(Car::getName, Function.identity()));

    output.printWinLists(singleWinner, multipleWinners);
    output.displayCarPosition(cars);

    //then
    assertThat(multipleWinnerMap).hasSize(2);
    assertThat(multipleWinnerMap.containsKey("grape")).isTrue();
    assertThat(multipleWinnerMap.containsKey("apple")).isTrue();
  }
}