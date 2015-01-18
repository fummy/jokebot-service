package fummy.jokebot.bot;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface JokeBot {
  
  @JsonProperty("bot_id")
  public int getId();

  @JsonProperty("bot_name")
  public String getName();

  @JsonProperty("profile")
  public String getProfile();

  @JsonProperty("picture_url")
  public String getPictureUrl();
  
  public Reaction reaction(String keyword);
}
