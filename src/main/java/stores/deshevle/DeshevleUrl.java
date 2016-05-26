package stores.deshevle;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import parser.CategoryUrl;

/**
 * Created by serge on 4/19/16.
 */
public class DeshevleUrl extends CategoryUrl {
  public DeshevleUrl(String head) {
    super(head);
  }

  @Override
  public URL asURL() throws MalformedURLException {
    return new URL(sanitize(getHead()+getTail()));
  }

  private String sanitize(String s) {
    s = s.replace(".html/", "/");
    return s;
  }
}
