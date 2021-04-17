package javaframework.order_food_manage.converter;

import javaframework.order_food_manage.dto.FoodDTO;
import javaframework.order_food_manage.entities.FoodEntity;
import javaframework.order_food_manage.repository.CategoryRepos;
import javaframework.order_food_manage.repository.FoodGroupRepos;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodConverter {

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private CategoryRepos categoryRepos;

    @Autowired
    private FoodGroupRepos foodGroupRepos;

    public FoodDTO toDto(FoodEntity entity) {
        FoodDTO dto = new FoodDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setPricePromotion(entity.getPrice_promotion());
        dto.setDescription(entity.getDescription());
        dto.setCategoryId(entity.getCategoryEntity().getId());
        dto.setCategoryName(entity.getCategoryEntity().getName());
        dto.setFoodGroupId(entity.getFoodGroupEntity().getId());
        dto.setFoodGroupName(entity.getFoodGroupEntity().getName());
        if( entity.getImageEntities() != null ) {
            entity.getImageEntities().stream().forEach(i -> {
                dto.getImageDTOS().add(imageConverter.toDto(i));
            });
        }
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public FoodEntity toEntity(FoodDTO dto) {
        FoodEntity entity = new FoodEntity();
        return getFoodEntity(entity,dto);
    }

    public FoodEntity toEntity(FoodEntity entity, FoodDTO dto) {
        return getFoodEntity(entity,dto);
    }

    @NotNull
    private FoodEntity getFoodEntity(FoodEntity entity, FoodDTO dto) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        if( dto.getPricePromotion() != null ) entity.setPrice_promotion(dto.getPricePromotion());
        else entity.setPrice_promotion(0l);
        entity.setDescription(dto.getDescription());
        entity.setCategoryEntity(categoryRepos.findById(dto.getCategoryId()).get());
        entity.setFoodGroupEntity(foodGroupRepos.findById(dto.getFoodGroupId()).get());
        entity.setStatus(true);
        return entity;
    }
}
