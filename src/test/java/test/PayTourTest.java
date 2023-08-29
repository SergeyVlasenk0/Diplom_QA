package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.CreditPage;
import pages.MainPage;
import pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;


public class PayTourTest {

    PaymentPage paymentPage;
    CreditPage creditPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Open payment page")
    void openPaymentPage() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
    }

    @Test
    @DisplayName("Open credit page")
    void openCreditPage() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
    }

    @Test
    @DisplayName("Successful card payment with APPROVED card")
    void successfulCardPaymentWithAPPROVEDCard() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var successfulPayment = paymentPage.successfulPayment(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode());
        var getPaymentStatus = SQLHelper.getPaymentStatus();
        var getPaymentId = SQLHelper.getPaymentIdFromDB();
        var getOrderInfo = SQLHelper.getPaymentInfoFromDB();
        assertAll("Получить статус оплаты 'APPROVED' и наличие заказа в БД",
                () -> assertEquals("APPROVED", getPaymentStatus),
                () -> assertEquals(getPaymentId, getOrderInfo)
        );
    }

    @Test
    @DisplayName("Successful credit payment with APPROVED card")
    void successfulCreditPaymentWithAPPROVEDCard() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var successfulCreditPayment = creditPage.successfulCreditPayment(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode());
        var getCreditStatus = SQLHelper.getCreditStatus();
        var getCreditId = SQLHelper.getCreditRequestIdFromDB();
        var getOrderInfo = SQLHelper.getCreditRequestInfoFromDB();
        assertAll("Получить статус оплаты 'APPROVED' по кредитной карте и наличие заказа в БД",
                () -> assertEquals("APPROVED", getCreditStatus),
                () -> assertEquals(getCreditId, getOrderInfo)
        );
    }

    @Test
    @DisplayName("Failed card payment with DECLINED card")
    void failedCardPaymentWithDECLINEDCard() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var declinedCard = DataHelper.getDeclinedCardInfo();
        var invalidCardPayment = paymentPage.failedPayment(String.valueOf(DataHelper.getDeclinedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode());
        var getPaymentStatus = SQLHelper.getPaymentStatus();
        var getPaymentInfo = SQLHelper.getPaymentIdFromDB();
        var getOrderInfo = SQLHelper.getPaymentInfoFromDB();
        assertAll("Получить статус оплаты 'DECLINED' и отсутствие заказа в БД",
                () -> assertEquals("DECLINED", getPaymentStatus),
                () -> assertNotEquals(getPaymentInfo, getOrderInfo)
        );
    }

    @Test
    @DisplayName("Failed credit payment with DECLINED card")
    void failedCreditPaymentWithDECLINEDCard() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var declinedCard = DataHelper.getDeclinedCardInfo();
        var failedCreditPayment = creditPage.failedCreditPayment(String.valueOf(DataHelper.getDeclinedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode());
        var getCreditStatus = SQLHelper.getCreditStatus();
        var getCreditId = SQLHelper.getCreditRequestIdFromDB();
        var getOrderInfo = SQLHelper.getCreditRequestInfoFromDB();
        assertAll("Получить статус оплаты 'DECLINED' по кредитной карте и отсутствие заказа в БД",
                () -> assertEquals("DECLINED", getCreditStatus),
                () -> assertNotEquals(getCreditId, getOrderInfo)
        );
    }

    @Test
    @DisplayName("Error when card number field is empty")
    void errorWhenCardNumberFieldIsEmpty() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var paymentNoCard = paymentPage.paymentCardNumberEmptyField(DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when card number field is empty with credit pay")
    void errorWhenCardNumberFieldIsEmptyWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var creditPaymentNoCard = creditPage.creditPaymentCardNumberEmptyField(DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when month field is empty with card pay")
    void errorWhenMonthFieldIsEmptyWithCardPay() {
        var mainPage = new MainPage();
        var paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentNoMonth = paymentPage.paymentMonthEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when month field is empty with credit pay")
    void errorWhenMonthFieldIsEmptyWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var creditPaymentNoCard = creditPage.creditPaymentMonthEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when year field is empty with card pay")
    void errorWhenYearFieldIsEmptyWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentNoYear = paymentPage.paymentYearEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when year field is empty with credit pay")
    void errorWhenYearFieldIsEmptyWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentNoYear = creditPage.creditPaymentYearEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when card owner field is empty with card pay")
    void errorWhenCardOwnerFieldIsEmptyWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentNoOwner = paymentPage.paymentOwnerEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when card owner field is empty with credit pay")
    void errorWhenCardOwnerFieldIsEmptyWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentNoOwner = creditPage.creditPaymentOwnerEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidCVCCode(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when CVC field is empty with card pay")
    void errorWhenCVCFieldIsEmptyWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentNoCVC = paymentPage.paymentCVCEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when CVC field is empty with credit pay")
    void errorWhenCVCFieldIsEmptyWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentNoCVC = creditPage.creditPaymentCVCEmptyField(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Error when card number is short with card pay")
    void errorWhenCardNumberIsShortWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var paymentShortCardNumber = paymentPage.paymentCardNumberShort(String.valueOf(DataHelper.generateInvalidCardNumberShort()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when card number is short with credit pay")
    void errorWhenCardNumberIsShortWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var creditPaymentShortCardNumber = creditPage.creditPaymentCardNumberShort(String.valueOf(DataHelper.generateInvalidCardNumberShort()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when card number is not in DB with card pay")
    void errorWhenCardNumberIsNotInDBWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var paymentCardNotInDB = paymentPage.failedPayment(DataHelper.generateCardNumberNotInDB(),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode());
    }

    @Test
    @DisplayName("Error when card number is not in DB with credit pay")
    void errorWhenCardNumberIsNotInDBWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var declinedCard = DataHelper.getDeclinedCardInfo();
        var failedCreditPayment = creditPage.failedCreditPayment(String.valueOf(DataHelper.generateCardNumberNotInDB()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode());
    }

    @Test
    @DisplayName("Error when 1 digit in month field with card pay")
    void errorWhen1DigitInMonthFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var payment1DigitMonth = paymentPage.paymentMonthWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generate1DigitNumber(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 digit in month field with credit pay")
    void errorWhen1DigitInMonthFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPayment1DigitMonth = creditPage.creditPaymentMonthWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generate1DigitNumber(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when card expired date with card pay")
    void errorWhenCardExpiredDateWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var expiredCard = DataHelper.getCardExpired();
        paymentPage.payment(DataHelper.getCardExpired().getNumber(),
                DataHelper.getCardExpired().getMonth(),
                DataHelper.getCardExpired().getYear(),
                DataHelper.getCardExpired().getCardOwner(),
                DataHelper.getCardExpired().getCVC());
        paymentPage.yearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Error when card expired date with credit pay")
    void errorWhenCardExpiredDateWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var expiredCard = DataHelper.getCardExpired();
        creditPage.creditPayment(DataHelper.getCardExpired().getNumber(),
                DataHelper.getCardExpired().getMonth(),
                DataHelper.getCardExpired().getYear(),
                DataHelper.getCardExpired().getCardOwner(),
                DataHelper.getCardExpired().getCVC());
        creditPage.yearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Error when card is future date with card pay")
    void errorWhenCardIsFutureDateWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var expiredCard = DataHelper.getCardFuture();
        paymentPage.payment(DataHelper.getCardFuture().getNumber(),
                DataHelper.getCardFuture().getMonth(),
                DataHelper.getCardFuture().getYear(),
                DataHelper.getCardFuture().getCardOwner(),
                DataHelper.getCardFuture().getCVC());
        paymentPage.yearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Error when card is future date with credit pay")
    void errorWhenCardIsFutureDateWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var expiredCard = DataHelper.getCardFuture();
        creditPage.creditPayment(DataHelper.getCardFuture().getNumber(),
                DataHelper.getCardFuture().getMonth(),
                DataHelper.getCardFuture().getYear(),
                DataHelper.getCardFuture().getCardOwner(),
                DataHelper.getCardFuture().getCVC());
        creditPage.yearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Error when zero in month field with card pay")
    void errorWhenZeroInMonthFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentZerosMonth = paymentPage.paymentMonthWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                "00",
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when zero in month field with credit pay")
    void errorWhenZeroInMonthFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentZerosMonth = creditPage.creditPaymentMonthWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                "00",
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 digit in year field with card pay")
    void errorWhen1DigitInYearFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentWrongYearFormat = paymentPage.paymentYearWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generate1DigitNumber()),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 digit in year field with credit pay")
    void errorWhen1DigitInYearFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentWrongYearFormat = creditPage.creditPaymentYearWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generate1DigitNumber()),
                DataHelper.generateValidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 word in card owner field with card pay")
    void errorWhen1WordInCardOwnerFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentOneWordOwner = paymentPage.paymentOwnerWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateOneWordInvalidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 word in card owner field with credit pay")
    void errorWhen1WordInCardOwnerFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentOneWordOwner = creditPage.creditPaymentOwnerWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateOneWordInvalidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when RU in card owner field with card pay")
    void errorWhenRUInCardOwnerFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentCyrillicOwner = paymentPage.paymentOwnerWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateCyrillicInvalidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when RU in card owner field with credit pay")
    void errorWhenRUInCardOwnerFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentCyrillicOwner = creditPage.creditPaymentOwnerWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateCyrillicInvalidOwnerName(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when numbers in card owner field with card pay")
    void errorWhenNumbersInCardOwnerFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentNumbersOwner = paymentPage.paymentOwnerWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateInvalidOwnerNameNumbers(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when numbers in card owner field with credit pay")
    void errorWhenNumbersInCardOwnerFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentNumbersOwner = creditPage.creditPaymentOwnerWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateInvalidOwnerNameNumbers(),
                DataHelper.generateValidCVCCode(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 digit in CVC field with card pay")
    void errorWhen1DigitInCVCFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var payment1DigitCVC = paymentPage.paymentCVCWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generate1DigitNumber(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 1 digit in CVC field with credit pay")
    void errorWhen1DigitInCVCFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPayment1DigitCVC = creditPage.creditPaymentCVCWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generate1DigitNumber(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 2 digits in CVC field with card pay")
    void errorWhen2DigitsInCVCFieldWithCardPay() {
        var mainPage = new MainPage();
        paymentPage = mainPage.tourPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var paymentCyrillicOwner = paymentPage.paymentCVCWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generate2DigitsNumber(),
                "Неверный формат");
    }

    @Test
    @DisplayName("Error when 2 digits in CVC field with credit pay")
    void errorWhen2DigitsInCVCFieldWithCreditPay() {
        var mainPage = new MainPage();
        creditPage = mainPage.tourCreditPayment();
        var approvedCard = DataHelper.getApprovedCardInfo();
        var creditPaymentCyrillicOwner = creditPage.creditPaymentCVCWrongFormat(String.valueOf(DataHelper.getApprovedCardInfo()),
                DataHelper.generateValidMonth(),
                String.valueOf(DataHelper.generateValidYear("yy")),
                DataHelper.generateValidOwnerName(),
                DataHelper.generate2DigitsNumber(),
                "Неверный формат");
    }
}