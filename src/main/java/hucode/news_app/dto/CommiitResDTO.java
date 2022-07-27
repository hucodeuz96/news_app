package hucode.news_app.dto;

import lombok.*;

import java.util.List;

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
public class CommiitResDTO {
    private String text;
    private String article;
    private Long user;
}
