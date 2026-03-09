package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierId;
import model.CourierModel;

import static data.TestData.*;
import static io.restassured.RestAssured.given;

// Шаги для курьера
public class CourierSteps {

    @Step ("Создать курьера")
    public  static Response createCourier(CourierModel courierModel){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step ("Войти и получить ID курьера")
    public static Response authorizeCourier(CourierLogin courierLogin) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierLogin)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .extract().response();
    }

    @Step  ("Удалить курьера")
    public static void deleteCourier(int id) {
        given()
                .log().all()
                .when()
                .delete(DELETE_COURIER_PATH + id);
    }

    @Step  ("Удалить курьера по ID")
    public void cleanUp(CourierLogin courierLogin) {
        // Авторизироваться и получить ID
        Response response = authorizeCourier(courierLogin);
        CourierId courierId = response.as(CourierId.class);
        int id =  courierId.getId();
        deleteCourier(id);
    }

}
