package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement heading = $(byText("Путешествие дня"));
    private SelenideElement paymentButton = $$("[type= 'button']").first();
    private SelenideElement creditButton = $$("[type= 'button']").last();

    public MainPage() {
        heading.shouldBe(Condition.visible);
        paymentButton.shouldBe(Condition.visible);
        creditButton.shouldBe(Condition.visible);
    }
    public PaymentPage tourPayment(){
        paymentButton.click();
        return new PaymentPage();
    }
    public CreditPage tourCreditPayment(){
        creditButton.click();
        return new CreditPage();
    }

}
