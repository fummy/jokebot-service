package fummy.jokebot.bot;

import java.util.HashMap;
import java.util.Map;

import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"param"})
public class DocomoDialogJokeBot extends AbstractJokeBot {

  @Autowired
  protected DocomoApiConfig docomoApiConfig;

  protected DialogueRequestParam param;


  public DialogueRequestParam getParam() {
    return param;
  }

  public void setParam(DialogueRequestParam param) {
    this.param = param;
  }

  @Override
  public String getName() {
    return param.getNickname();
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
