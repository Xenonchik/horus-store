package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import domain.Product;
import parser.source.ParseSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Blahblahblah
 */
abstract public class HtmlProductParser {
    private boolean processCategory = true;
    private Long category;

    protected Integer productsCount = 0;

    protected Document doc;

    public List<Product> parse(ParseSource source) {
        List<Product> products = new ArrayList<>();
        doc = Jsoup.parse(source.getContent());
        checkNextPage(doc);
        Elements blocks = getBlocks(doc);

        if(blocks.size() == 0) {
            setProcessCategory(false);
        }
        for(Element block : blocks) {
            Product product = processProduct(block);
//            product.setHtml(block.html());
            product.setCategory(category);
            if (product.isValid()) {
                if (product.getPrice() != null && product.getPrice() > 0) {
                    product.setUrl(source.getUrl());
                    if(checkAddDate(product)) {
                        product.setDate(new Date());
                        product.setDay(new Date());
                    }
                    products.add(product);
                }
                else
                setProcessCategory(false);
            }
        }

        checkProductsCount(products.size());

        return products;
    }

    private boolean checkAddDate(Product product) {
        if(product.getStore() > 10) return false;
        return true;
    }

    protected void checkNextPage(Document doc){

    }

    private void checkProductsCount(int size) {
        if(size < productsCount) {
            setProcessCategory(false);
        }
    }

    protected abstract Product processProduct(Element block);

    protected abstract Elements getBlocks(Document doc);

    public boolean processCategory() {
        return processCategory;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public void setProcessCategory(boolean processCategory) {
        this.processCategory = processCategory;
    }

}
