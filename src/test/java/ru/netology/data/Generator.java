package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Locale;
import static io.restassured.RestAssured.given;


public class Generator {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUpAll(ClientData clientData) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(clientData) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static ClientData shouldBeActive() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        setUpAll(new ClientData(login, password, "active"));
        return new ClientData(login, password,"active");
    }

    public static ClientData shouldBeBlocked() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        setUpAll(new ClientData(login, password, "blocked"));
        return new ClientData(login, password,"blocked");
    }

    public static ClientData shouldBeInvalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        String status = "active";
        setUpAll(new ClientData("оксана", password, status));
        return new ClientData("оксана", password, status);
    }

    public static ClientData shouldBeInvalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String status = "active";
        setUpAll(new ClientData(login, "русский", status));
        return new ClientData(login, "русский", status);
    }
}
