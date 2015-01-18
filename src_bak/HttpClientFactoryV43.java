package fummy.jokebot.bot;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

public class HttpClientFactoryV43 {

  private BasicHttpClientConnectionManager connectionManager;

  public HttpClientFactoryV43() {
    try {
      SSLContext sslContext = new SSLContextBuilder()
          .loadTrustMaterial(null, new TrustSelfSignedStrategy())
          .loadTrustMaterial(null).build();
      SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
          sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      Registry<ConnectionSocketFactory> registry = RegistryBuilder
          .<ConnectionSocketFactory> create()
          .register("http", PlainConnectionSocketFactory.getSocketFactory())
          .register("https", sslConnectionSocketFactory).build();
      connectionManager = new BasicHttpClientConnectionManager(registry);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (KeyManagementException e) {
      throw new RuntimeException(e);
    } catch (KeyStoreException e) {
      throw new RuntimeException(e);
    }
  }

  public HttpClient insecureClient() {
    RequestConfig config = RequestConfig.custom().setSocketTimeout(1000)
        .setConnectTimeout(1000).setConnectionRequestTimeout(1000)
        .setStaleConnectionCheckEnabled(true).build();

    return HttpClients.custom().setDefaultRequestConfig(config)
        .setMaxConnPerRoute(100).setMaxConnTotal(100).disableAutomaticRetries()
        .disableConnectionState().disableContentCompression()
        .disableRedirectHandling().setConnectionManager(connectionManager)
        .build();
  }
}
