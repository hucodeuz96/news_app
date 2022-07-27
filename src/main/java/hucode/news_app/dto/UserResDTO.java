package hucode.news_app.dto;

import hucode.news_app.entity.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author "Husniddin Ulachov"
 * @created 10:38 AM on 7/18/2022
 * @project news_app
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResDTO {
    @NotNull(message = "fullName mustn't be empty")
    private String fullName;
    @NotNull(message = "email mustn't be empty")
    private String email;
    @NotNull(message = "password mustn't be empty")
    private String password;
    private String role;
    private int articleCount;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true ;
    private boolean credentialNonExpired = true;
    private boolean enabled =true ;

}
