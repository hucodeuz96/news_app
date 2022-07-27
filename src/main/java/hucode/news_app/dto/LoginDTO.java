package hucode.news_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author "Husniddin Ulachov"
 * @created 11:13 AM on 7/18/2022
 * @project news_app
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDTO {
    @NotNull(message = "email must not not empty")
    private String email;
    @NotNull(message = "password must not not empty")
    private String password;
}
