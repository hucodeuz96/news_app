package hucode.news_app.dto;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author "Husniddin Ulachov"
 * @created 9:36 AM on 7/27/2022
 * @project news_app
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ArticleResWithCommit extends ArticleResDTO{
    public ArticleResWithCommit(String name, String description, String category, String createdAt, String updatedAt, String user, String file, Map<String, String> commit) {
        super(name, description, category, createdAt, updatedAt, user, file);
        this.commit = commit;
    }

    private Map<String,String> commit;
}
