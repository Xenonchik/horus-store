package stores.foxtrot;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.Product;
import parser.HtmlProductParser;

/**
 * Done
 */
public class FoxtrotParser extends HtmlProductParser {

  private Integer storeId = 4;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.product a.title").text());

    String priceStr = block.select("div.price a").text();
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.gtile-i-box");
  }

}
