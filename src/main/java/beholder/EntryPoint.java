package beholder;

import beholder.app.Application;

/**
 * Created by serge on 3/27/16.
 */
public class EntryPoint {

  public static void main(String[] args) throws Exception {
    Application app = new Application();
    app.go(args);
  }

}
