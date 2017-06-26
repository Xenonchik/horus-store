package bigr;

import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.collect.Sets;

import parser.source.HtmlSourceBuilder;
import parser.source.SourceBuilder;

/**
 * Blahblahblah
 */
public class AntoshkaBrands {

  SourceBuilder builder = new HtmlSourceBuilder();

  public Set<String> getBrands(String url) {
    Set<String> result = Sets.newHashSet();

    String page = builder.getSource(url).getContent();
    Document doc = Jsoup.parse(page);
    Elements els = doc.select("dd.filter-tm_id a");
    els.forEach(el ->
      result.add(el.text().toUpperCase())
    );

    return result;
  }
}
