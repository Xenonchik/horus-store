package stores.rozetka;

import org.apache.commons.lang3.StringUtils;
import parser.CategoryUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by serge on 4/19/16.
 */
public class RozetkaUrl extends CategoryUrl {
  public RozetkaUrl(String head) {
    super(head);
  }

  @Override
  public URL asURL() throws MalformedURLException {
    return new URL(sanitize(getHead()+getTail()));
  }

  private String sanitize(String s) {
    if(StringUtils.countMatches(s, "=") == 3) {
      s = s.replace("/page", ";page");
    }
    return s;
  }
}
