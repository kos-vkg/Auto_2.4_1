package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyCardsPage {

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement formMyCards = $(withText("Ваши карты"));
    private SelenideElement errorMessage = $(".notification__content");
    private SelenideElement buttonRefresh = $("[data-test-id=action-reload]");

    public MyCardsPage() {
        formMyCards.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public String isErrMessage() {
        errorMessage.shouldBe(Condition.visible);
        String text = errorMessage.getText();
        return text;
    }

    private Integer extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public Integer getSum(int numCard) {
        String text = $$(".list__item").get(numCard).$("[data-test-id]").text();
        return extractBalance(text);
    }

    public TransferPage clickUpCard(int numCard) {
        $$(".list__item").get(numCard).$("button").click();
        return new TransferPage();
    }

    public void transfer(int numSourceCard, int numTargetCard, int summTransf, String codeSourceCard) {
        TransferPage transfer = clickUpCard(numTargetCard);
        transfer.setData(summTransf, codeSourceCard);
        transfer.clickOk();
    }
}
