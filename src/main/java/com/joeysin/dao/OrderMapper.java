package com.joeysin.dao;

import com.joeysin.domain.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO t_order (order_id,user_id) VALUES (#{orderId},#{userId})")
    public void insert(Order order);

    @Results(value = {@Result(property = "userId", column = "user_id"), @Result(property = "orderId", column = "order_id")})
    @Select("SELECT * FROM t_order WHERE user_id=#{id}")
    public List<Order> findByUserId(Integer id);

    @Results(value = {@Result(property = "userId", column = "user_id"), @Result(property = "orderId", column = "order_id")})
    @Select("SELECT * FROM t_order WHERE order_id=#{id}")
    public List<Order> findByOrderId(Integer id);

}
