package fummy.jokebot.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration()
//@ContextConfiguration({"file:src/main/webapp/WEB-INF/JokeBotService-servlet.xml"})
public class JokeBotTest {


  //@Autowired
  protected DocomoApiConfig docomoApiConfig;

  //@Autowired
  @Qualifier("siri")
  protected JokeBot siri;

  //@Autowired
  //@Qualifier("akemi")
  protected DocomoDialogJokeBot akemi;
  
  //@Autowired
  protected RestTemplate restTemplate;

  @SuppressWarnings("unchecked")
  //@Test
  public void testService() {
    System.out.printf("%s %n", "weee ");
    Reaction reaction = siri.reaction("スイカ食べたいな");
    System.out.printf("あああwee %s %n", reaction.getAnswer());
  }
  
  @SuppressWarnings("unchecked")
  //@Test
  public void testService2() {
    

    /*
    String url = "https://api.apigw.smt.docomo.ne.jp/knowledgeQA/v1/ask?APIKEY={APIKEY}&q={QUESTION}";
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("APIKEY", this.docomoApiConfig.getApikey());
    //vars.put("QUESTION", "ワールドカップの優勝国は？");
    //vars.put("QUESTION", "人類初の宇宙飛行士は？");

    Map<String, String> result = this.restTemplate.getForObject(url, Map.class, vars);
    */
    

    /*
    String url = this.docomoApiConfig.getDialogueUrl();
    
    
    DialogueRequestParam param = akemi.getParam();
    param.setUtt("世界・・・インフルエンザ！");
    //param.setMode("srtr");
    
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("APIKEY", this.docomoApiConfig.getApikey());

    Map<String, String> result = this.restTemplate.postForObject(url, param, Map.class, vars);

    System.out.printf("%s %n", result);
    */
    
    Tokenizer tokenizer = Tokenizer.builder().build();
    //List<Token> tokens = tokenizer.tokenize("スイカ食べたいな");
    List<Token> tokens = tokenizer.tokenize("日本食べたいな");
    //List<Token> tokens = tokenizer.tokenize("なんか健康ということですが、何か意識されていることはありますか？");
    
    List<String> words = new ArrayList<String>();
    
    for (Token token : tokens) {
      /*
      System.out.printf("%s %n", "============================");
      System.out.printf("%s %n", token.getAllFeatures());
      System.out.printf("%s %n", Arrays.asList(token.getAllFeaturesArray()));
      System.out.printf("%s %n", token.getPartOfSpeech());
      
      System.out.printf("%s %n", token.getSurfaceForm());
      System.out.printf("%s %n", token.getBaseForm());
      System.out.printf("%s %n", token.getReading());
      */
      
      List<String> befwords = Arrays.asList(token.getAllFeaturesArray());

      System.out.printf("%s%n", befwords);

      //if ((befwords.size() > 1) && "名詞".equals(befwords.get(0)) && ("非自立".equals(befwords.get(1)) == false) ) {
      if ((befwords.size() > 1) && "名詞".equals(befwords.get(0))) {
        words.add(token.getSurfaceForm());

        System.out.printf("%s%n", token.getSurfaceForm());
        
      }
    }

    int lastIndex = words.size() - 1;
    String word = words.get(lastIndex);
    
    //if ((words.size() > 0) && ("名詞".equals(words.get(0))) ) {
      String url = this.docomoApiConfig.getDialogueUrl();
      
      DialogueRequestParam param = akemi.getParam();
      //param.setUtt(token.getSurfaceForm());
      param.setUtt(word);
      param.setMode("srtr");
      
      param.setCharacter(30);
      
      Map<String, String> vars = new HashMap<String, String>();
      vars.put("APIKEY", this.docomoApiConfig.getApikey());

      Map<String, String> result = this.restTemplate.postForObject(url, param, Map.class, vars);

      System.out.printf("%s %n", result);
      
      String utt = result.get("utt");
      String yomi = result.get("yomi");
      
      if (utt.endsWith("私の勝ちです。")) {
        System.out.printf("%s・・・%s%n", word, utt);
      } else {
        int index = result.get("yomi").length() - 1;
        String nextWord = yomi.substring(index);
        //System.out.printf("%s %n", nextWord);
        //System.out.printf("%s・・・%sの%s！%n", word, utt, nextWord);
        System.out.printf("%s・・・%s！%n", word, utt);
      }

      /*
      */
      
    //}

  }
}
