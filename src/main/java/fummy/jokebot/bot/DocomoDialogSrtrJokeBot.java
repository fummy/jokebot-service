package fummy.jokebot.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"param"})
public class DocomoDialogSrtrJokeBot implements JokeBot {

  @Autowired
  protected DocomoApiConfig docomoApiConfig;

  @Autowired
  protected RestTemplate restTemplate;
  
  protected DialogueRequestParam param;

  protected int id;
  
  protected String profile;

  protected String pictureUrl;

  @Override
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return param.getNickname();
  }

  @Override
  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  @Override
  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public DialogueRequestParam getParam() {
    return param;
  }

  public void setParam(DialogueRequestParam param) {
    this.param = param;
  }

  @SuppressWarnings({ "unchecked" })
  public Reaction reaction(String keyword) {
    Reaction reaction = new Reaction();

    Tokenizer tokenizer = Tokenizer.builder().build();
    List<Token> tokens = tokenizer.tokenize(keyword);

    List<String> words = new ArrayList<String>();

    for (Token token : tokens) {
      List<String> features = Arrays.asList(token.getAllFeaturesArray());

      if ((features.size() > 1) && "名詞".equals(features.get(0))) {
        words.add(token.getSurfaceForm());
      }
    }

    if (words.size() == 0) {
      return reaction;
    }

    String url = this.docomoApiConfig.getDialogueUrl();

    String word = words.get(words.size() - 1);
    this.param.setUtt(word);
    this.param.setMode("srtr");
    this.param.setCharacter(30);

    Map<String, String> vars = new HashMap<String, String>();
    vars.put("APIKEY", this.docomoApiConfig.getApikey());

    Map<String, String> result = this.restTemplate.postForObject(url, param,
        Map.class, vars);

    // System.out.printf("%s %n", result);

    String utt = result.get("utt");
    //String yomi = result.get("yomi");

    if (utt.endsWith("私の勝ちです。")) {
      System.out.printf("%s・・・%s", word, utt);
      // reaction.setAnswer(String.format("%s・・・%s%n", word, utt));
    } else {
      // int index = result.get("yomi").length() - 1;
      // System.out.printf("%s・・・%s！%n", word, utt);
      reaction.setAnswer(String.format("%s・・・%s！", word, utt));
    }

    // String url = this.docomoApiConfig.getDialogueUrl();
    // this.param.setUtt(keyword);
    // Map<String, String> vars = new HashMap<String, String>();
    // vars.put("APIKEY", this.docomoApiConfig.getApikey());

    // Map<String, String> result = this.restTemplate.postForObject(url,
    // this.param, Map.class, vars);
    reaction.setId(this.id);
    reaction.setName(this.param.getNickname());
    // reaction.setAnswer(result.get("utt"));
    reaction.setPictureUrl(this.getPictureUrl());

    return reaction;
  }

  public DocomoDialogSrtrJokeBot() {
    this.param = new DialogueRequestParam();
  }
}
