package hucode.news_app.repository;

import hucode.news_app.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author "Husniddin Ulachov"
 * @created 10:03 AM on 7/18/2022
 * @project news_app
 */
@PreAuthorize(value = "hasAuthority('ADMIN')")
@RepositoryRestResource(path = "category")
public interface CategoriesRepository extends JpaRepository<Categories,Long> {

}
