package hucode.news_app.service;

import hucode.news_app.dto.LoginDTO;
import hucode.news_app.dto.UserDTO;
import hucode.news_app.entity.Article;
import hucode.news_app.entity.RoleUser;
import hucode.news_app.entity.User;
import hucode.news_app.exception.ResourceNotFoundException;
import hucode.news_app.repository.ArticleRepository;
import hucode.news_app.repository.AttachmentRepository;
import hucode.news_app.repository.UserRepository;
import hucode.news_app.security.JwtProvider;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 9:42 AM on 7/18/2022
 * @project news_app
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private  final AttachmentRepository attachmentRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public User createUser(UserDTO userDto) {
        User user = toTDO(userDto);
        return userRepository.save(user);
    }

    public String loginService(LoginDTO loginDTO) {
        String password = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Authentification","eamil",loginDTO.getEmail())).getPassword();
        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), password);
        if (matches) {
           return jwtProvider.generateToken(loginDTO.getEmail());
        }
        return "Password didn't match : "+loginDTO.getPassword();
    }
    public Page<User> readAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest);
    }

    public User editById(UserDTO userDTO,Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }
    private User toTDO(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        user.setRole(RoleUser.valueOf(userDTO.getRole()));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }

    public String deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        List<Article> articleList = articleRepository.findByUserFullName(user.getFullName());
        for (Article article : articleList) {
            if (article.getAttachment()!=null){
                try {
                    Files.deleteIfExists(Path.of(ArticleService.root +"\\"+ article.getAttachment().getFileName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            articleRepository.deleteById(article.getId());
        }
        userRepository.deleteById(id);
        return "Successfully deleted";
    }

    public User getOneById(Long id) {
       return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
    }
}
