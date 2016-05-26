package stores.tehnohata;

import java.net.MalformedURLException;
import java.net.URL;

import parser.CategoryUrl;

/**
 * Created by serge on 4/19/16.
 */
public class TehnohataUrl extends CategoryUrl {
  public TehnohataUrl(String head) {
    super(head);
  }

  @Override
  public URL asURL() throws MalformedURLException {
    return new URL(sanitize(getHead()+getTail()));
  }

  private String sanitize(String s) {
    if(s.contains("filter.php")) {
      s = s.replace("?page", "&page");
    }
    return s;
  }
}
