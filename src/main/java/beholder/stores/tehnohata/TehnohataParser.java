package beholder.stores.tehnohata;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beholder.domain.Product;
import beholder.parser.HtmlProductParser;

/**
 * Done
 */
public class TehnohataParser extends HtmlProductParser {

  final static Logger log = LoggerFactory.getLogger(TehnohataParser.class);

  private Integer storeId = 9;

  {
    this.productsCount = 100;
  }

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.product_list_cat").text() + " " + block.select("a.product_list_name").text());

    String priceStr = block.select("div.product_list_buy span.price").text();

    Element priceblock = block.select("div.product_list_buy span.price").first();

    if(priceblock.select("span.productOldPrice").size() > 0) {
      priceStr = ((TextNode) priceblock.childNodes().get(1)).text();
    }

    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.product");
  }

  @Override
  protected void checkNextPage(Document doc) {
    try {
      if (!doc.select("a.pageResults").last().text().equals("Следующая")) {
        setProcessCategory(false);
      }
    } catch (NullPointerException e) {
      log.warn("No next page");
    }
  }
}
