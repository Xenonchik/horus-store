package beholder.stores.foxtrot;

import java.net.MalformedURLException;
import java.net.URL;

import beholder.parser.CategoryUrl;

/**
 * Created by serge on 4/19/16.
 */
public class FoxtrotUrl extends CategoryUrl {
  public FoxtrotUrl(String head) {
    super(head);
  }

  @Override
  public URL asURL() throws MalformedURLException {
    return new URL(sanitize(getHead()+getTail()));
  }

  private String sanitize(String s) {
    if(s.contains("?filter")) {
      s = s.replace("?price", "&price");
    }
    return s;
  }
}
