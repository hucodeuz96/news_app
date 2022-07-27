package hucode.news_app.service;

import hucode.news_app.dto.ArticleDTO;
import hucode.news_app.dto.ArticleResDTO;
import hucode.news_app.dto.ArticleResWithCommit;
import hucode.news_app.entity.*;
import hucode.news_app.exception.ResourceNotFoundException;
import hucode.news_app.repository.*;
import hucode.news_app.security.JwtFilter;
import hucode.news_app.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author "Husniddin Ulachov"
 * @created 11:05 AM on 7/19/2022
 * @project news_app
 */
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    public static final Path root = Paths.get("C:\\MyFile\\6-modul\\news-app-file");
    private final AttachmentRepository attachmentRepository;
    private final CategoriesRepository categoriesRepository;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private  final CommiitRepository commiitRepository;

    public ArticleResDTO addArticle(ArticleDTO articleDTO) {
        Attachment saveAttachment = null;
        try {
        if (articleDTO.getMultipartFile()!=null){
            saveAttachment = attachmentRepository.save(Attachment.builder()
                    .contentType(articleDTO.getMultipartFile().getContentType())
                    .fileName(articleDTO.getMultipartFile().getOriginalFilename())
                    .size(articleDTO.getMultipartFile().getSize())
                    .build());
            MultipartFile file = articleDTO.getMultipartFile();
            Files.deleteIfExists(Path.of(root +"\\"+ articleDTO.getMultipartFile().getOriginalFilename()));
            Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        Categories categories = categoriesRepository.findById(articleDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", articleDTO.getCategoryId()));
        String email = jwtProvider.getEmailFromToken(jwtFilter.sessionToken);
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        Article article = new Article();
        article.setName(articleDTO.getName());
        article.setDescription(articleDTO.getDescription());
        article.setCategory(categories);
        article.setUser(user);
        article.setAttachment(saveAttachment);
        Article save = articleRepository.save(article);
        ArticleResDTO map = modelMapper.map(save, ArticleResDTO.class);
        map.setFile(articleDTO.getMultipartFile().getOriginalFilename());
        map.setUser(user.getFullName());
        map.setCategory(categories.getName());
        return map;
    }
    public List<ArticleResDTO> readAll(int page, int size, String articleName, String category) {
        Pageable pagable = PageRequest.of(page, size);
        Page<Article> articleList = null;
        if (articleName.equals("") && category.equals("")) {
            articleList = articleRepository.findAll(pagable);
        } else if (articleName.equals("")) {
            articleList = articleRepository.findByCategoryNameContainingIgnoreCase(category, pagable);
        } else if (category.equals("")) {
            articleList = articleRepository.findByNameContainingIgnoreCase(articleName, pagable);
        }
        List<ArticleResDTO> articleResDTOS = new ArrayList<>();
        assert articleList != null;
        for (Article article : articleList) {
            ArticleResDTO map = modelMapper.map(article, ArticleResDTO.class);
            map.setUser(article.getUser().getFullName());
            map.setCategory(article.getCategory().getName());
            map.setFile(article.getAttachment().getFileName());
            articleResDTOS.add(map);
        }
        return articleResDTOS;
    }
    public ArticleResDTO editById(ArticleDTO articleDTO, Long id) {
        Attachment attachment = new Attachment();
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        Categories category = categoriesRepository.findById(articleDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", articleDTO.getCategoryId()));
        article.setName(articleDTO.getName());
        article.setCategory(category);
        article.setDescription(article.getDescription());
        try {
            if (articleDTO.getMultipartFile() != null) {
                Files.deleteIfExists(Path.of(root + "\\" + articleDTO.getMultipartFile().getOriginalFilename()));
                Files.copy(articleDTO.getMultipartFile().getInputStream(), root.resolve(Objects.requireNonNull(articleDTO.getMultipartFile().getOriginalFilename())));
                Attachment attachment1 = attachmentRepository.findById(article.getAttachment().getId()).orElseThrow(
                        () -> new ResourceNotFoundException("Attachment", "id", article.getAttachment().getId()));
                attachment1.setContentType(articleDTO.getMultipartFile().getContentType());
                attachment1.setFileName(articleDTO.getMultipartFile().getOriginalFilename());
                attachment1.setSize(articleDTO.getMultipartFile().getSize());
                Attachment save = attachmentRepository.save(attachment1);
                article.setAttachment(save);
            } else {
                Files.copy(articleDTO.getMultipartFile().getInputStream(), root.resolve(Objects.requireNonNull(articleDTO.getMultipartFile().getOriginalFilename())));
                attachment.setContentType(articleDTO.getMultipartFile().getContentType());
                attachment.setFileName(articleDTO.getMultipartFile().getOriginalFilename());
                attachment.setSize(articleDTO.getMultipartFile().getSize());
                Attachment save = attachmentRepository.save(attachment);
                article.setAttachment(save);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Article article1 = articleRepository.save(article);
        ArticleResDTO map = modelMapper.map(article1, ArticleResDTO.class);
        map.setFile(article.getAttachment().getFileName());
        map.setCategory(category.getName());
        String email = jwtProvider.getEmailFromToken(jwtFilter.sessionToken);
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        map.setUser(user.getFullName());
        map.setCategory(article.getCategory().getName());
        return map;
    }

    public String deleteById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("article", "id", id));
        String email = jwtProvider.getEmailFromToken(jwtFilter.sessionToken);
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        if (article.getAttachment()!=null && article.getUser().getEmail().equals(user.getEmail())) {
            try {
                Files.deleteIfExists(Path.of(root +"\\"+ article.getAttachment().getFileName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            articleRepository.deleteById(id);
            return "Successfully deleted";
        }
        return "System error occured";
    }

    public ArticleResWithCommit loadWithCommiit(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        ArticleResWithCommit map = modelMapper.map(article, ArticleResWithCommit.class);
        map.setCategory(article.getCategory().getName());
        map.setUser(article.getUser().getFullName());
        map.setFile(article.getAttachment().getFileName());
        Map<String,String> commitMap = new HashMap<>();
        List<Commiit> commiits = commiitRepository.findByArticle_Id(id);
        for (Commiit commiit : commiits) {
            commitMap.put(commiit.getUser().getFullName(),commiit.getText());
        }
        map.setCommit(commitMap);
        System.out.println(map);
        return map;
    }
}
