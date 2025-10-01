package strategy.movingStrategy;

import java.util.Random;

public class AwakeStopStrategy implements MovingStrategy {
  private final Random random = new Random();
  private final int min;
  private final int max;

  public AwakeStopStrategy(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public boolean isMove(int count) {
    int make = random.nextInt(10);
//4미만정지
    if (make < 4) {
      return false;
    }
    return true;//4이상 이동
  }
}