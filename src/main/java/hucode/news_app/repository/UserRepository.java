package hucode.news_app.repository;

import hucode.news_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author "Husniddin Ulachov"
 * @created 10:03 AM on 7/18/2022
 * @project news_app
 */

public interface UserRepository extends JpaRepository<User,Long> {
        Optional<UserDetails> findByEmail(String email);
        Optional<User> findByEmailAndPassword(String email,String password);
}
