package stores.hotline;

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
public class HotlineParser extends HtmlProductParser {

  final static Logger log = LoggerFactory.getLogger(HotlineParser.class);


  private Integer storeId = 12;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();

    Elements name = block.select("div.gd-info-cell b.m_r-10 a.g_statistic");
    product.setName(name.text());


    String priceStr;

    priceStr = block.select("div.gd-price-sum div.orng").text();

    priceStr = priceStr.replaceAll("[^\\d]", "");

    if (!priceStr.equals("")) {
        product.setPrice(Long.parseLong(priceStr));
    } else {
      setProcessCategory(false);
    }

    product.setProductUrl(block.select("div.gd-info-cell b.m_r-10 a.g_statistic").attr("href"));

    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("div.gd-item div.gd-box");
  }

  @Override
  protected void checkNextPage(Document doc) {
    try {
      if (doc.select("div.pager a.next").size() == 0) {
        setProcessCategory(false);
      }
    } catch (NullPointerException e) {
      log.warn("CHECK NEXT PAGE: No next page");
    }
  }

}
