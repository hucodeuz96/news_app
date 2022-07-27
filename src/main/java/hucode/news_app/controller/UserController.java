package hucode.news_app.controller;

import hucode.news_app.dto.UserDTO;
import hucode.news_app.entity.User;
import hucode.news_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author "Husniddin Ulachov"
 * @created 9:40 AM on 7/18/2022
 * @project news_app
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id){
      return ResponseEntity.ok().body(userService.getOneById(id));
    }
    @GetMapping("/list")
    public ResponseEntity<Page<User>> getAll(
            @RequestParam (defaultValue = "0")  int page,
            @RequestParam (defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(userService.readAll(page,size));
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<User> edit(@PathVariable Long id, @RequestBody UserDTO userDTO){
        User user = userService.editById(userDTO, id);
        return ResponseEntity.status(user != null ?200:409).body(user);
    }
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.deleteById(id));
    }
}
