package carException;

public class CarTurnCountException extends IllegalArgumentException {
  public CarTurnCountException(String message) {
    super(message);
  }
}
