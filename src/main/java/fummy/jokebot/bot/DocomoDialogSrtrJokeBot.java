package fummy.jokebot.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "param" })
public class DocomoDialogSrtrJokeBot extends DocomoDialogJokeBot {

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

    Map<String, String> result = this.restTemplate.postForObject(url, param, Map.class, vars);

    String utt = result.get("utt");
    if (utt.endsWith("私の勝ちです。")) {
      reaction.setAnswer(String.format("%s・・・%s", word, utt));
    } else {
      reaction.setAnswer(String.format("%s・・・%s！", word, utt));
    }

    reaction.setId(this.id);
    reaction.setName(this.param.getNickname());
    reaction.setPictureUrl(this.getPictureUrl());

    return reaction;
  }
}
