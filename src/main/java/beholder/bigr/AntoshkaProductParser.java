package beholder.bigr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import beholder.parser.source.HtmlSourceBuilder;
import beholder.parser.source.SourceBuilder;

/**
 * Created by serge on 4/26/17.
 */
public class AntoshkaProductParser {

  SourceBuilder builder = new HtmlSourceBuilder();

  public BiProduct parseUrl(String url) {
    BiProduct product = new BiProduct();
    product.setUrl(url);

    String page = builder.getSource(url).getContent();

    Document doc = Jsoup.parse(page);
    String sku = doc.select("div.article").text().replace("Артикул производителя:", "").trim();
    product.setSKU(sku);
    return product;
  }

}
