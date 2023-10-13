package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardOrderTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldTestWithYInNames() {
        $("input[name='name']").setValue("Йован Йованович");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestWithYoInNames() {
        $("input[name='name']").setValue("Алена Попова");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestWithDashInNames() {
        $("input[name='name']").setValue("Иван Иванов-Петров");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestWithoutName() {
        $("input[name='name']").setValue("");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithCapitalLettersInNames() {
        $("input[name='name']").setValue("ОЛЕГ СИДОРОВ");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestNameWithNumbersInNames() {
        $("input[name='name']").setValue("ОЛЕГ1 СИДОРОВ2");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWithLatinLettersInNames() {
        $("input[name='name']").setValue("Wout Weghorst");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWithSpacesInNames() {
        $("input[name='name']").setValue(" О л е г С и д о р о в ");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestWithSymbolsInNames() {
        $("input[name='name']").setValue("!&7[{]} %*:");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWithoutPhone() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldTestPhoneWithoutPlus() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("86665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestPhoneLess11Numbers() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("6665554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestPhoneMore11Numbers() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("+766655544333");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestPhoneWithSymbols() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("+7-666-555-44-33");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestPhoneWithLetter() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("+7Ю66Q554433");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestAgreementBoxChecked() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").click();
        $("[data-test-id=agreement].checkbox_checked").isEnabled();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestAgreementBoxUnchecked() {
        $("input[name='name']").setValue("Олег Сидоров");
        $("input[type='tel']").setValue("+76665554433");
        $("[data-test-id=agreement]").doubleClick();
        $x("//button").click();
        $("label.input_invalid").shouldBe(visible);
    }
}