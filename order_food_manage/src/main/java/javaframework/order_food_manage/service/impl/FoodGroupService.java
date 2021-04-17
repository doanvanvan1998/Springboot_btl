package javaframework.order_food_manage.service.impl;

import javaframework.order_food_manage.converter.FoodGroupConverter;
import javaframework.order_food_manage.dto.FoodGroupDTO;
import javaframework.order_food_manage.entities.FoodGroupEntity;
import javaframework.order_food_manage.repository.FoodGroupRepos;
import javaframework.order_food_manage.service.IFoodGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodGroupService implements IFoodGroupService {
    @Autowired
    private FoodGroupRepos foodGroupRepos;

    @Autowired
    private FoodGroupConverter foodGroupConverter;

    @Autowired
    private FoodService foodService;

    @Override
    public List<FoodGroupDTO> findAll() {
        List<FoodGroupDTO> foodGroupDTOS = new ArrayList<>();
        List<FoodGroupEntity> categoryEntities = (List<FoodGroupEntity>) foodGroupRepos.findAll();
        categoryEntities.forEach(categoryEntity -> {
            foodGroupDTOS.add(foodGroupConverter.toDto(categoryEntity));
        });
        return foodGroupDTOS;
    }

    @Override
    public FoodGroupDTO getFoodGroupPagination(int page, int limit, String keyword) {
        FoodGroupDTO foodGroupDTO = new FoodGroupDTO();
        List<FoodGroupEntity> foodGroupEntities = new ArrayList<>();
        List<FoodGroupDTO> foodGroupDTOS = new ArrayList<>();
        if (limit == 1) {
            if( !keyword.equals("") ){
                foodGroupEntities = (List<FoodGroupEntity>) foodGroupRepos.findAllSearch(keyword);
            }else{
                foodGroupEntities = (List<FoodGroupEntity>) foodGroupRepos.findAll();
            }
        } else {
            if( !keyword.equals("") ){
                foodGroupEntities = foodGroupRepos.findAllSearch(keyword, PageRequest.of(page-1, limit));
            }else {
                foodGroupEntities = foodGroupRepos.findAll(PageRequest.of(page - 1, limit)).getContent();
            }
            foodGroupDTO.setTotalPage((int) Math.ceil((double) totalItem() / limit));
        }
        foodGroupEntities.forEach(result -> {
            foodGroupDTOS.add(foodGroupConverter.toDto(result));
        });
        foodGroupDTO.setResultList(foodGroupDTOS);
        foodGroupDTO.setPage(page);
        foodGroupDTO.setLimit(limit);
        foodGroupDTO.setSearch(keyword);
        return foodGroupDTO;
    }

    @Override
    public int totalItem() {
        return (int) foodGroupRepos.count();
    }

    @Override
    public FoodGroupDTO findOne(Long id) {
        return foodGroupConverter.toDto(foodGroupRepos.findById(id).get());
    }

    @Override
    public FoodGroupDTO save(FoodGroupDTO foodGroupDTO) {
        FoodGroupEntity foodGroupEntity = new FoodGroupEntity();
        FoodGroupDTO foodGroupResult = new FoodGroupDTO();
        boolean checkInsert = false;
        if (foodGroupDTO.getId() != null) {
            FoodGroupEntity entity_old = foodGroupRepos.findById(foodGroupDTO.getId()).get();
            foodGroupEntity = foodGroupConverter.toEntity( entity_old, foodGroupDTO);
        } else {
            foodGroupEntity = foodGroupConverter.toEntity(foodGroupDTO);
            checkInsert = true;
        }
        foodGroupResult = foodGroupConverter.toDto((FoodGroupEntity) foodGroupRepos.save(foodGroupEntity));
        if( foodGroupResult != null ){
            foodGroupResult.setAlert("success");
            if( checkInsert ) {
                foodGroupResult.setMessage("Insert success");
            }else{
                foodGroupResult.setMessage("Update success");
            }
        }else{
            foodGroupResult.setAlert("danger");
            foodGroupResult.setMessage("No success");
        }
        return foodGroupResult;
    }

    @Override
    public List<FoodGroupDTO> delete(Long[] id) {
        List<Long> listFoodGroupId = foodService.getFoodGroupIdUnduplicated(); //Lấy tất cả CategoryId của product
        List<FoodGroupDTO> foodGroupDTOS = new ArrayList<>();
        for (Long item : id) {
            if( listFoodGroupId.contains(item) ) {
                foodGroupDTOS.add(foodGroupConverter.toDto(foodGroupRepos.findById(item).get()));
                foodGroupRepos.deleteById(item);
            }
        }
        return foodGroupDTOS;
    }
}
