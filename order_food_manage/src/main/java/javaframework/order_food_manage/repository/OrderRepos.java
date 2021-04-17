package javaframework.order_food_manage.repository;

import javaframework.order_food_manage.entities.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepos extends PagingAndSortingRepository<OrderEntity, Long> {
}
