package hucode.news_app.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 6:16 AM on 7/18/2022
 * @project news_app
 */
@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleUser role;



    private boolean accountNonExpired = true ;
    private boolean accountNonLocked = true;
    private boolean credentialNonExpired = true;
    private boolean enabled = true;

    public User(String fullName, String email, String password, RoleUser role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleUser roleUser : RoleUser.values()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleUser.name()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.accountNonExpired;
    }
}
