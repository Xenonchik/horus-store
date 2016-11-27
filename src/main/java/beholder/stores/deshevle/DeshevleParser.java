package beholder.stores.deshevle;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import beholder.domain.Product;
import beholder.parser.HtmlProductParser;

/**
 * Done
 */
public class DeshevleParser extends HtmlProductParser {

  private Integer storeId = 3;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.cti_head").text());

    String priceStr = block.select("div.cti_price span").text();
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.catalog_tile_item");
  }

}
