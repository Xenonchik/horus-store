package parser.source;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.ssl.HostnameVerifier;

/**
 * Blahblahblah
 */
public class HtmlSourceBuilder implements SourceBuilder {

    final static Logger log = LoggerFactory.getLogger(HtmlSourceBuilder.class);

    private CloseableHttpClient httpclient;
    private String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0";

    public HtmlSourceBuilder() {

        try {
            httpclient = (CloseableHttpClient) createHttpClient();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
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
            log.info(httpGet.getFirstHeader("User-Agent").getValue());
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
        return httpGet;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public HttpClient createHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder b = HttpClientBuilder.create();

        // setup a Trust Strategy that allows all certificates.
        //
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
        b.setSslcontext( sslContext);
        // here's the special part:
        //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        //      -- and create a Registry, to register it.
        //
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
            SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslSocketFactory)
            .build();
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
        b.setConnectionManager( connMgr);

        HttpHost proxy = new HttpHost("31.131.23.37", 3128);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);


        HttpClient client = b
            .setRoutePlanner(routePlanner)
            .build();
        return client;
    }
}
