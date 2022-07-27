package hucode.news_app.entity;

import lombok.*;

import javax.persistence.Entity;


/**
 * @author "Husniddin Ulachov"
 * @created 6:43 AM on 7/18/2022
 * @project news_app
 */

@NoArgsConstructor
@Entity(name = "category")
@Builder
@Getter
@Setter
public class Categories extends AbsNameEntity {
}
