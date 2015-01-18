package fummy.jokebot.bot;

import java.util.HashMap;
import java.util.Map;

import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"param"})
public class DocomoDialogJokeBot implements JokeBot {

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

    String url = this.docomoApiConfig.getDialogueUrl();
    this.param.setUtt(keyword);
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("APIKEY", this.docomoApiConfig.getApikey());

    Map<String, String> result = this.restTemplate.postForObject(url, this.param, Map.class, vars);
    reaction.setId(this.id);
    reaction.setName(this.param.getNickname());
    reaction.setAnswer(result.get("utt"));
    reaction.setPictureUrl(this.getPictureUrl());

    return reaction;
  }

  public DocomoDialogJokeBot() {
    this.param = new DialogueRequestParam();
  }
}
