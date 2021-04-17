package javaframework.order_food_manage.repository;

import javaframework.order_food_manage.entities.FoodEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepos extends PagingAndSortingRepository<FoodEntity, Long> {
    @Query("Select f from FoodEntity f where f.categoryEntity.code = ?1")
    public List<FoodEntity> findByCategoryCode(String categoryCode);

    @Query("Select f from FoodEntity f where f.foodGroupEntity.id = ?1")
    public List<FoodEntity> findByFoodGroupId(Long foodGroupId);

    @Query("Select f from FoodEntity f where f.price_promotion > 0")
    public List<FoodEntity> findAllHavePricePromotion();

    @Query("select f from FoodEntity f where concat(f.name, f.description, f.price, f.price_promotion, f.categoryEntity.name, f.foodGroupEntity.name) like %?1%")
    public List<FoodEntity> findAllSearch(String keyword, Pageable pageable);

    @Query("select f from FoodEntity f where concat(f.name, f.description, f.price, f.price_promotion, f.categoryEntity.name, f.foodGroupEntity.name) like %?1%")
    public List<FoodEntity> findAllSearch(String keyword);

    @Query("Select Count(f) from FoodEntity f where concat(f.name, f.description, f.price, f.price_promotion, f.categoryEntity.name, f.foodGroupEntity.name) like %?1%")
    public int countSearch(String keyword);

    @Query("select distinct f.categoryEntity.id from FoodEntity f")
    public List<Long> getListCategoryIdUnduplicated();

    @Query("select distinct f.foodGroupEntity.id from FoodEntity f")
    public List<Long> getListFoodGroupIdUnduplicated();

}
