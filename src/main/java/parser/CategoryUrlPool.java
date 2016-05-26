package parser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Encapsulates logic of category urls creation
 */
public abstract class CategoryUrlPool implements UrlPool {

    private String urlHead;

    public CategoryUrlPool(String urlHead) {
        this.urlHead = urlHead;
    }

    @Override
    public URL getNextUrl() {
        CategoryUrl url = getUrl(urlHead);
        url.setTail(calculateTail());
        try {
            return url.asURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected CategoryUrl getUrl(String urlHead) {
        return new CategoryUrl(urlHead);
    }


    protected abstract String calculateTail();
}
