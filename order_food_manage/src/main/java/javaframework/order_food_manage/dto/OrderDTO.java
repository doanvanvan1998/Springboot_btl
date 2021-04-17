package javaframework.order_food_manage.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO extends AbstractDTO<OrderDTO>{
    private String code;
    private Long total;
    private List<OrderDetailDTO> listOrderDetail = new ArrayList<>();
    private Integer orderStatus;
    private Long user_manager_id;
    private Long user_customer_id;
    private String user_manager_name;
    private String user_customer_name;

    public void setTotal(){
        Long sum = 0l;
        for (OrderDetailDTO item : listOrderDetail) {
            sum += item.getSubTotal();
        }
        this.total = sum;
    }

    public int getListOrderDetailCount(){
        return listOrderDetail.size();
    }

    public void addOrderDetailDto(OrderDetailDTO orderDetailDTO){
        addOrderDetailDto(orderDetailDTO.getFoodDTO(), orderDetailDTO.getQuantity());
    }

    // add OrderDetail into Order
    public void addOrderDetailDto(FoodDTO foodDTO, Integer quantity){
        OrderDetailDTO itemOrder = getOrderDetails(foodDTO);    //Take 1 item orderDetail in cart
        if( itemOrder != null){             // if item had in cart then set quantity
            itemOrder.setQuantity(itemOrder.getQuantity() + quantity);
        }else{              // if item not exists in cart then create OrderDetailDTO object with foodDTO and set quantity
            itemOrder = new OrderDetailDTO(foodDTO);
            itemOrder.setQuantity(quantity);
            listOrderDetail.add(itemOrder);
        }
        setTotal();
    }

    // Kiểm tra sản phẩm để có trong cart chưa nếu có thì lấy ra
    public OrderDetailDTO getOrderDetails(FoodDTO foodDTO){
        for (OrderDetailDTO itemOrder : listOrderDetail) {
            if( itemOrder.getFoodDTO().getId() == foodDTO.getId() ){
                return itemOrder;
            }
        }
        return null;
    }

    public void updateQuantityInOrderDetail(FoodDTO foodDTO, Integer quantity){
        OrderDetailDTO itemOrder = getOrderDetails(foodDTO);
        if( itemOrder != null) {
            itemOrder.setQuantity(quantity);
        }
        setTotal();
    }

    public void removeOrderDetail(FoodDTO foodDTO){
        OrderDetailDTO itemOrder = getOrderDetails(foodDTO);
        if( itemOrder != null) {
            listOrderDetail.remove(itemOrder);
        }
        setTotal();
    }

    public void clear(){
        listOrderDetail.clear();
        total = 0l;
    }

    public boolean isEmpty(){
        return listOrderDetail.isEmpty();
    }

}
