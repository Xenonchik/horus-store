package stores.rozetka;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Product;
import parser.HtmlProductParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Blahblahblah
 */
public class RozetkaParser extends HtmlProductParser {
    final static Logger log = LoggerFactory.getLogger(RozetkaParser.class);

    private Integer storeId = 1;

    private Pattern PRICE_PATTERN = Pattern.compile("\\%7B\\%22price\\%22\\%3A\\%22\\d+\\%22\\%2C\\%22", Pattern.DOTALL);

    @Override
    protected Elements getBlocks(Document doc) {
        return doc.select("div.g-i-tile-catalog");
    }

    @Override
    protected Product processProduct(Element block) {
        Product product = new Product();
        product.setName(block.select("div.g-i-tile-i-title a").text());
        Matcher m = PRICE_PATTERN.matcher(block.select("div[name~=prices] script").toString());
        String priceStr;
        if (m.find()) {
            priceStr = m.group();
            product.setPrice(Long.parseLong(priceStr.replace("%7B%22price%22%3A%22", "").replace("%22%2C%22", "")));
        }
        product.setStore(storeId);
        return product;
    }

    @Override
    protected void checkNextPage(Document doc) {
        try {
            if (doc.select("span.g-i-more-link-text") == null) {
                setProcessCategory(false);
            }
        } catch (NullPointerException e) {
            log.warn("No next page");
        }
    }
}
