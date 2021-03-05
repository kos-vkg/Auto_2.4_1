package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
  public DataHelper() {}

  private static final String[] ARR_CARD = {"5559 0000 0000 0001", "5559 0000 0000 0002", "5559 0000 0000 0003"};

  @Value static class CardInfo{
    private String codeCard;
  }

  public static String getCardByIndex(int index){
   return  new CardInfo(ARR_CARD[index]).getCodeCard();
  }

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }
}
