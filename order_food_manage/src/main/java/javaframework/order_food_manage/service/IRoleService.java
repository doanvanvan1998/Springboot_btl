package javaframework.order_food_manage.service;


import javaframework.order_food_manage.dto.RoleDTO;

import java.util.List;

public interface IRoleService {
    RoleDTO findOne(Long id);
    List<RoleDTO> findAll();
}
