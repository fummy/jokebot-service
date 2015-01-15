package fummy.jokebot.bot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reaction {

  @JsonProperty("bot_id")
  private Integer id;

  @JsonProperty("bot_name")
  private String name;
  
  private String answer;

  @JsonProperty("picture_url")
  private String pictureUrl;

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
