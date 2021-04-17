package javaframework.order_food_manage.service;


import javaframework.order_food_manage.dto.FoodGroupDTO;

import java.util.List;

public interface IFoodGroupService {
    List<FoodGroupDTO> findAll();

    FoodGroupDTO save(FoodGroupDTO foodGroupDTO);

    List<FoodGroupDTO> delete(Long[] id);

    FoodGroupDTO getFoodGroupPagination(int page, int limit, String search);

    int totalItem();

    FoodGroupDTO findOne(Long id);
}
