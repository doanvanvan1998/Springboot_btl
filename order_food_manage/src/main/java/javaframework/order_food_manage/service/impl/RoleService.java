package javaframework.order_food_manage.service.impl;

import javaframework.order_food_manage.converter.RoleConverter;
import javaframework.order_food_manage.dto.RoleDTO;
import javaframework.order_food_manage.entities.RoleEntity;
import javaframework.order_food_manage.repository.RoleRepos;
import javaframework.order_food_manage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepos roleRepos;

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public RoleDTO findOne(Long id) {
        return roleConverter.toDto(roleRepos.findById(id).get());
    }

    @Override
    public List<RoleDTO> findAll() {
        List<RoleDTO> roleDTOS = new ArrayList<>();
        List<RoleEntity> roleEntities = (List<RoleEntity>) roleRepos.findAll();
        roleEntities.forEach(roleEntity -> {
            roleDTOS.add(roleConverter.toDto(roleEntity));
        });
        return roleDTOS;
    }
}
