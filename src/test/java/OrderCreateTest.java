import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.instanceOf;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseApiTest {
    private final List<String> color;

    public OrderCreateTest (List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "color: {0}")
    public static Object[][] getColor() {
        return new Object[][] {
                // можно указать один из цветов — BLACK или GREY
                {List.of("BLACK")},
                {List.of("GREY")},
                // можно указать оба цвета
                {List.of("BLACK", "GREY")},
                // можно совсем не указывать цвет
                {List.of()}
        };
    }

    @Test   //Создать заказа; проверить, что тело ответа содержит track
    @DisplayName("Создать заказа. Позитивный тест")
    @Description("Проверить что можно создать заказ, используя значения поля color из параметризации")
    public void createOrderTest() {
        // Создать объект для color
        OrderModel orderModel = new OrderModel(color);
        // Отправить запрос
        createOrder(orderModel)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .assertThat().body("track", instanceOf(Integer.class));
    }
}
