package beholder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import beholder.app.Application;

/**
 * Created by serge on 3/27/16.
 */
public class EntryPoint {


  public static void main(String[] args) throws Exception {
    ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    Application app = context.getBean(Application.class);
    app.go(args);
  }

}
