package model;

import lombok.*;
import java.util.List;

// Создаем заказ
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderModel {
    public String firstName = "Фаина";
    public String lastName = "Раневская";
    public String address = "ул. Весенняя";
    public String metroStation = "Театральная";
    public String phone = "+71234567891";
    public Integer rentTime = 4;
    public String deliveryDate = "2026-03-07";
    public String comment = "Нет дома, оставьте соседям";
    private List<String> color;

    public OrderModel(List<String> color) {
        this.color = color;
    }
}
