package javaframework.order_food_manage.service;


import javaframework.order_food_manage.dto.FoodDTO;

import java.util.List;

public interface IFoodService {
    List<FoodDTO> findByCategoryCode(String categoryCode);
    List<FoodDTO> findByFoodGroupId(Long foodGroupId);
    List<FoodDTO> findAllHavePricePromotion();
    List<FoodDTO> findAll();
    FoodDTO findOne(Long id);
    FoodDTO save(FoodDTO foodDTO);
    List<FoodDTO> delete(Long[] id);
    FoodDTO getProductPagination(int page, int limit, String keyword);
    int totalItem();
    int countSearch(String keySearch);
    List<Long> getListCategoryIdUnduplicated();
    List<Long> getFoodGroupIdUnduplicated();

}
