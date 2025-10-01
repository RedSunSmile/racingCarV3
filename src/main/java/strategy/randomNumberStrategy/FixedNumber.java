package strategy.randomNumberStrategy;

public class FixedNumber implements RandomNumber {
  private final int fixedNumber;

  public FixedNumber(int fixedNumber) {
    this.fixedNumber = fixedNumber;
  }

  @Override
  public int generate() {
    return fixedNumber;
  }
}
