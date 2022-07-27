package hucode.news_app.repository;

import hucode.news_app.entity.Article;
import hucode.news_app.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author "Husniddin Ulachov"
 * @created 10:03 AM on 7/18/2022
 * @project news_app
 */

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
    Optional<Attachment> findByFileName(String fileName);

}
