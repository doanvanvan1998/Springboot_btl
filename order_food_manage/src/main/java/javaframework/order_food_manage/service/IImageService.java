package javaframework.order_food_manage.service;


import javaframework.order_food_manage.dto.ImageDTO;

import java.util.List;

public interface IImageService {
    List<ImageDTO> findAll();

    ImageDTO save(ImageDTO imageDTO);

    void delete(Long[] id);

    ImageDTO getImagePagination(int page, int limit, String keyword);

    int totalItem();

    int countSearch(String keySearch);

    ImageDTO findOne(Long id);

    List<ImageDTO> findByFoodId(Long id);
}
