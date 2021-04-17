package javaframework.order_food_manage.service;

import javaframework.order_food_manage.dto.OrderDTO;

import javax.servlet.http.HttpSession;

public interface IOrderService {
    OrderDTO getOrderDto(HttpSession session);
    void setOrderDto(HttpSession session, OrderDTO orderDTO);
    void removeOrderDTO(HttpSession session);
}
