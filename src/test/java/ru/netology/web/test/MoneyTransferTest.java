package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.MyCardsPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    private static final int INIT_SUM = 10000;

    private MyCardsPage initCardsPage() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        return new MyCardsPage();
    }

    private int ballansToInit(MyCardsPage myCardsPage, int card1, int card2) {
        // возвращает (выравнивает) суммы на картах
        int sum1 = myCardsPage.getSum(card1);
        int sum2 = myCardsPage.getSum(card2);
        int diffSum = sum1 - sum2;
        int numSourceCard = card1;
        int numTargetCard = card2;
        if (diffSum == 0) {
            return sum1;
        } else if (diffSum < 0) {
            numSourceCard = card2;
            numTargetCard = card1;
        }
        myCardsPage.transfer(numSourceCard, numTargetCard, -diffSum / 2, codeCard(numSourceCard));
        return (sum1 + sum2) / 2;
    }

    private String codeCard(int index) {
        return DataHelper.getCardByIndex(index);
    }

    @Test
    void shouldHappyPathCard1() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int sumTransf = 5000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM + sumTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - sumTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldHappyPathCard0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 1;
        int sumTransf = 5000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM + sumTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - sumTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldFullSumCard1() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int sumTransf = 10000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM + sumTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - sumTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldFullSumCard0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 1;
        int sumTransf = 10000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM + sumTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - sumTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldTransferHimself0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 0;
        int sumTransf = 1000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldTransferByWrongCard() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int sumTransf = 1000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(2));
        String errText = myCardsPage.isErrMessage();
        assertEquals("Ошибка! Произошла ошибка", errText);
    }

    @Test
    void shouldShortageCard1() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int sumTransf = 12000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM; // + sumTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM; // - sumTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldShortageCard0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 1;
        int sumTransf = 12000;
        int initSum = ballansToInit(myCardsPage, 0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, sumTransf, codeCard(numSourceCard));
        int expectedTarget = INIT_SUM; //+ sumTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM; // - sumTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }
}

