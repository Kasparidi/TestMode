package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
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

    static Faker faker = new Faker(new Locale("en"));

    public static String generatePassword() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.bothify("???????");
    }

    public static String generateLogin() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.bothify("?????");
    }

    public static ClientData shouldBeActive() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        setUpAll(new ClientData(login, password, "active"));
        return new ClientData(login, password,"active");
    }

    public static ClientData shouldBeBlocked() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        setUpAll(new ClientData(login, password, "blocked"));
        return new ClientData(login, password,"blocked");
    }

    public static ClientData shouldBeInvalidLogin() {
        String password = faker.internet().password();
        String status = "active";
        setUpAll(new ClientData(generateLogin(), password, status));
        return new ClientData(generateLogin(), password, status);
    }

    public static ClientData shouldBeInvalidPassword() {
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        setUpAll(new ClientData(login, generatePassword(), status));
        return new ClientData(login, generatePassword(), status);
    }

    public static ClientData rewritingUser() {
        String login = faker.name().firstName().toLowerCase();
        ClientData firstUser = new ClientData(login, generatePassword(), "active");
        ClientData secondUser = new ClientData(login, generatePassword(), "active");
        setUpAll(firstUser);
        setUpAll(secondUser);
        return secondUser;
    }
}
