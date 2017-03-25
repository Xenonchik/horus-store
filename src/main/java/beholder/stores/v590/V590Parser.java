package beholder.stores.v590;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;

import beholder.domain.Product;
import beholder.parser.HtmlProductParser;

/**
 * Done
 */
public class V590Parser extends HtmlProductParser {

  private Integer storeId = 10;

  @Override
  protected Product processProduct(Element block) {
    Product product = new Product();
    product.setName(block.select("div.head span.description").text() + " " + block.select("div.head a").text().trim());

    String priceStr = (block.select("span.new").text().equals("") ?
        block.select("span.price_sidebar").text() : block.select("span.new").text());
    priceStr = priceStr.replaceAll("[^\\d]", "");

    if(!priceStr.equals("")) {
      product.setPrice(Long.parseLong(priceStr));
    }
    product.setStore(storeId);
    return product;
  }

  @Override
  protected Elements getBlocks(Document doc) {
    Elements elements = doc.select("div.category_block_view_item");
    List<Element> elementsToRemove = Lists.newArrayList();

    elementsToRemove.addAll(elements.stream().filter(
        element -> element.child(0).tagName().equals("a"))
            .collect(Collectors.toList())
    );
    elements.removeAll(elementsToRemove);

    return elements;
  }

}
