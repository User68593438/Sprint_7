import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierLogin;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.createCourier;

public class CourierCreateTest extends BaseApiTest {
    CourierModel courierModel;
    CourierLogin courierLogin;
    CourierSteps courierSteps;

    @Before
    public void testCreateCourier() {
        courierModel = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        courierLogin = new CourierLogin(LOGIN, PASSWORD);
        courierSteps = new CourierSteps();
    }


    @Test   // Создать курьера
    @DisplayName("Создать курьера. Позитивный тест")
    @Description("Курьера можно создать при заполнении всех обязательных полей валидными данными")
    public void createCourierTest() {
        createCourier(courierModel)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создать курьера с логином, который уже есть в системе")
    @Description("При создании курьера с логином, который уже есть в системе появляется ошибка: 409 Сonflict, текст: Этот логин уже используется")
    public void errorCreateAgainTest() {
        // Создать курьера первый раз
        createCourier(courierModel);
        // Отправить запрос с login который использовался в первый раз
        createCourier(courierModel)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создать курьера без заполнения одного из обязательных полей (login)")
    @Description("При создании курьера без заполнения поля login, запрос возвращает ошибку 400 Bad Request, текст: Недостаточно данных для создания учетной записи")
    public void errorCreatingCourierWithoutLoginTest() {
        // Создать запрос на регистрацию без login
        CourierModel courierModel = new CourierModel(null, PASSWORD, FIRSTNAME);
        // Отправить запрос
        createCourier(courierModel)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создать курьера без заполнения одного из обязательных полей (password)")
    @Description("При создании курьера без заполнения поля password, запрос возвращает ошибку 400 Bad Request, текст: Недостаточно данных для создания учетной записи")
    public void errorCreatingCourierWithoutPasswordTest() {
        // Создать запрос на регистрацию без password
        CourierModel courierModel = new CourierModel(LOGIN, null, FIRSTNAME);
        // Отправить запрос
        createCourier(courierModel)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        courierSteps.cleanUp(courierLogin);
    }
}
