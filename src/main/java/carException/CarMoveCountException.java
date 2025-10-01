package carException;

public class CarMoveCountException extends IllegalArgumentException{
  public CarMoveCountException(String message){
    super(message);
  }
}
