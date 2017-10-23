package bigr.phase2.cat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Blahblahblah
 */
public class CategoryParser {
  public CategoryInfo parse(String html) {
    CategoryInfo categoryInfo = new CategoryInfo();
    Document doc = Jsoup.parse(html);

    String countStr = doc.select("div.filters-hd span.bold").first().text();
    categoryInfo.setProductsCount(Integer.parseInt(countStr));

    String nameStr = doc.select("div.heading h1").text();
    categoryInfo.setName(nameStr);
    return categoryInfo;
  }
}
