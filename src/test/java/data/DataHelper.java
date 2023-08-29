package data;

import lombok.Value;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;


public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String number;
    }

    @Value
    public static class CardInvalid {
        private String number;
        private String month;
        private String year;
        private String cardOwner;
        private String CVC;
    }

    public static CardInfo getApprovedCardInfo() {

        return new CardInfo("4444 4444 4444 4441");
    }

    public static CardInfo getDeclinedCardInfo() {

        return new CardInfo("4444 4444 4444 4442");
    }

    public static Faker faker = new Faker(new Locale("en"));


    public static CardInvalid getCardExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        String date = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return new CardInvalid("4444 4444 4444 4441", month, year, "Card Holder", "748");
    }

    public static CardInvalid getCardFuture() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 10);
        String date = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return new CardInvalid("4444 4444 4444 4441", month, year, "Card Holder", "652");
    }

    public static String generateInvalidCardNumberShort() {
        return faker.number().digits(15);
    }

    public static String generateCardNumberNotInDB() {
        return faker.business().creditCardNumber();
    }

    public static String generateValidMonth() {
        int max = (LocalDate.now().getMonthValue());
        int min = 1;
        Random random = new Random();
        int month = faker.number().numberBetween(min, max);
        return String.format("%02d %n", month);
    }

    public static int generateValidYear(String pattern) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yy");
        String minimumYear = dateFormat.format(currentDate);
        int minYear = Integer.valueOf(minimumYear);
        int maxYear = minYear + 6;
        return faker.number().numberBetween(minYear, maxYear);
    }

    public static String generateValidOwnerName() {
        return faker.name().name();
    }

    public static String generateOneWordInvalidOwnerName() {
        return faker.name().firstName();
    }

    public static String generateCyrillicInvalidOwnerName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().name();
    }

    public static String generateInvalidOwnerNameNumbers() {
        int nameOfNumbers = faker.number().randomDigitNotZero();
        return Integer.toString(nameOfNumbers);
    }

    public static String generateValidCVCCode() {
        return faker.number().digits(3);
    }

    public static String generate1DigitNumber() {
        return faker.number().digits(1);
    }

    public static String generate2DigitsNumber() {
        return faker.number().digits(2);
    }


}
