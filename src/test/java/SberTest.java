import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SberTest extends PapaTest {
    String insurance = "//span[text()='Страхование']";
    String travelerInsurance = "//div[@class=\"kit-col kit-col_lg_5 lg-menu__col\"]//a[text()='Страхование путешественников']";
    String textCheck = "//div[@class='kit-col_xs_12 kit-col_md_0 kit-col_lg_6 kit-col_xs-bottom_20 kit-col_lg-bottom_10 kit-col_xs-top_20 kit-col_lg-top_40']//h2[text()='Страхование путешественников' ]";
    String placeOrderOnline = "//b[text()='Оформить онлайн']";
    String minimalSum = "//h3[text()='Минимальная']";
    String placeOrder = "//button[@class='btn btn-primary btn-large']";
    String surname = "//input[@id='surname_vzr_ins_0']";
    String name = "//input[@id='name_vzr_ins_0']";
    String lastName = "//input[@id='person_lastName']";
    String firstName = "//input[@id='person_firstName']";
    String middleName = "//input[@id='person_middleName']";
    String firstBirthDate = "//input[@id='birthDate_vzr_ins_0']";
    String firstBirthDateBar = "//vzr-online-insured//div[@class='pika-lendar']";
    String firstBirthYear = "//vzr-online-insured//option[@value='1990']";
    String firstBirthMonth = "//vzr-online-insured//option[@value='10']";
    String firstBirthDay = "//vzr-online-insured//button[@data-pika-day='12']";
    String birthDate = "//div[@class='col-12']//input[@id='person_birthDate']";
    String birthDateBar = "//div[@class='col-12']//div[@class='pika-lendar']";
    String birthYear = "//div[@class='col-12']//option[@value='1995']";
    String birthMonth = "//div[@class='col-12']//option[@value='11']";
    String birthDay = "//div[@class='col-12']//button[@data-pika-day='26']";
    String passSeries = "//input[@id='passportSeries']";
    String passNum = "//input[@id='passportNumber']";
    String documentDate = "//div[@class='col-sm-6 col-12']//input[@id='documentDate']";
    String registrationDateBar = "//div[@class='col-sm-6 col-12']//div[@class='pika-lendar']";
    String registrationYear = "//div[@class='col-sm-6 col-12']//option[@value='2017']";
    String registrationMonth = "//div[@class='col-sm-6 col-12']//option[@value=4]";
    String registrationDay = "//div[@class='col-sm-6 col-12']//span[@id='documentDate_datepicker']//button[@data-pika-day='10']";
    String documentIssue = "//input[@id='documentIssue']";
    String continueButton = "//button[@class='btn btn-primary page__btn']";
    String alert = "//div[@class='alert-form alert-form-error']";
    String sex = "//label[contains(text(), 'Мужской')]";

    @ParameterizedTest
    @MethodSource("fioParameters")
    public void mainTest(String lName, String fName, String mName) {
        driver.get("http://www.sberbank.ru/ru/person");

        click(insurance);
        click(travelerInsurance);

        waitUntilVisible(textCheck);
        assertThat("Надпись отсутствует", isDisplayed(textCheck));

        click(placeOrderOnline);

        waitUntilClickable(minimalSum);
        click(minimalSum);

        moveToElement(placeOrder);
        click(placeOrder);

        waitUntilClickable(surname);
        textInput(surname, "Petrov");
        Assert.assertEquals("Фамилия не соответствует ожиданиям", "Petrov", getAttribute(surname, "value"));

        textInput(name, "Alexandr");
        Assert.assertEquals("Имя не соответствует ожиданиям", "Alexandr", getAttribute(name, "value"));

        click(firstBirthDate);
        waitUntilVisible(firstBirthDateBar);
        click(firstBirthYear);
        click(firstBirthMonth);
        moveToElement(firstBirthDate);
        click(firstBirthDate);
        click(firstBirthDay);
        Assert.assertEquals("Дата рождения застрахованного не соответствует ожиданиям", "12.11.1990",
                getAttribute(firstBirthDate, "value"));

        textInput(lastName, lName);
        Assert.assertEquals("Фамилия не соответствует ожиданиям", lName, getAttribute(lastName, "value"));

        click(firstName);
        textInput(firstName, fName);
        Assert.assertEquals("Имя не соответствует ожиданиям", fName, getAttribute(firstName, "value"));

        click(middleName);
        textInput(middleName, mName);
        Assert.assertEquals("Отчество не соответствует ожиданиям", mName,
                getAttribute(middleName, "value"));

        click(birthDate);
        waitUntilVisible(birthDateBar);
        click(birthYear);
        click(birthMonth);
        moveToElement(birthDate);
        click(birthDate);
        click(birthDay);
        Assert.assertEquals("Дата рождения страхователя не соответствует ожиданиям", "26.12.1995",
                getAttribute(birthDate, "value"));

        click(sex);

        click(passSeries);
        textInput(passSeries, "1234");
        Assert.assertEquals("Серия паспорта не соответствует ожиданиям", "1234",
                getAttribute(passSeries, "value"));

        click(passNum);
        textInput(passNum, "567890");
        Assert.assertEquals("Номер паспорта не соответствует ожиданиям", "567890",
                getAttribute(passNum, "value"));

        click(documentDate);
        waitUntilVisible(registrationDateBar);
        click(registrationYear);
        click(registrationMonth);
        moveToElement(documentDate);
        click(documentDate);
        click(registrationDay);
        Assert.assertEquals("Дата регистрации не соответствует ожиданиям", "10.05.2017",
                getAttribute(documentDate, "value"));

        textInput(documentIssue, "УФМС РФ по ПГТ Простоквашино");
        Assert.assertEquals("Графа 'кем выдан' не соответствует ожиданиям",
                "УФМС РФ по ПГТ Простоквашино", getAttribute(documentIssue, "value"));

        click(continueButton);

        waitUntilVisible(alert);
        assertThat("Надпись не соответствует ожиданиям", isDisplayed(alert));


    }

    static Stream<Arguments> fioParameters() {
        return Stream.of(
                arguments("Иванов", "Иван", "Иванович"),
                arguments("Петров", "Петр", "Петрович"),
                arguments("Васильев", "Василий", "Васильевич")
        );
    }
}
