import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import steps.OrderSteps;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest extends BaseApiTest{
    @Test   //
    @DisplayName("Проверь, что в тело ответа возвращается список заказов")
    public void getOrdersListTest() {
        OrderSteps.getListOrders()
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .assertThat().body("orders", notNullValue());
    }

}
