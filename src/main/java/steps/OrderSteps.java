package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static data.TestData.ORDERS_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step ("Создать заказ")
    public static Response createOrder(OrderModel orderModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(ORDERS_PATH)
                .then()
                .extract().response();
    }

    @Step ("Получить список заказов")
    public static Response getListOrders() {
        return given()
                .log().all()
                .get(ORDERS_PATH)
                .then()
                .extract().response();
    }
}
