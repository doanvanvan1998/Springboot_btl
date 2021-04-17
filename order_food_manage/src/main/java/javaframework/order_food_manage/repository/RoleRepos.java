package javaframework.order_food_manage.repository;

import javaframework.order_food_manage.entities.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepos extends PagingAndSortingRepository<RoleEntity, Long> {
}

