package beholder.stores.mobilluck;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import beholder.domain.Product;
import beholder.parser.HtmlProductParser;

/**
 * Done
 */
public class MobilluckParser extends HtmlProductParser {

  private Integer storeId = 6;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("p.cci2_mdl a").text());

    String priceStr = (block.select("p.cci2_newprice").text().equals("") ?
        block.select("td.spezd2-pr").text() : block.select("p.cci2_newprice").text());
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.ico_zindex");
  }

}
