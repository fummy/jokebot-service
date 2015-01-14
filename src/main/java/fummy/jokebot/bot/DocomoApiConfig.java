package fummy.jokebot.bot;

public class DocomoApiConfig {

  private String apikey;
  
  private String dialogueUrl;
  

  private String dialogueUrl2;

  public String getDialogueUrl2() {
    return dialogueUrl2;
  }

  public void setDialogueUrl2(String dialogueUrl2) {
    this.dialogueUrl2 = dialogueUrl2;
  }

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
