package fummy.jokebot.bot;

import java.util.HashMap;
import java.util.Map;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class BotlibreJokeBot extends AbstractJokeBot {
  
  protected String botlibreUrl;

  protected String botlibreApplicationId;

  protected String botlibreInstanceId;
  
  protected String microsoftTranslatorApiClientId;

  protected String microsoftTranslatorApiClientSecret;

  public Reaction reaction(String keyword) {
    Reaction reaction = new Reaction();
    reaction.setId(this.id);
    reaction.setName(this.getName());
    reaction.setAnswer("");

    try {
      // Microsoft Translator API ja to en
      Translate.setClientId(this.microsoftTranslatorApiClientId);
      Translate.setClientSecret(this.microsoftTranslatorApiClientSecret);
      String translatedText = Translate.execute(keyword, Language.JAPANESE, Language.ENGLISH);

      // http://www.botlibre.com/browse?id=132686
      String url = this.botlibreUrl + "application={application}&instance={instance}&message={message}";
      Map<String, String> vars = new HashMap<String, String>();
      vars.put("application", this.botlibreApplicationId);
      vars.put("instance", this.botlibreInstanceId);
      vars.put("message", translatedText);
      BotlibreResponse result = this.restTemplate.getForObject(url, BotlibreResponse.class, vars);

      // Microsoft Translator API en to ja
      translatedText = Translate.execute(result.getMessage(), Language.ENGLISH, Language.JAPANESE);
      reaction.setAnswer(translatedText);
      //reaction.setPictureUrl(this.getPictureUrl());
      reaction.setPictureUrl("http://www.botlibre.com/" + result.getAvatar());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return reaction;
  }

  public void setBotlibreUrl(String botlibreUrl) {
    this.botlibreUrl = botlibreUrl;
  }

  public void setBotlibreApplicationId(String botlibreApplicationId) {
    this.botlibreApplicationId = botlibreApplicationId;
  }

  public void setBotlibreInstanceId(String botlibreInstanceId) {
    this.botlibreInstanceId = botlibreInstanceId;
  }

  public void setMicrosoftTranslatorApiClientId(String microsoftTranslatorApiClientId) {
    this.microsoftTranslatorApiClientId = microsoftTranslatorApiClientId;
  }

  public void setMicrosoftTranslatorApiClientSecret(String microsoftTranslatorApiClientSecret) {
    this.microsoftTranslatorApiClientSecret = microsoftTranslatorApiClientSecret;
  }

}
