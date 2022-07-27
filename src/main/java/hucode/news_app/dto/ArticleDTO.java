package hucode.news_app.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author "Husniddin Ulachov"
 * @created 12:16 PM on 7/19/2022
 * @project news_app
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {
    private String name;
    private String description;
    private Long categoryId;
    private MultipartFile multipartFile = null;
}
