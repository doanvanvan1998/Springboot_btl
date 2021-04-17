package javaframework.order_food_manage.repository;

import javaframework.order_food_manage.entities.CategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepos extends PagingAndSortingRepository<CategoryEntity, Long> {
    @Query("select c from CategoryEntity c where concat(c.id, c.code, c.name) like %?1%")
    public List<CategoryEntity> findAllSearch(String keyword, Pageable pageable);

    @Query("select c from CategoryEntity c where concat(c.id, c.code, c.name) like %?1%")
    public List<CategoryEntity> findAllSearch(String keyword);
}
