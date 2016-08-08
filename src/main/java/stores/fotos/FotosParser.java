package stores.fotos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.HtmlProductParser;

/**
 * Done
 */
public class FotosParser extends HtmlProductParser {

  private final Pattern DISPLAY = Pattern.compile("[a-z]+\\{display\\:block", Pattern.DOTALL);
//  private final Pattern DISPLAY = Pattern.compile("none", Pattern.DOTALL);

  private Integer storeId = 2;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();

    Elements name = block.select("td.info a.product_click").size() > 0 ?
            block.select("td.info a.product_click") : block.select("div.title a.product_click");
    product.setName(name.text());

    String priceStr = block.select("div.price div." + getPriceClass() + " span.main").size() > 0
            ? block.select("div.price div." + getPriceClass() + " span.main").text() :
            block.select("div.price div." + getPriceClass() + " span.action").text()
            ;
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  private String getPriceClass() {
//    String style = doc.select("body style").toString();
//    String result = "";
//    Matcher m = DISPLAY.matcher(style);
//    if(m.find()) {
//      result = m.group().replace("{display:block", "");
//    }

    String result = doc.select("div.price").get(0).children().get(7).attr("class");

    return result;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("tr.catalog_item").size() > 0 ? doc.select("tr.catalog_item") : doc.select("div.catalog_item");
  }

}
