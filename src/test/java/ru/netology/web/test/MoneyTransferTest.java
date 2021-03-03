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

    @Test
    void shouldHappyPathCard1() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int summTransf = 5000;
        int initSum = myCardsPage.ballansToInit(numSourceCard, numTargetCard);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM + summTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - summTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldHappyPathCard0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 1;
        int summTransf = 5000;
        int initSum = myCardsPage.ballansToInit(numSourceCard, numTargetCard);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM + summTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - summTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldFullSumCard1() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int summTransf = 10000;
        int initSum = myCardsPage.ballansToInit(numSourceCard, numTargetCard);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM + summTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - summTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldFullSumCard0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 1;
        int summTransf = 10000;
        int initSum = myCardsPage.ballansToInit(numSourceCard, numTargetCard);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM + summTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM - summTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldTransferHimself0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 0;
        int summTransf = 1000;
        int initSum = myCardsPage.ballansToInit(0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

    @Test
    void shouldTransferByWrongCard() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 2;
        int numTargetCard = 0;
        int summTransf = 1000;
        int initSum = myCardsPage.ballansToInit(0, 1);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        String errText = myCardsPage.isErrMessage();
        assertEquals("Ошибка! Произошла ошибка", errText);
    }

    @Test
    void shouldShortageCard1() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 1;
        int numTargetCard = 0;
        int summTransf = 12000;
        int initSum = myCardsPage.ballansToInit(numSourceCard, numTargetCard);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM; // + summTransf;

        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM; // - summTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
        System.out.println("s:" + expectedSource + " t:" + expectedTarget);
    }

    @Test
    void shouldShortageCard0() {
        MyCardsPage myCardsPage = initCardsPage();
        int numSourceCard = 0;
        int numTargetCard = 1;
        int summTransf = 12000;
        int initSum = myCardsPage.ballansToInit(numSourceCard, numTargetCard);
        assertEquals(INIT_SUM, initSum);
        myCardsPage.transfer(numSourceCard, numTargetCard, summTransf);
        int expectedTarget = INIT_SUM; // + summTransf;
        assertEquals(expectedTarget, myCardsPage.getSum(numTargetCard));
        int expectedSource = INIT_SUM; // - summTransf;
        assertEquals(expectedSource, myCardsPage.getSum(numSourceCard));
    }

}

