package hucode.news_app.service;

import hucode.news_app.dto.CommiitDTO;
import hucode.news_app.dto.CommiitResDTO;
import hucode.news_app.entity.Article;
import hucode.news_app.entity.Commiit;
import hucode.news_app.entity.User;
import hucode.news_app.exception.ResourceNotFoundException;
import hucode.news_app.repository.ArticleRepository;
import hucode.news_app.repository.CommiitRepository;
import hucode.news_app.repository.UserRepository;
import hucode.news_app.security.JwtFilter;
import hucode.news_app.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 2:42 PM on 7/26/2022
 * @project news_app
 */
@Service
@RequiredArgsConstructor
public class CommiitService {
    private final ArticleRepository articleRepository;
    private final JwtFilter jwtFilter;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final CommiitRepository commiitRepository;

    public CommiitResDTO updateById(Long id, String text) {
        Commiit commiit = commiitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commiit", "id", id));
        commiit.setText(text);
        Commiit save = commiitRepository.save(commiit);
      return  CommiitResDTO.builder().text(save.getText()).article(save.getArticle().getName()).user(save.getUser().getId()).build();
    }
    public CommiitResDTO getOne(Long id) {
        Commiit commiit = commiitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commiit", "id", id));
        return CommiitResDTO.builder().text(commiit.getText()).user(commiit.getUser().getId()).article(commiit.getArticle().getName()).build();
    }
    public CommiitResDTO generateCommit(CommiitDTO commiitDTO) {
        Article article = articleRepository.findById(commiitDTO.getArticle()).orElseThrow(
                () -> new ResourceNotFoundException("Article", "id", commiitDTO.getArticle()));
        String email = jwtProvider.getEmailFromToken(jwtFilter.sessionToken);
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        Commiit build = Commiit.builder()
                .text(commiitDTO.getText())
                .article(article)
                .user(user).build();
        Commiit save = commiitRepository.save(build);
        return CommiitResDTO.builder().article(save.getArticle().getName()).user(user.getId()).text(commiitDTO.getText()).build();
    }
    public String deleteById(Long id) {
        Commiit commiit = commiitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commiit", "id", id));
        String email = jwtProvider.getEmailFromToken(jwtFilter.sessionToken);
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        if (commiit.getUser().getEmail().equals(user.getEmail())){
            commiitRepository.deleteById(id);
            return "Succesfully deleted";
        }
        return "Server error";
    }
}
