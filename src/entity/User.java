package entity;


public class User {

  private int userId;
  private String userName;
  private int type;
  private String userPsd;


  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public String getUserPsd() {
    return userPsd;
  }

  public void setUserPsd(String userPsd) {
    this.userPsd = userPsd;
  }

}
