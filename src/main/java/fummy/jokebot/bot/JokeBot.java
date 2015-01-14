package fummy.jokebot.bot;

import java.util.HashMap;
import java.util.Map;

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

    //RestTemplate restTemplate = new RestTemplate();
    Map<String, String> result = this.restTemplate.postForObject(url, this.param, Map.class, vars);

    reaction.setName(this.param.getNickname());
    reaction.setAnswer(result.get("utt"));
    return reaction;
  }

  public JokeBot() {
    this.param = new DialogueRequestParam();
  }
}
