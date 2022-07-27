package hucode.news_app.companent;

import hucode.news_app.entity.RoleUser;
import hucode.news_app.entity.User;
import hucode.news_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author "Husniddin Ulachov"
 * @created 9:58 AM on 7/18/2022
 * @project news_app
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){
            userRepository.save(new User("Husniddin","email@mail.com", passwordEncoder.encode("123"), RoleUser.ADMIN));
        }

    }
}
