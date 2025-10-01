package groupCar.singleCar;

import carException.CarMoveCountException;
import carException.CarNameException;
import carException.CarTurnCountException;
import strategy.carNameStrategy.CarNameStrategy;
import strategy.carNameStrategy.CarNameValidator;
import strategy.randomNumberStrategy.RandomNumber;
import strategy.randomNumberStrategy.RealRandomNumber;

import java.util.Objects;
import java.util.Random;

public class Car {
  private String name;
  private int positionScore;
  private final CarNameStrategy carNameStrategy = new CarNameValidator(1, 5);
  private RandomNumber randomNumber;

  public Car(String name, int positionScore) {
    this.name = name;
    this.positionScore = positionScore;
    this.randomNumber = new RealRandomNumber(new Random());
  }

  public Car(String name, RandomNumber randomNumber) {
    this.name = name;
    this.randomNumber = randomNumber;
    this.positionScore = 0;
  }

  //난수 생성
  public int makeRandomNumber() {
    return randomNumber.generate();
  }

  public int getPositionScore() {
    return positionScore;
  }

  public void savePositionScore(int score) {
    this.positionScore += score;
  }

  //자동차 이름생성
  public String getName() {
    return name;
  }

  //자동차 이름여부
  public void validateNameLength(String name) {
    if (name.length() < 1 || name.length() > 6) {
      throw new CarNameException("자동차 이름길이는 1이상부터 5이하까지 입력가능합니다.");
    }
  }

  //자동차이동거리
  public int validateTurnCount(int turnCount) {
    if (turnCount < 1 || turnCount > 5) {
      throw new CarTurnCountException("자동차 이동횟수는 1이상부터 5이하까지 입력가능합니다.");
    }
    return turnCount;
  }

  //자동차회전시도후 전진으로 보내기
  public void performTurn(int turnCount) {
    this.positionScore = turnCount;
  }

  //자동차 전진여부
  public void validateMoveCar() {
    int number = makeRandomNumber();
    if (number >= 4 && number <= 9) {
      savePositionScore(number);
    } else if (number >= 0 && number < 4) {
      //전진하지 않음 점수 변환없음
    } else {
      throw new CarMoveCountException("전진은 4이상부터 9이하까지 입력가능합니다.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Car car = (Car) o;
    return Objects.equals(name, car.name) && Objects.equals(carNameStrategy, car.carNameStrategy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, carNameStrategy);
  }

}
