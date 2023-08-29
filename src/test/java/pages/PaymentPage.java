package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.HOME;
import static org.openqa.selenium.Keys.SHIFT;

public class PaymentPage {
    private SelenideElement paymentHeading = $(byText("Оплата по карте"));
    private SelenideElement cardNumber = $("fieldset > div:nth-of-type(1) .input__control");
    private SelenideElement month = $$("fieldset > div:nth-of-type(2) .input__control").first();
    private SelenideElement year = $$("fieldset > div:nth-of-type(2) .input__control").last();
    private SelenideElement cardOwner = $$("fieldset > div:nth-of-type(3) .input__control").first();

    private SelenideElement CVCCode = $$("fieldset > div:nth-of-type(3) .input__control").last();
    private SelenideElement continueButton = $$("[type= 'button']").findBy(Condition.exactText("Продолжить"));

    private SelenideElement errorBankNotification= $$(".notification__content").findBy(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successPayNotification= $$(".notification__content").findBy(Condition.exactText("Операция одобрена Банком."));

    private SelenideElement cardErrorSub= $("fieldset > .form-field > .input .input__sub");
    private SelenideElement monthErrorSub= $("fieldset > div:nth-of-type(2) > span:nth-of-type(1) > span:nth-of-type(1) span:nth-of-type(3)");
    private SelenideElement yearErrorSub= $("fieldset > div:nth-of-type(2) span:nth-of-type(2) span:nth-of-type(3)");
    private SelenideElement ownerErrorSub= $("fieldset > div:nth-of-type(3) > span:nth-of-type(1) > span:nth-of-type(1) span:nth-of-type(3)");
    private SelenideElement cvcErrorSub= $("fieldset > div:nth-of-type(3) span:nth-of-type(2) span:nth-of-type(3)");

    public PaymentPage(){
        paymentHeading.shouldBe(Condition.visible);
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
        errorBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(30));
        successPayNotification.shouldBe(Condition.hidden, Duration.ofSeconds(35));
    }
    public void payment(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        cardOwner.setValue(cardHolder);
        CVCCode.setValue(CVC);
        continueButton.click();
    }

    public PaymentPage successfulPayment(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC){
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        successVerify();
        return new PaymentPage();
    }
    public PaymentPage failedPayment(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        failureVerify();
        return new PaymentPage();
    }
    public void paymentNoCard(String expireMonth, String expireYear,String cardHolder, String CVC) {
        cardNumber.sendKeys(Keys.chord(SHIFT, HOME),Keys.BACK_SPACE);
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
    public PaymentPage paymentCardNumberEmptyField(String expireMonth, String expireYear,String cardHolder, String CVC, String errorText) {
        paymentNoCard(expireMonth, expireYear, cardHolder, CVC);
        cardNumberError(errorText);
        return new PaymentPage();
    }

    public PaymentPage paymentCardNumberShort(String cardInfo, String expireMonth, String expireYear,String cardHolder, String CVC, String errorText) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        cardNumberError(errorText);
        return new PaymentPage();
    }

    public void paymentNoMonth(String cardInfo, String expireYear, String cardHolder, String CVC) {
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
    public PaymentPage paymentMonthEmptyField(String cardInfo, String expireYear,String cardHolder, String CVC, String errorText) {
        paymentNoMonth(cardInfo, expireYear, cardHolder, CVC);
        monthError(errorText);
        return new PaymentPage();
    }
    public void paymentNoYear(String cardInfo, String expireMonth, String cardHolder, String CVC) {
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

    public PaymentPage paymentYearEmptyField(String cardInfo, String expireMonth, String cardHolder, String CVC, String errorText){
        paymentNoYear(cardInfo, expireMonth, cardHolder, CVC);
        yearError(errorText);
        return new PaymentPage();
    }

    public PaymentPage paymentNoOwner(String cardInfo, String expireMonth, String expireYear, String CVC) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        CVCCode.setValue(CVC);
        continueButton.click();
        return new PaymentPage();
    }
    public void ownerError(String errorText) {
        ownerErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        cardErrorSub.shouldBe(Condition.hidden);
        monthErrorSub.shouldBe(Condition.hidden);
        yearErrorSub.shouldBe(Condition.hidden);
        cvcErrorSub.shouldBe(Condition.hidden);
    }
    public PaymentPage paymentOwnerEmptyField(String cardInfo, String expireMonth, String expireYear, String CVC, String errorText){
        paymentNoOwner(cardInfo, expireMonth, expireYear, CVC);
        ownerError(errorText);
        return new PaymentPage();
    }

    public PaymentPage paymentNoCVC(String cardInfo, String expireMonth, String expireYear, String cardHolder) {
        cardNumber.setValue(cardInfo);
        month.setValue(expireMonth);
        year.setValue(expireYear);
        cardOwner.setValue(cardHolder);
        continueButton.click();
        return new PaymentPage();
    }
    public void cvcError(String errorText) {
        cvcErrorSub.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        cardErrorSub.shouldBe(Condition.hidden);
        monthErrorSub.shouldBe(Condition.hidden);
        yearErrorSub.shouldBe(Condition.hidden);
        ownerErrorSub.shouldBe(Condition.hidden);
    }
    public PaymentPage paymentCVCEmptyField(String cardInfo, String expireMonth, String expireYear, String cardHolder, String errorText) {
        paymentNoCVC(cardInfo, expireMonth, expireYear, cardHolder);
        cvcError(errorText);
        return new PaymentPage();
    }

    public PaymentPage paymentCardNumberWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        cardNumberError(errorText);
        return new PaymentPage();
    }


    public PaymentPage paymentMonthWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        monthError(errorText);
        return new PaymentPage();
    }

    public PaymentPage paymentYearWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        yearError("Неверный формат");
        return new PaymentPage();
    }

    public PaymentPage paymentOwnerWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        ownerError(errorText);
        return new PaymentPage();
    }

    public PaymentPage paymentCVCWrongFormat(String cardInfo, String expireMonth, String expireYear, String cardHolder, String CVC, String errorText) {
        payment(cardInfo, expireMonth, expireYear, cardHolder, CVC);
        cvcError(errorText);
        return new PaymentPage();
    }


}
