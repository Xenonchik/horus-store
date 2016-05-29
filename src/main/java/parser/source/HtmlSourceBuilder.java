package parser.source;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Blahblahblah
 */
public class HtmlSourceBuilder implements SourceBuilder {

    final static Logger log = LoggerFactory.getLogger(HtmlSourceBuilder.class);

    private CloseableHttpClient httpclient;
    private String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0";

    public HtmlSourceBuilder() {

        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            httpclient = HttpClients.custom().setSSLSocketFactory(
                    sslsf).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ParseSource getSource(String uri) {

        String content;

        HttpGet httpGet = buildHttpGet(uri);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            content = httpclient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            log.error(uri);
            e.printStackTrace();
            content = "";
        }

        return new DefaultSource(content, uri);
    }

    protected HttpGet buildHttpGet(String uri) {
        HttpGet httpGet = new HttpGet(uri);

        httpGet.addHeader("User-Agent", this.userAgent);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        return new HttpGet(uri);
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
