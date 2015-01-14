package fummy.jokebot.bot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;


public class JokeBot {

  @Autowired
  protected DocomoApiConfig docomoApiConfig;

  @Autowired
  protected RestTemplate restTemplate;

  protected DialogueRequestParam param;
  

  public DialogueRequestParam getParam() {
    return param;
  }

  public void setParam(DialogueRequestParam param) {
    this.param = param;
  }
  
  @SuppressWarnings("unchecked")
  public Reaction reaction(String keyword) {
    Reaction reaction = new Reaction();

    String url = this.docomoApiConfig.getDialogueUrl();
    this.param.setUtt(keyword);
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("APIKEY", this.docomoApiConfig.getApikey());

    // RestTemplate restTemplate = new RestTemplate();
    // Map<String, String> result = this.restTemplate.postForObject(url,
    // this.param, Map.class, vars);
    // reaction.setName(this.param.getNickname());
    // reaction.setAnswer(result.get("utt"));

    SSLContextBuilder builder = new SSLContextBuilder();
    try {
      builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
      CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

      // HttpGet httpGet = new HttpGet("https://some-server");
      // CloseableHttpResponse response = httpclient.execute(httpGet);

      String url2 = this.docomoApiConfig.getDialogueUrl2() + this.docomoApiConfig.getApikey();
      HttpPost httpPost = new HttpPost(url2);
      //httpPost.se
      httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
      
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      nvps.add(new BasicNameValuePair("utt", "uhh."));
      //nvps.add(new BasicNameValuePair("utt", keyword));
      // nvps.add(new BasicNameValuePair("password", "secret"));
      httpPost.setEntity(new UrlEncodedFormEntity(nvps));
      
      // dialogueUrl2
      
      
      //CloseableHttpResponse response2 = httpclient.execute(httpPost);
      //HttpEntity entity = response2.getEntity();
      
      //HttpClient httpclient2 = new DefaultHttpClient();
      HttpClient httpclient2 = getNewHttpClient();
      

      
      HttpPost request = new HttpPost(url2);
      
      //httppost.setEntity(new UrlEncodedFormEntity(nvps));
      //JSONObject json = new JSONObject(); 
      StringEntity params = new StringEntity("{\"utt\":\"" + keyword + "\"}");
      request.addHeader("content-type", "application/json");
      request.addHeader("Accept","application/json");
      request.setEntity(params);
      //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
      //httppost.setEntity(se);

      HttpResponse response = httpclient.execute(request);
      //HttpResponse response = httpclient2.execute(request);
      
      
      //HttpEntity entity = response.getEntity();
      
      
      
      
      
      //is = entity.getContent();
      //entity.
      //entity.
      
      reaction.setName(this.param.getNickname());
      //reaction.setAnswer(response.toString() + ":OK");
      //reaction.setAnswer(response.getEntity().toString() + ":OK");

      reaction.setAnswer(EntityUtils.toString(response.getEntity(), "UTF-8") + ":OK:" + keyword);

      
      
    } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
      e.printStackTrace();
      reaction.setName(this.param.getNickname() + ":ERROR!!!");
      reaction.setAnswer(e.getLocalizedMessage());
    }

    return reaction;
  }
  
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
      return new DefaultHttpClient();
    }
  }
  


  public JokeBot() {
    this.param = new DialogueRequestParam();
  }
}
