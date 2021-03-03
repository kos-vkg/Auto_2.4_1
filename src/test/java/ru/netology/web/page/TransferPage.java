package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement formTransfer = $(withText("Пополнение карты"));
    private SelenideElement summaTransfer = $(".input__control[type='text']");//$("[data-test-id='amount']");
    private SelenideElement sourceTransfer = $(".input__control[type='tel']");// $("[data-test-id='from']");
    private SelenideElement buttonUpCard = $("[data-test-id='action-transfer']");
    private SelenideElement buttonCancel = $("[data-test-id='action-cancel']");

    public TransferPage() {
        formTransfer.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public MyCardsPage clickCancel() {
        buttonCancel.click();
        return new MyCardsPage();
    }

    public void clickOk() {
        buttonUpCard.click();
        return;
    }

    public void setData(int summa, String numCard) {
        final String b16 = "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";
        final String b9 = "\b\b\b\b\b\b\b\b\b";
        sourceTransfer.setValue(b16 + numCard);
        summaTransfer.setValue(b9 + Integer.toString(summa));
    }


}
