package javaframework.order_food_manage.converter;

import javaframework.order_food_manage.dto.ImageDTO;
import javaframework.order_food_manage.entities.ImageEntity;
import javaframework.order_food_manage.repository.FoodRepos;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {

    @Autowired
    private FoodRepos foodRepos;

    public ImageDTO toDto(ImageEntity entity) {
        ImageDTO dto = new ImageDTO();
        dto.setId(entity.getId());
        dto.setPath(entity.getPath());
        dto.setIs_preview(entity.getIsPreview());
        dto.setFoodId(entity.getFoodEntity().getId());
        dto.setFoodName(entity.getFoodEntity().getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public ImageEntity toEntity(ImageDTO dto) {
        ImageEntity entity = new ImageEntity();
        return getImageEntity(entity,dto);
    }

    public ImageEntity toEntity(ImageEntity entity, ImageDTO dto) {
        return getImageEntity(entity,dto);
    }

    @NotNull
    private ImageEntity getImageEntity(ImageEntity entity, ImageDTO dto) {
        if( dto.getPath() != null ) entity.setPath(dto.getPath());
        entity.setIsPreview(dto.getIs_preview());
        entity.setFoodEntity(foodRepos.findById(dto.getFoodId()).get());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setModifiedBy(dto.getModifiedBy());
        entity.setStatus(true);
        return entity;
    }
}
