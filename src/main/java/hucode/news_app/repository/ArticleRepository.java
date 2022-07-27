package hucode.news_app.repository;

import hucode.news_app.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 10:03 AM on 7/18/2022
 * @project news_app
 */

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findByCategoryNameContainingIgnoreCase(String name,Pageable pageable);
    Page<Article> findByNameContainingIgnoreCase(String name,Pageable pageable);
    List<Article> findByUserFullName(String fullName);

}
