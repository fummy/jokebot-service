package fummy.jokebot.bot;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
public class BotlibreResponse {

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
