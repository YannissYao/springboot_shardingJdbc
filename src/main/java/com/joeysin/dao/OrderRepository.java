package com.joeysin.dao;

import com.joeysin.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderRepository {

    void insert(Order order);

    void truncateTable();


    List<Order> findByUserId(Integer id);


    List<Order> findByOrderId(Integer id);
}
