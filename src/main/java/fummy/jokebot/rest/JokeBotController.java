package fummy.jokebot.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  ServletContext servletContext;
  
  @Autowired
  @Qualifier("akemi")
  protected JokeBot akemi;

  @Autowired
  @Qualifier("hosogai")
  protected JokeBot hosogai;

  @Autowired
  @Qualifier("siri")
  protected JokeBot siri;
  
  @Autowired
  @Qualifier("botlibre")
  protected JokeBot botlibre;
  
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
    jokeBots.add(this.siri);
    
    return jokeBots;
  }
  
  @RequestMapping(value = "/bot/avatars/{botId}", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET)
  @ResponseBody
  public byte[] helloWorld(@PathVariable int botId) {
    try {
      String imageFile = "akemi.jpg";
      String imageType = "jpg";
      
      if (botId == 2) {
        imageFile = "hosogai.jpg";
      } else if(botId == 3) {
        imageFile = "siri.png";
        imageType = "png";
      }
      InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/views/img/" + imageFile);      
      BufferedImage bufferedImage = ImageIO.read(inputStream);

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, imageType, byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
      
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  @RequestMapping(value = "/bot/list", headers="Accept=application/json")
  @ResponseBody
  public List<JokeBot> list() {
    List<JokeBot> jokeBots = new ArrayList<JokeBot>();
    jokeBots.add(this.akemi);
    jokeBots.add(this.hosogai);
    jokeBots.add(this.siri);
    jokeBots.add(this.botlibre);

    return jokeBots;
  }
  
  @RequestMapping(value = "/bot/reaction", headers="Accept=application/json")
  @ResponseBody
  public Reaction reaction(@RequestParam String keyword, @RequestParam("bot_id") int botId) { 
    if (botId == 4) {
      return this.botlibre.reaction(keyword);
    } else if (botId == 3) {
      return this.siri.reaction(keyword);
    } else if(botId == 2)  {
      return this.hosogai.reaction(keyword);
    } else {
      return this.akemi.reaction(keyword);
    }
  }
}
