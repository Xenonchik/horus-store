package stores.v590;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.Product;
import parser.HtmlProductParser;

/**
 * Done
 */
public class V590Parser extends HtmlProductParser {

  private Integer storeId = 10;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.head span.description").text() + " " + block.select("div.head a").text());

    String priceStr = (block.select("span.new").text().equals("") ?
        block.select("span.price_sidebar").text() : block.select("span.new").text());
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.category_block_view_item");
  }

}
