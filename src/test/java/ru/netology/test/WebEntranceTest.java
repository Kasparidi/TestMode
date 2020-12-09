package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Generator;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebEntranceTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void happyPath() {
        $("[data-test-id=login] [name=login]").setValue("vasya");
        $("[data-test-id=password] [type=password]").setValue("password");
        $("[data-test-id=action-login] [class=button__text]").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldBeAuthorized() {
        $("[data-test-id=login] [name=login]").setValue(Generator.generateActive("en").getLogin());
        $("[data-test-id=password] [type=password]").setValue(Generator.generateActive("en").getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }


}
