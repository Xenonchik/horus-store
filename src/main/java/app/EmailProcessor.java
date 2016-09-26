package app;

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
    sender.setHost("46.101.96.139");

    MimeMessage message = sender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, 1, null);

      helper.setTo("a.shmelev@emir.kiev.ua");
      helper.addCc("xenonchikmaxxx@gmail.com");
      helper.addCc("y.ermak@emir.kiev.ua");

      helper.setText("Prices from " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setSubject("Prices from " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setFrom("serhii.harnyk@gmail.com");
      helper.addAttachment("prices", new File("/opt/data/prices.csv"));

      sender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
