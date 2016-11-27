package beholder.stores.palladium;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import beholder.domain.Product;
import beholder.parser.HtmlProductParser;

/**
 * Done
 */
public class PalladiumParser extends HtmlProductParser {

  private Integer storeId = 8;

  {
    this.productsCount = 20;
  }

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.product-in span.title-prod a").text());

    String priceStr = block.select("div.product-in div.price-group b").text();
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.prod-info");
  }

}
