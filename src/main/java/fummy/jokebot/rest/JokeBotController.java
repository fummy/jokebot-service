package fummy.jokebot.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

  @RequestMapping(value = "/debug", headers="Accept=application/json")
  @ResponseBody
  public List<JokeBot> debug() {    
    List<JokeBot> jokeBots = new ArrayList<JokeBot>();
    jokeBots.add(this.akemi);
    jokeBots.add(this.hosogai);
    
    return jokeBots;
  }
  
  @RequestMapping(value = "/bot/list", headers="Accept=application/json")
  @ResponseBody
  public List<JokeBot> list() {
    List<JokeBot> jokeBots = new ArrayList<JokeBot>();
    jokeBots.add(this.akemi);
    jokeBots.add(this.hosogai);

    return jokeBots;
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
