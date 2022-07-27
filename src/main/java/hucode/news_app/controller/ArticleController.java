package hucode.news_app.controller;

import hucode.news_app.dto.ArticleDTO;
import hucode.news_app.dto.ArticleResDTO;
import hucode.news_app.dto.ArticleResWithCommit;
import hucode.news_app.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 11:03 AM on 7/19/2022
 * @project news_app
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<ArticleResDTO> addArticle(@Valid @ModelAttribute ArticleDTO articleDTO){
        ArticleResDTO articleResDTO = articleService.addArticle(articleDTO);
        return ResponseEntity.status(articleResDTO!=null ?200:400).body(articleResDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ArticleResDTO>>  getAll(@RequestParam (defaultValue = "0")  int page,
                                      @RequestParam (defaultValue = "10") int size,
                                      @RequestParam (defaultValue = "") String articleName,
                                      @RequestParam (defaultValue = "") String category
    ){
        return ResponseEntity.ok(articleService.readAll(page,size,articleName,category));
    }
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleResDTO>  edit(@PathVariable Long id, @ModelAttribute ArticleDTO articleDTO){
        ArticleResDTO articleResDTO = articleService.editById(articleDTO, id);
        return ResponseEntity.ok(articleResDTO);
    }
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String deleted = articleService.deleteById(id);
        return ResponseEntity.ok(deleted);
    }
    @GetMapping("getOne/{id}")
    public ResponseEntity <ArticleResWithCommit> getOneWithCommit(@PathVariable Long id){
        return ResponseEntity.ok(articleService.loadWithCommiit(id));
    }

}
