package fummy.jokebot.bot;

public class DocomoApiConfig {

  private String apikey;
  
  private String dialogueUrl;

  public String getDialogueUrl() {
    return dialogueUrl;
  }

  public void setDialogueUrl(String dialogueUrl) {
    this.dialogueUrl = dialogueUrl;
  }

  public String getApikey() {
    return apikey;
  }

  public void setApikey(String apikey) {
    this.apikey = apikey;
  }
}
