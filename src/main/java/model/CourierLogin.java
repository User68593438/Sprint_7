package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Созданный курьер
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierLogin {
    private String login;
    private String password;
}
