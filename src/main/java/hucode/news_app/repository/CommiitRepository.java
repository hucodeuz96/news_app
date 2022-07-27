package hucode.news_app.repository;

import hucode.news_app.entity.Commiit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 10:03 AM on 7/18/2022
 * @project news_app
 */
public interface CommiitRepository extends JpaRepository<Commiit,Long> {
    List<Commiit> findByArticle_Id(Long id);

}
