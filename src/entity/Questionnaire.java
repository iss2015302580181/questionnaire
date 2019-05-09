package entity;


import java.sql.Timestamp;

public class Questionnaire {

  private int questionnaireId;
  private int creatorId;
  private String queTitle;
  private String queDis;
  private int isPublished;
  private Timestamp timestamp;
  private int isOutOfDate;

  public int getIsOutOfDate() {
    return isOutOfDate;
  }

  public void setIsOutOfDate(int isOutOfDate) {
    this.isOutOfDate = isOutOfDate;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public int getQuestionnaireId() {
    return questionnaireId;
  }

  public void setQuestionnaireId(int questionnaireId) {
    this.questionnaireId = questionnaireId;
  }


  public int getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(int creatorId) {
    this.creatorId = creatorId;
  }


  public String getQueTitle() {
    return queTitle;
  }

  public void setQueTitle(String queTitle) {
    this.queTitle = queTitle;
  }


  public String getQueDis() {
    return queDis;
  }

  public void setQueDis(String queDis) {
    this.queDis = queDis;
  }


  public int getIsPublished() {
    return isPublished;
  }

  public void setIsPublished(int isPublished) {
    this.isPublished = isPublished;
  }

}
