package javaframework.order_food_manage.service.impl;

import javaframework.order_food_manage.converter.FoodConverter;
import javaframework.order_food_manage.dto.FoodDTO;
import javaframework.order_food_manage.entities.FoodEntity;
import javaframework.order_food_manage.entities.ImageEntity;
import javaframework.order_food_manage.repository.FoodRepos;
import javaframework.order_food_manage.repository.ImageRepos;
import javaframework.order_food_manage.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private FoodRepos foodRepos;

    @Autowired
    private FoodConverter foodConverter;

    @Autowired
    private ImageRepos imageRepos;

    @Override
    public FoodDTO findOne(Long id) {
        return foodConverter.toDto(foodRepos.findById(id).get());
    }

    @Override
    public List<FoodDTO> findByCategoryCode(String categoryCode) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        List<FoodEntity> foodEntities = foodRepos.findByCategoryCode(categoryCode);
        foodEntities.forEach(f->{
            foodDTOS.add(foodConverter.toDto(f));
        });
        return foodDTOS;
    }

    @Override
    public List<FoodDTO> findByFoodGroupId(Long foodGroupId) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        List<FoodEntity> foodEntities = foodRepos.findByFoodGroupId(foodGroupId);
        foodEntities.forEach(f->{
            foodDTOS.add(foodConverter.toDto(f));
        });
        return foodDTOS;
    }

    @Override
    public List<FoodDTO> findAllHavePricePromotion() {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        List<FoodEntity> foodEntities = foodRepos.findAllHavePricePromotion();
        foodEntities.forEach(f->{
            foodDTOS.add(foodConverter.toDto(f));
        });
        return foodDTOS;
    }

    @Override
    public List<FoodDTO> findAll() {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        List<FoodEntity> foodEntities = (List<FoodEntity>) foodRepos.findAll();
        foodEntities.forEach(f->{
            foodDTOS.add(foodConverter.toDto(f));
        });
        return foodDTOS;
    }

    @Override
    public FoodDTO save(FoodDTO foodDTO) {
        FoodEntity foodEntity = new FoodEntity();
        FoodDTO result = new FoodDTO();
        boolean checkInsert = false;
        if (foodDTO.getId() != null) {
            FoodEntity entity_old = foodRepos.findById(foodDTO.getId()).get();
            foodEntity = foodConverter.toEntity(entity_old, foodDTO);
        } else {
            foodEntity = foodConverter.toEntity(foodDTO);
            checkInsert = true;
        }
        try {
            result = foodConverter.toDto(foodRepos.save(foodEntity));
        }catch ( Exception e){
            e.printStackTrace();
        }finally {
            if (result.getId() != null) {
                result.setAlert("success");
                if (checkInsert) {
                    result.setMessage("Insert success");
                } else {
                    result.setMessage("Update success");
                }
            } else {
                result.setAlert("danger");
                result.setMessage("No success");
            }
        }
        return result;
    }

//    @Override
//    public void delete(Long[] ids) {
//        for (Long item : ids) {
//            FoodEntity entity = foodRepos.findById(item).get();
//            entity.setStatus(false);
//            entity = foodRepos.save(entity);
//        }
//    }
    @Override
    public List<FoodDTO> delete(Long[] ids) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        for (Long item : ids) {
            List<ImageEntity> imageEntities =  imageRepos.findByFoodId(item);
            if( imageEntities.size() > 0 ){
                foodDTOS.add(foodConverter.toDto(foodRepos.findById(item).get()));
            }else{
                foodRepos.deleteById(item);
            }
        }
        return foodDTOS;
    }

    @Override
    public FoodDTO getProductPagination(int page, int limit, String keyword) {
        FoodDTO foodDTO = new FoodDTO();
        List<FoodEntity> foodEntities = new ArrayList<>();
        int total = 0;
        if (limit == 1) {
            if( !keyword.equals("") ){
                foodEntities = (List<FoodEntity>) foodRepos.findAllSearch(keyword);
            }else {
                foodEntities = (List<FoodEntity>) foodRepos.findAll();
            }
        } else {
            if( !keyword.equals("")){
                foodEntities = foodRepos.findAllSearch(keyword, PageRequest.of(page-1, limit));
                total = (int) Math.ceil((double) countSearch(keyword) / limit);
            }else {
                foodEntities = foodRepos.findAll(PageRequest.of(page - 1, limit)).getContent();
                total = (int) Math.ceil((double) totalItem() / limit);
            }
        }
        foodEntities.forEach(result -> {
            foodDTO.getResultList().add(foodConverter.toDto(result));
        });
        foodDTO.setTotalPage(total);
        foodDTO.setPage(page);
        foodDTO.setLimit(limit);
        foodDTO.setSearch(keyword);
        return foodDTO;
    }

    @Override
    public int totalItem() {
        return (int) foodRepos.count();
    }

    @Override
    public int countSearch(String keySearch) {
        return foodRepos.countSearch(keySearch);
    }

    @Override
    public List<Long> getListCategoryIdUnduplicated() {
        List<Long> listCategoryId = foodRepos.getListCategoryIdUnduplicated();
        return listCategoryId;
    }

    @Override
    public List<Long> getFoodGroupIdUnduplicated() {
        List<Long> listFoodGroupId = foodRepos.getListFoodGroupIdUnduplicated();
        return listFoodGroupId;
    }
}
