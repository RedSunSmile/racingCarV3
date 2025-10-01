package strategy.carNameStrategy;

public class CarNameValidator implements CarNameStrategy {
  private final int min;
  private final int max;

  public CarNameValidator(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public boolean isValid(String name) {
    int length = name.length();
    return length >= min && length <= max;
  }
}