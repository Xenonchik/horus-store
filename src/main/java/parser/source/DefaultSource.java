package parser.source;

/**
 * Blahblahblah
 */
public class DefaultSource implements ParseSource {

    private String content;
    private String url;

    public DefaultSource(String content, String url) {
        this.url = url;
        this.content = content;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
