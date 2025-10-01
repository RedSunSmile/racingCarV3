package strategy.gameMergeStrategy;

import groupCar.singleCar.Car;
import java.util.ArrayList;
import java.util.List;

public class MergeSortScoreStrategy implements MergeSortStrategy {
  public List<Car> mergeSortCars(List<Car> cars) {
    if (cars.size() <= 1) {
      return cars;
    }
    int midCar = cars.size() / 2;//분할정복쓰기
    List<Car> left = mergeSortCars(cars.subList(0, midCar));//왼쪽정렬
    List<Car> right = mergeSortCars(cars.subList(midCar, cars.size()));//오른쪽정렬
    return merge(left, right);//병합
  }

  private List<Car> merge(List<Car> left, List<Car> right) {
    List<Car> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < left.size() && j < right.size()) {
      if (left.get(i).getPositionScore() <= right.get(j).getPositionScore()) {
        result.add(left.get(i++));
      }
      result.add(right.get(j++));
    }

    while (i < left.size())
      result.add(left.get(i++));

    while (j < right.size())
      result.add(right.get(j++));
    return result;
  }
}


