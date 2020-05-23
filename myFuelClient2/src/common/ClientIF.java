
package common;

import javafx.stage.Stage;

public interface ClientIF 
{
  public abstract boolean sendToController(Object obj);
  public abstract void start(Stage primaryStage) throws Exception;
}
