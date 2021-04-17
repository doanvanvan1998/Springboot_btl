package javaframework.order_food_manage.dto;

import lombok.Data;

@Data
public class OrderDetailDTO extends AbstractDTO<OrderDetailDTO>{
    private final FoodDTO foodDTO;
    private Long price;
    private Integer quantity;
    private Long subTotal;
    private Long order_id;

    public OrderDetailDTO(FoodDTO foodDTO){
        this.foodDTO = foodDTO;
        this.quantity = 1;
        this.subTotal = foodDTO.getPrice();
        if( foodDTO.getPricePromotion() != null ){
            this.price = foodDTO.getPricePromotion();
        }
        else this.price = foodDTO.getPrice();
    }

    public Long getSubTotal() {
        subTotal = foodDTO.getPrice()*quantity;
        return subTotal;
    }
}
