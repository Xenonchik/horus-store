package stores.fotos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    String priceClass = getPriceClass();
    if(priceClass.length() > 0) {
      String priceStr = block.select("div.price div." + priceClass + " span.main").size() > 0
          ? block.select("div.price div." + priceClass + " span.main").text() :
          block.select("div.price div." + priceClass + " span.action").text();

      priceStr = priceStr.replaceAll("[^\\d]", "");
      if (!priceStr.equals("")) {
        product.setPrice(Long.parseLong(priceStr));
      }
    } else {
      setProcessCategory(false);
    }
    product.setStore(storeId);
    return product;
  }

  private String getPriceClass() {
    String style = doc.select("body style").toString();
    style = style.replace("<style>", "").replace("</style>", "");
    List<String> styles = Arrays.asList(style.split("\n\t"));
    Set<String> setStyles = new HashSet<>();
    for(String s : styles) {
      setStyles.add(s.replace("{display:none;}", "").replace(".", "").replace("\n", ""));
    }

    String result = "";
    for(Element el : doc.select("div.price").get(0).children()) {
      if(!setStyles.contains(el.attr("class"))) {
        result = el.attr("class");
      }
    }

    return result;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    return doc.select("tr.catalog_item").size() > 0 ? doc.select("tr.catalog_item") : doc.select("div.catalog_item");
  }

}
