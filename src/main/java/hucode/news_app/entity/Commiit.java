package hucode.news_app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 8:41 AM on 7/26/2022
 * @project news_app
 */
@Entity(name = "commit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Commiit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne
    private User user;



}
