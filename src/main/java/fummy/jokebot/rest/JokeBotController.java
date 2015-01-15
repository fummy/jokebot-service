package fummy.jokebot.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import fummy.jokebot.bot.DocomoApiConfig;
import fummy.jokebot.bot.JokeBot;
import fummy.jokebot.bot.Reaction;

@Controller
public class JokeBotController {
  
  @Autowired
  protected DocomoApiConfig docomoApiConfig;
  
  @Autowired
  @Qualifier("akemi")
  protected JokeBot akemi;

  @Autowired
  @Qualifier("hosogai")
  protected JokeBot hosogai;
  
  
  @RequestMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping(value = "/store_apikey", headers="Accept=application/json")
  @ResponseBody
  public Map<String, String> storeApiKey(@RequestParam String apikey) {
    this.docomoApiConfig.setApikey(apikey);
    
    Map<String, String> result = new HashMap<String, String>();
    result.put("status", "success");
    return result;
  }
  

  @RequestMapping(value = "/bot/list", headers="Accept=application/json")
  @ResponseBody
  public List<Map<String, String>> list() {
    List<Map<String, String>> list = new ArrayList<Map<String,String>>();
    Map<String, String> bot = null;
    
    bot = new HashMap<String, String>();
    bot.put("bot_id", "1");
    bot.put("bot_name", "朱美");
    bot.put("profile", "xxx");
    bot.put("picture_url", "xxx");
    list.add(bot);

    bot = new HashMap<String, String>();
    bot.put("bot_id", "2");
    bot.put("bot_name", "細貝");
    bot.put("profile", "xxx");
    bot.put("picture_url", "xxx");
    list.add(bot);
    
    return list;
  }
  

  @RequestMapping(value = "/bot/reaction", headers="Accept=application/json")
  @ResponseBody
  public Reaction reaction(@RequestParam String keyword, @RequestParam("bot_id") int botId) { 
    if (botId == 1) {
      return this.akemi.reaction(keyword);
    } else {
      return this.hosogai.reaction(keyword);
    }
  }
}
