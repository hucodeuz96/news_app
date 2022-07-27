package hucode.news_app.controller;

import hucode.news_app.dto.LoginDTO;
import hucode.news_app.dto.UserDTO;
import hucode.news_app.entity.User;
import hucode.news_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author "Husniddin Ulachov"
 * @created 5:24 AM on 7/23/2022
 * @project news_app
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<User> registration(@Valid @RequestBody UserDTO user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO){
        String token = userService.loginService(loginDTO);
        return ResponseEntity.status(token != ("Password didn't match" +loginDTO.getPassword())?200:400).body(token);
    }
}
