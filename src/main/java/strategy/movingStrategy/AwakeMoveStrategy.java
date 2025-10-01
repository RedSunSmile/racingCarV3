package strategy.movingStrategy;

//자동차 사용자 턴시도 횟수
public class AwakeMoveStrategy implements MovingStrategy {
  public boolean isMove(int count) {
    return count >= 0 && count <= 5;
  }
}
