package beholder.app.proc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Blahblahblah
 */
public class EmailProcessor {
  public void process() {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost("localhost");
    sender.setPort(465);

    MimeMessage message = sender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, 1, null);

//      helper.setTo("ekhrenov@mail.ua");
      helper.setTo("xenonchikmaxxx@gmail.com");
//      helper.addCc("yuriy.ermak@yandex.ua");

      helper.setText("Prices from " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setSubject("Prices from " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setFrom("serhii.harnyk@gmail.com");
      helper.addAttachment("prices.csv", new File("/opt/data/prices.csv"));

      sender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
