package strategy.randomNumberStrategy;

import java.util.Random;

public class RealRandomNumber implements RandomNumber1 {
  private Random random;

  public RealRandomNumber(Random random) {
    this.random = random;
  }

  public int generate() {
    return random.nextInt(10);
  }
}
