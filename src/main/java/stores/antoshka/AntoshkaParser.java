package stores.antoshka;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Product;
import parser.HtmlProductParser;

/**
 * Done
 */
public class AntoshkaParser extends HtmlProductParser {

  final static Logger log = LoggerFactory.getLogger(AntoshkaParser.class);


  private Integer storeId = 11;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();

    Elements name = block.select("h2.product-name a");
    product.setName(name.text());


    String priceStr = "";

    if(!block.select("div.price-box p.special-price").text().equals("")) {
      priceStr = block.select("div.price-box p.special-price").text();
    }
    else if(!block.select("div.price-box span.special-price").text().equals("")) {
      priceStr = block.select("div.price-box span.special-price").text();
    }
    else if(!block.select("div.price-box span.regular-price").text().equals("")) {
      priceStr = block.select("div.price-box span.regular-price").text();
    }

    priceStr = priceStr.replaceAll("[^\\d]", "");

    if (!priceStr.equals("")) {
        product.setPrice(Long.parseLong(priceStr));
    } else {
      setProcessCategory(false);
    }

    product.setProductUrl(block.select("h2.product-name a").attr("href"));

    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.item-inner");
  }

  @Override
  protected void checkNextPage(Document doc) {
    try {
      if (doc.select("div.pages li.current ~ li").size() == 0) {
        setProcessCategory(false);
      }
    } catch (NullPointerException e) {
      log.warn("No next page");
    }
  }

}
