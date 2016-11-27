package beholder.stores.rozetka;

import beholder.parser.CategoryUrl;
import beholder.parser.CategoryUrlPool;

/**
 * Blahblahblah
 */
public class RozetkaUrlPool extends CategoryUrlPool {

    private int page = 1;

    public RozetkaUrlPool(String urlHead) {
        super(urlHead);
    }

    @Override
    protected String calculateTail() {
        String tail;
        if (page == 1) {
            tail = "/";
        }
        else tail = String.format("/page=%d/", page);
        page++;

        return tail;
    }

    @Override
    protected CategoryUrl getUrl(String urlHead) {
        return new RozetkaUrl(urlHead);
    }
}
