package beholder.parser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Blahblahblah
 */
public class CategoryUrl {
    private String head;
    private String tail;

    public CategoryUrl(String head) {
        this.setHead(head);
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public URL asURL() throws MalformedURLException {
        return new URL(getHead() +tail);
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
