package stores.tehnos;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.Product;
import parser.HtmlProductParser;

/**
 * Done
 */
public class TehnosParser extends HtmlProductParser {

  private Integer storeId = 5;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.tovar-title a").attr("title").equals("") ?
        block.select("div.tovar-title a").text() : block.select("div.tovar-title a").attr("title"));

    String priceStr = block.select("div.price big span").text();
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.tovar-desc");
  }

}
