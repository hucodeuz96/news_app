package hucode.news_app.dto;

import lombok.*;

/**
 * @author "Husniddin Ulachov"
 * @created 2:55 PM on 7/26/2022
 * @project news_app
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommiitDTO {
    private String text;
    private Long article;
}
