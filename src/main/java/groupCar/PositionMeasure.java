package groupCar;

import groupCar.singleCar.Car;

import java.util.ArrayList;
import java.util.List;

//전진시 한칸 이동
public class PositionMeasure {
  private List<Car> cars;
  private final CarList carList;

  public PositionMeasure(CarList carList) {
    this.carList = carList;
    this.cars = carList.getCarList();
  }

  //모든차 전진시 한칸이동
  public void moveAllCars() {
    for (Car car : cars) {
      System.out.println(car.getName() + " 이동 전 위치: " + car.getPositionScore());
      car.validateMoveCar();
      System.out.println(car.getName() + " 이동 후 위치: " + car.getPositionScore());
    }
  }

  //차가 5대이상일경우 전진시 4-9에해당하면 한칸이동
  public List<Integer> moveFirstFiveCars() {
    List<Car> cars = carList.getCarList();
    List<Integer> lastScores = new ArrayList<>();

    for (int i = 0; i < cars.size(); i += 5) {
      List<Car> subList = cars.subList(i, Math.min(i + 5, cars.size()));

      for (Car car : subList) {
        car.validateMoveCar();
        lastScores.add(car.getPositionScore());
      }
    }
    return lastScores;
  }
}

