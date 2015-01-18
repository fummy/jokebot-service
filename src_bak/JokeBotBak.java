import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import fummy.jokebot.bot.HttpClientFactoryV43;
import fummy.jokebot.bot.MySSLSocketFactory;
import fummy.jokebot.bot.Reaction;

public class JokeBotBak {
  

  public Reaction reaction(String keyword) {

    //HttpClient httpclient = getNewHttpClient();
    //HttpClient httpclient = getNewHttpClient2();
    //HttpClient httpclient = createHttpClientFactoryV43();

    try {

      //RestTemplate restTemplate = new RestTemplate();
      HttpClient httpclient = new DefaultHttpClient();
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
      RestTemplate restTemplate = new RestTemplate(requestFactory);
      //Map<String, String> result = restTemplate.postForObject(url, this.param, Map.class, vars);
      
    } catch (Exception e) {
      e.printStackTrace();
      reaction.setName(this.param.getNickname() + ":ERROR!!!");
      reaction.setAnswer(e.getLocalizedMessage());
    }
    
    /*
    try {

      String url2 = this.docomoApiConfig.getDialogueUrl2() + this.docomoApiConfig.getApikey();
      HttpPost httpPost = new HttpPost(url2);
      httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
      
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      nvps.add(new BasicNameValuePair("utt", "uhh."));
      httpPost.setEntity(new UrlEncodedFormEntity(nvps));
      
      
      //HttpEntity entity = response.getEntity();
      
      

      
      HttpPost request = new HttpPost(url2);
      
      //httppost.setEntity(new UrlEncodedFormEntity(nvps));
      //JSONObject json = new JSONObject(); 
      request.addHeader("content-type", "application/json; charset=UTF-8");
      //request.addHeader("content-type", "application/x-www-form-urlencoded");
      //request.addHeader("Accept","application/json");
      //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
      //StringEntity params = new StringEntity("{\"utt\":\"" + keyword + "\"}");
      StringEntity params = new StringEntity("{\"utt\":\"こんにちは\"}");
      request.setEntity(params);


      //CloseableHttpResponse response = this.getNewHttpClientNG().execute(httpPost);

      //HttpClient httpclient = new DefaultHttpClient();
      HttpClient httpclient = getNewHttpClient();
      
      
      
      
      HttpResponse response = httpclient.execute(request);
      
           
      
      
      
      reaction.setName(this.param.getNickname());
      
      //reaction.setAnswer(response.toString() + ":OK");
      //reaction.setAnswer(response.getEntity().toString() + ":OK");
      reaction.setAnswer(EntityUtils.toString(response.getEntity(), "UTF-8") + ":OK:" + keyword);

      
      
    } catch (IOException e) {
      e.printStackTrace();
      reaction.setName(this.param.getNickname() + ":ERROR!!!");
      reaction.setAnswer(e.getLocalizedMessage());
    }
    */

  }
  
  
  private HttpClient createHttpClientFactoryV43() {
    HttpClientFactoryV43 factory = new HttpClientFactoryV43();
    return factory.insecureClient();
  }

  private HttpClient getNewHttpClient2() {
    // クライアント側の証明書の処理

    try {
      
    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    char[] keyPass = "changeit".toCharArray();
    //keyStore.load(new FileInputStream("/app/.jdk/jre/lib/security/cacerts"), keyPass);
    keyStore.load(new FileInputStream("/app/keystore2"), keyPass);

    SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, keyPass).build();

    ConnectionConfig connectionConfig = ConnectionConfig.DEFAULT;
    Registry<ConnectionSocketFactory> socketFactoryRegistry 
      = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
      .register("https", new SSLConnectionSocketFactory(sslcontext)).build();

    BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
    connManager.setConnectionConfig(connectionConfig);

    CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();
    //return new DefaultHttpClient();
    return httpclient;
    } catch(Exception exp) {
      exp.printStackTrace();
      return null;
    }

  }

  
  /**
   * 証明書のチェックなしに https にアクセスできる
   * @return
   */
  @SuppressWarnings("deprecation")
  private HttpClient getNewHttpClient() {
    try {
      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      trustStore.load(null, null);
      MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
      sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
   
      HttpParams params = new BasicHttpParams();
      HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
   
      SchemeRegistry registry = new SchemeRegistry();
      registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      registry.register(new Scheme("https", sf, 443));
   
      ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
      return new DefaultHttpClient(ccm, params);
    } catch (Exception e) {
      e.printStackTrace();
      return new DefaultHttpClient();
    }
  }
  

  /**
   * 
   * 証明書のチェックなしに https にアクセスできない
   * この方法ではうまくいかない
   * 
   * @return
   */
  @SuppressWarnings("unused")
  private CloseableHttpClient getNewHttpClientNG() {
    try {
      SSLContextBuilder builder = new SSLContextBuilder();
      builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
      SSLConnectionSocketFactory sslsf;
      sslsf = new SSLConnectionSocketFactory(builder.build());
      CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
      return httpclient;
    } catch (NoSuchAlgorithmException | KeyStoreException |KeyManagementException e) {
      e.printStackTrace();
      return null;
    }
  }

}