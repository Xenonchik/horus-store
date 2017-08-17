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

    private Pattern PRICE_PATTERN = Pattern.compile("rozetkaEvents\\.setGoodsData\\(\\{\\sid\\:\\s279261.+\\}\\)\\;", Pattern.DOTALL);

    @Override
    protected Elements getBlocks(Document doc) {
        return doc.select("div.g-i-tile-i-box");
    }

    @Override
    protected Product processProduct(Element block) {
        Product product = new Product();
        product.setName(block.select("div.g-i-tile-i-title a").text());
        String productId = block.select("div.g-i-tile-i-image div.g-id").text();
        Pattern p = Pattern.compile("rozetkaEvents\\.setGoodsData\\(\\{\\sid\\:\\s"+productId+".+?GTMEventsData", Pattern.DOTALL);
        Matcher m = p.matcher(doc.select("script").toString());
        String priceStr;
        if (m.find()) {
            priceStr = m.group();

            Pattern p2 = Pattern.compile("price\\:\\s\"\\d+", Pattern.DOTALL);
            Matcher m2 = p2.matcher(priceStr);
            if(m2.find()) {
                priceStr = m2.group();
                product.setPrice(Long.parseLong(priceStr.replaceAll("[^\\d.]", "")));
            }

        }
        product.setStore(storeId);
        product.setProductUrl(block.select("div.g-i-tile-i-title a").attr("href"));
        return product;
    }

    @Override
    protected void checkNextPage(Document doc) {
        try {
            if (doc.select("span.g-i-more-link-text") == null) {
                log.warn("No Rozetka next page");
                setProcessCategory(false);
            }
        } catch (NullPointerException e) {
            log.warn("No Rozetka next page, NPE");
        }
    }
}
