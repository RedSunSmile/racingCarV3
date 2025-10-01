package strategy.gameMergeStrategy;

import groupCar.singleCar.Car;

import java.util.List;

public interface MergeSortStrategy {
  List<Car> mergeSortCars(List<Car> cars);
}
