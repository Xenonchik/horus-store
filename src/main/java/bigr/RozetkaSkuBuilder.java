package bigr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Blahblahblah
 */
public class RozetkaSkuBuilder implements SkuBuilder {
  static Pattern r = Pattern.compile("\\(.*?\\)");
  @Override
  public Sku buildSku(BiProduct product) {
    Sku sku = Sku.fromProduct(product);
    sku.setSku(getSKU(product.getName()));
    return sku;
  }

  private String getSKU(String name) {
    Matcher m = r.matcher(name);
    String s = "";
    while (m.find()) {
      s = m.group();
    }
    return s.replaceAll("\\(", "").replaceAll("\\)", "");
  }
}
