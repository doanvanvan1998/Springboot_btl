package javaframework.order_food_manage.controller.client;

import javaframework.order_food_manage.dto.FoodDTO;
import javaframework.order_food_manage.dto.OrderDTO;
import javaframework.order_food_manage.service.impl.FoodService;
import javaframework.order_food_manage.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public OrderDTO addToCart(HttpSession session, @RequestParam("id") Long id,
                                    @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity){
        FoodDTO foodDTO = foodService.findOne(id);
        // Create OrderDTO and add into session
        OrderDTO orderDTO = orderService.getOrderDto(session);
        // Order add OrderDetail with FoodDTO and quantity
        orderDTO.addOrderDetailDto(foodDTO, quantity);
        orderService.setOrderDto(session,orderDTO);
        return orderDTO;
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public OrderDTO removeOrderDetailInOrder(HttpSession session, @RequestParam("id") Long id){
        FoodDTO foodDTO = foodService.findOne(id);
        // Create OrderDTO and add into session
        OrderDTO orderDTO = orderService.getOrderDto(session);
        // Order remove OrderDetail with FoodDTO
        orderDTO.removeOrderDetail(foodDTO);
        orderService.setOrderDto(session,orderDTO);
        return orderDTO;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public OrderDTO updateQuantityOrder(HttpSession session, @RequestParam("id") Long id,
                             @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity){
        FoodDTO foodDTO = foodService.findOne(id);
        // Create OrderDTO and add into session
        OrderDTO orderDTO = orderService.getOrderDto(session);
        // Update OrderDetail in Order with FoodDTO and quantity update
        orderDTO.updateQuantityInOrderDetail(foodDTO,quantity);
        return orderDTO;
    }
}
