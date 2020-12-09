package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.ClientData;
import ru.netology.data.Generator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebEntranceTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldBeAuthorized() {
        ClientData clientData = Generator.shouldBeActive();
        $("[data-test-id=login] [name=login]").setValue(clientData.getLogin());
        $("[data-test-id=password] [type=password]").setValue(clientData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldBeBlocked() {
        ClientData clientData = Generator.shouldBeBlocked();
        $("[data-test-id=login] [name=login]").setValue(clientData.getLogin());
        $("[data-test-id=password] [type=password]").setValue(clientData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification']").find(String.valueOf(exactText("Ошибка! Пользователь заблокирован")));
    }

    @Test
    void shouldBeInvalidLogin() {
        ClientData clientData = Generator.shouldBeInvalidLogin();
        $("[data-test-id=login] [name=login]").setValue(clientData.getLogin());
        $("[data-test-id=password] [type=password]").setValue(clientData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль")).waitUntil(visible, 4000);
    }

    @Test
    void shouldBeInvalidPassword() {
        ClientData clientData = Generator.shouldBeInvalidPassword();
        $("[data-test-id=login] [name=login]").setValue(clientData.getLogin());
        $("[data-test-id=password] [type=password]").setValue(clientData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль")).waitUntil(visible, 4000);
    }

}
