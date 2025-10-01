package carGame.carV3;

import groupCar.CarList;
import io.ConsoleInputHandler;
import io.ConsoleOutputHandler;
import playGame.RacingCarGame;

import java.util.HashMap;

public class CarV3Application {

  public static void main(String[] args) {
    ConsoleInputHandler inputHandler = new ConsoleInputHandler(new HashMap<>());
    ConsoleOutputHandler outputHandler = new ConsoleOutputHandler(inputHandler);
    String input = outputHandler.showCarName();
    CarList carList = new CarList().initialize(input);
    RacingCarGame racingCarGame = new RacingCarGame(inputHandler, outputHandler, carList);
    racingCarGame.run();
  }

}
