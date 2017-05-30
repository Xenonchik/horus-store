package bigr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import parser.source.HtmlSourceBuilder;
import parser.source.SourceBuilder;

/**
 * Created by serge on 4/26/17.
 */
public class AntoshkaProductParser {

  SourceBuilder builder = new HtmlSourceBuilder();

  public String parseUrl(String url) {
    String page = builder.getSource(url).getContent();

    Document doc = Jsoup.parse(page);
    String sku = doc.select("div.article").text().replace("Артикул производителя:", "").trim();

    return sku;
  }

}
