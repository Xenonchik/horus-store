package app.proc;

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
    sender.setPort(25);

    MimeMessage message = sender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, 1, null);

      helper.setTo("ekhrenov@mail.ua");
      helper.setTo("xenonchikmaxxx@gmail.com");
      helper.addCc("yuriy.ermak@yandex.ua");

      helper.setText("Цены за " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setSubject("Цены за " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setFrom("parser@blackbeholder.net");
      helper.addAttachment("prices.csv", new File("/opt/data/prices.csv"));

      sender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
