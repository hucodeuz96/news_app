package hucode.news_app.service;



import hucode.news_app.dto.ApiResponse;
import hucode.news_app.dto.LoginDTO;
import hucode.news_app.exception.ResourceNotFoundException;
import hucode.news_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author "Husniddin Ulachov"
 * @created 12:12 AM on 7/13/2022
 * @project Edu-Center
 */
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("login ","email ",email));
    }
    public UserDetails loadUser(String email,String password){
        return userRepository.findByEmailAndPassword(email,password).orElseThrow(() -> new ResourceNotFoundException("login","password",password));
    }

}
