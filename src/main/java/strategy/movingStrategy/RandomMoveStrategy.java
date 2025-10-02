package strategy.movingStrategy;


public class RandomMoveStrategy implements MovingStrategy {
  private final int min;
  private final int max;

  public RandomMoveStrategy(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public boolean isMove(int moveCount) {
    return moveCount >= min && moveCount <= max;
  }

}
