package javaframework.order_food_manage.converter;

import javaframework.order_food_manage.dto.FoodGroupDTO;
import javaframework.order_food_manage.entities.FoodGroupEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class FoodGroupConverter {
    public FoodGroupDTO toDto(FoodGroupEntity entity) {
        FoodGroupDTO dto = new FoodGroupDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public FoodGroupEntity toEntity(FoodGroupDTO dto) {
        FoodGroupEntity entity = new FoodGroupEntity();
        return getFoodGroupEntity(entity,dto);
    }

    public FoodGroupEntity toEntity(FoodGroupEntity entity, FoodGroupDTO dto) {
        return getFoodGroupEntity(entity,dto);
    }

    @NotNull
    private FoodGroupEntity getFoodGroupEntity(FoodGroupEntity entity, FoodGroupDTO dto) {
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setStatus(true);
        return entity;
    }
}
