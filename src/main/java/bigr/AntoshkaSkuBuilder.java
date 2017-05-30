package bigr;

/**
 * Blahblahblah
 */
public class AntoshkaSkuBuilder implements SkuBuilder {
  AntoshkaProductParser parser = new AntoshkaProductParser();

  public Sku buildSku(BiProduct product) {
    Sku sku = Sku.fromProduct(product);
    sku.setSku(getSKU(product.getUrl()));
    return sku;
  }

  private String getSKU(String url) {
    return parser.parseUrl(url);
  }
}
