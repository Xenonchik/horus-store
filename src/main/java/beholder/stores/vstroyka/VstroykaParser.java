package beholder.stores.vstroyka;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import beholder.domain.Product;
import beholder.parser.HtmlProductParser;

/**
 * Done
 */
public class VstroykaParser extends HtmlProductParser {

  private Integer storeId = 7;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("p.b-cat-item-name").text() + " " + block.select("p.b-cat-item-brand").text());

    String priceStr = (block.select("span.b-cat-item-price").text());
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.b-cat-item");
  }

}
