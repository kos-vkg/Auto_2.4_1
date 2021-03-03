package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.*;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyCardsPage {

    private static final String[] ARR_CARD = {"5559 0000 0000 0001", "5559 0000 0000 0002", "5559 0000 0000 0003"};
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
        System.out.println(text);
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

    public void transfer(int numSourceCard, int numTargetCard, int summTransf) {
        TransferPage transfer = clickUpCard(numTargetCard);
        transfer.setData(summTransf, ARR_CARD[numSourceCard]);
        transfer.clickOk();
    }

    public int ballansToInit(int card1, int card2) {
        // возвращает (выравнивает) суммы на картах
        int sum1 = getSum(card1);
        int sum2 = getSum(card2);
        int diffSum = sum1 - sum2;
        int numSourceCard = card1;
        int numTargetCard = card2;
        if (diffSum == 0) {
            return getSum(card1);
        } else if (diffSum < 0) {
            numSourceCard = card2;
            numTargetCard = card1;
        }
        transfer(numSourceCard, numTargetCard, -diffSum / 2);
        return (sum1 + sum2) / 2;
    }
}
