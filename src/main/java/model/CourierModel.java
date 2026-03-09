package model;

import lombok.*;

 //Создаем курьера
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierModel {
    private String login;
    private String password;
    private String firstName;
}

