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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static steps.CourierSteps.authorizeCourier;
import static steps.CourierSteps.createCourier;

public class CourierLoginTest extends BaseApiTest {
    CourierModel courierModel;
    CourierLogin courierLogin;
    CourierSteps courierSteps;

    @Before
    public void testCreateCourier() {
        courierModel = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        courierLogin = new CourierLogin(LOGIN, PASSWORD);
        courierSteps = new CourierSteps();

        // Создать курьера
        createCourier(courierModel);
    }

    @Test  // Курьер может авторизоваться; успешный запрос возвращает id.
    @DisplayName("Проверить что курьер может авторизироваться с валидными логином и паролем")
    @Description("При успешной авторизации запрос возвращает id ")
    public void courierAuthorizationPositiveTest() {

        // Для авторизации передаем валидные логин и пароль
        authorizeCourier(courierLogin)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .assertThat().body("id", instanceOf(Integer.class));
    }

    @Test //для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку;
    @DisplayName("Проверить что курьер НЕ может авторизироваться если не заполнено поле login")
    @Description("При попытке авторизации с пустым полем login запрос возвращает ошибку 400 Bad Request, текст: Недостаточно данных для входа")
    public void errorCourierAuthorizationNoLoginTest() {
        // Заполнить форму авторизации без login
        CourierLogin courierLogin = new CourierLogin(null, PASSWORD);
        // Передать данные без login
        authorizeCourier(courierLogin)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .assertThat().body("massage", equalTo("Недостаточно данных для входа"));
    }

    @Test //для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку;
    @DisplayName("Проверить что курьер НЕ может авторизироваться если не заполнено поле password")
    @Description("При попытке авторизации с пустым полем password запрос возвращает ошибку 400 Bad Request, текст: Недостаточно данных для входа")
    public void errorCourierAuthorizationNoPasswordTest() {
        // Заполнить форму авторизации без password
        CourierLogin courierLogin = new CourierLogin(LOGIN, null);
        // Передать данные без password
        authorizeCourier(courierLogin)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .assertThat().body("massage", equalTo("Недостаточно данных для входа"));
    }

    @Test //система вернёт ошибку, если неправильно указать логин или пароль; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @DisplayName("Проверить что курьер НЕ может авторизироваться если неправильно указать login")
    @Description("При попытке авторизации с неправильно указанным login запрос возвращает ошибку 404 Not Found, текст: Учетная запись не найдена")
    public void errorCourierAuthorizationIncorrectLoginTest() {
        // Заполнить форму авторизации в поле login указать неправильные данные
        CourierLogin courierLogin = new CourierLogin(LOGIN + 12, PASSWORD);
        // Передать данные
        authorizeCourier(courierLogin)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test //система вернёт ошибку, если неправильно указать логин или пароль; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @DisplayName("Проверить что курьер НЕ может авторизироваться если неправильно указать password")
    @Description("При попытке авторизации с неправильно указанным password запрос возвращает ошибку 404 Not Found, текст: Учетная запись не найдена")
    public void errorCourierAuthorizationIncorrectPasswordTest() {
        // Заполнить форму авторизации в поле password указать неправильные данные
        CourierLogin courierLogin = new CourierLogin(LOGIN, PASSWORD + 1);
        // Передать данные
        authorizeCourier(courierLogin)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        courierSteps.cleanUp(courierLogin);
    }
}
