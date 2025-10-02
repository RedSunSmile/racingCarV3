package strategy.randomNumberStrategy;

public class FixedNumber implements RandomValue {
  private final int fixedNumber;

  public FixedNumber(int fixedNumber) {
    this.fixedNumber = fixedNumber;
  }

  @Override
  public int generate() {
    return fixedNumber;
  }
}
