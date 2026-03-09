package data;

import com.github.javafaker.Faker;

public class TestData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    // Генерируем уникальные данные для курьера
    static Faker user = new Faker();
    public static final String LOGIN = user.name().lastName() + user.regexify("[0-9]{4}"); // добавляем несколько цифр для уникальности
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String FIRSTNAME = user.name().firstName();

    // Эндпоинты
    public static final String CREATE_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    public static final String DELETE_COURIER_PATH = "/api/v1/courier/";
    public static final String ORDERS_PATH = "/api/v1/orders";
}
