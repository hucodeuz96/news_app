package hucode.news_app.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 6:29 AM on 7/18/2022
 * @project news_app
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article  extends AbsNameEntity {
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private Categories category;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Attachment attachment;


}
