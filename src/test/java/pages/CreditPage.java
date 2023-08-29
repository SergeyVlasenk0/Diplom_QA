package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.HOME;
import static org.openqa.selenium.Keys.SHIFT;

public class CreditPage {

    private SelenideElement creditHeading = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumber = $("fieldset > div:nth-of-type(1) .input__control");
    private SelenideElement month = $$("fieldset > div:nth-of-type(2) .input__control").first();
    private SelenideElement year = $$("fieldset > div:nth-of-type(2) .input__control").last();
    private SelenideElement cardOwner = $$("fieldset > div:nth-of-type(3) .input__control").first();

    private SelenideElement CVCCode = $$("fieldset > div:nth-of-type(3) .input__control").last();
    private SelenideElement continueButton = $$("[type= 'button']").find(Condition.exactText("Продолжить"));

    private SelenideElement errorBankNotification = $$(".notification__content").find(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successPayNotification = $$(".notification__content").find(Condition.exactText("Операция одобрена Банком."));


    private SelenideElement cardErrorSub = $("fieldset > .form-field > .input .input__sub");
    private SelenideElement monthErrorSub = $("fieldset > div:nth-of-type(2) > span:nth-of-type(1) > span:nth-of-type(1) span:nth-of-type(3)");
    private SelenideElement yearErrorSub = $("fieldset > div:nth-of-type(2) span:nth-of-type(2) span:nth-of-type(3)");
    private SelenideElement ownerErrorSub = $("fieldset > div:nth-of-type(3) > span:nth-of-type(1) > span:nth-of-type(1) span:nth-of-type(3)");
    private SelenideElement cvcErrorSub = $("fieldset > div:nth-of-type(3) span:nth-of-type(2) span:nth-of-type(3)");


    public CreditPage() {
        creditHeading.shouldBe(Condition.visible);
        cardNumber.shouldBe(Condition.visible);
        month.shouldBe(Condition.visible);
        year.shouldBe(Condition.visible);
        cardOwner.shouldBe(Condition.visible);
        CVCCode.shouldBe(Condition.visible);
        continueButton.shouldBe(Condition.visible);
    }

    public void successVerify() {
        successPayNotification.shouldBe(Condition.visible, Duration.ofSeconds(40));
        errorBankNotification.shouldBe(Condition.hidden, Duration.ofSeconds(40));
    }

    public void failureVerify() {
        errorBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(35));
        successPayNotification.shouldBe(Condition.hidden, Duration.ofSeconds(30));
    }

    public void creditPayment(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        cardOwner.setValue(cardHolder);
        CVCCode.setValue(CVC);
        continueButton.click();
    }

    public CreditPage successfulCreditPayment(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        successVerify();
        return new CreditPage();
    }

    public CreditPage failedCreditPayment(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        failureVerify();
        return new CreditPage();
    }

    public void creditPaymentNoCard(String expireMonth, String expireYear, String cardHolder, String CVC) {
        cardNumber.sendKeys(Keys.chord(SHIFT, HOME), Keys.BACK_SPACE);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        cardOwner.setValue(cardHolder);
        CVCCode.setValue(CVC);
        continueButton.click();
    }

    public void cardNumberError(String errorText) {
        cardErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        monthErrorSub.shouldBe(Condition.hidden);
        yearErrorSub.shouldBe(Condition.hidden);
        ownerErrorSub.shouldBe(Condition.hidden);
        cvcErrorSub.shouldBe(Condition.hidden);
    }

    public CreditPage creditPaymentCardNumberEmptyField(String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPaymentNoCard(expireMonth, expireYear, cardHolder, CVC);
        cardNumberError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentCardNumberShort(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        cardNumberError(errorText);
        return new CreditPage();
    }

    public void creditPaymentNoMonth(String cardInfo, String expireYear, String cardHolder, String CVC) {
        cardNumber.setValue(cardInfo);
        year.setValue(expireYear);
        cardOwner.setValue(cardHolder);
        CVCCode.setValue(CVC);
        continueButton.click();
    }

    public void monthError(String errorText) {
        monthErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        cardErrorSub.shouldBe(Condition.hidden);
        yearErrorSub.shouldBe(Condition.hidden);
        ownerErrorSub.shouldBe(Condition.hidden);
        cvcErrorSub.shouldBe(Condition.hidden);
    }

    public CreditPage creditPaymentMonthEmptyField(String cardInfo, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPaymentNoMonth(cardInfo, expireYear, cardHolder, CVC);
        monthError(errorText);
        return new CreditPage();
    }

    public void creditPaymentNoYear(String cardInfo, String expireMonth, String cardHolder, String CVC) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        cardOwner.setValue(cardHolder);
        CVCCode.setValue(CVC);
        continueButton.click();
    }

    public void yearError(String errorText) {
        yearErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        cardErrorSub.shouldBe(Condition.hidden);
        monthErrorSub.shouldBe(Condition.hidden);
        ownerErrorSub.shouldBe(Condition.hidden);
        cvcErrorSub.shouldBe(Condition.hidden);
    }

    public CreditPage creditPaymentYearEmptyField(String cardInfo, String expireMonth, String cardHolder, String CVC, String errorText) {
        creditPaymentNoYear(cardInfo, expireMonth, cardHolder, CVC);
        yearError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentNoOwner(String cardInfo, String expireMonth, String expireYear, String CVC) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        CVCCode.setValue(CVC);
        continueButton.click();
        return new CreditPage();
    }

    public void ownerError(String errorText) {
        ownerErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        cardErrorSub.shouldBe(Condition.hidden);
        monthErrorSub.shouldBe(Condition.hidden);
        yearErrorSub.shouldBe(Condition.hidden);
        cvcErrorSub.shouldBe(Condition.hidden);
    }

    public CreditPage creditPaymentOwnerEmptyField(String cardInfo, String expireMonth, String expireYear, String CVC, String errorText) {
        creditPaymentNoOwner(cardInfo, expireMonth, expireYear, CVC);
        ownerError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentNoCVC(String cardInfo, String expireMonth, String expireYear, String cardHolder) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        cardOwner.setValue(cardHolder);
        continueButton.click();
        return new CreditPage();
    }

    public void cvcError(String errorText) {
        cvcErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        cardErrorSub.shouldBe(Condition.hidden);
        monthErrorSub.shouldBe(Condition.hidden);
        yearErrorSub.shouldBe(Condition.hidden);
        ownerErrorSub.shouldBe(Condition.hidden);
    }

    public CreditPage creditPaymentCVCEmptyField(String cardInfo, String expireMonth, String expireYear, String cardHolder, String errorText) {
        creditPaymentNoCVC(cardInfo, expireMonth, expireYear, cardHolder);
        cvcError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentCardNumberWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        cardNumberError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentMonthWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        monthError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentYearWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        yearError("Неверный формат");
        return new CreditPage();
    }


    public CreditPage creditPaymentOwnerWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        ownerError(errorText);
        return new CreditPage();
    }

    public CreditPage creditPaymentCVCWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        creditPayment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        cvcError(errorText);
        return new CreditPage();
    }


}
