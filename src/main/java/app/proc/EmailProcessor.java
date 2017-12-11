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


      helper.setTo("xenonchikmaxxx@gmail.com");
      helper.addCc("a.shmelev@emir.kiev.ua");
      helper.addCc("o.petrechenko@emir.kiev.ua");
      helper.addCc("i.borsuk@emir.kiev.ua");
      helper.addCc("a.grebenyuk@emir.kiev.ua");
      helper.addCc("i.lazukina@emir.kiev.ua");
      helper.addCc("r.malenko@emir.kiev.ua");
      helper.addCc("i.petrichuk@emir.kiev.ua");
      helper.addCc("a.shtabovenko@emir.kiev.ua");
      helper.addCc("danilshmelev765@gmail.com");

      helper.setText("Цены за " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setSubject("Цены за " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
      helper.setFrom("parser@blackbeholder.net");
      helper.addAttachment("without_alias.csv", new File("/opt/data/without_alias.csv"));
      helper.addAttachment("prices.csv", new File("/opt/data/prices_summary.csv"));

      sender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
