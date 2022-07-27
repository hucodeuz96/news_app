package hucode.news_app.dto;

import lombok.*;

/**
 * @author "Husniddin Ulachov"
 * @created 1:03 PM on 7/20/2022
 * @project news_app
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResDTO  {
    private String name;
    private String description;
    private String category;
    private String createdAt;
    private String updatedAt;
    private String user;
    private String file;

}
