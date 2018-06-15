package com.joeysin;

import com.joeysin.dao.OrderMapper;
import com.joeysin.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private OrderMapper orderMapper;


    /**
     * Created by Joeysin on  2018/6/15  下午5:31.
     * Describe：分库分表测试
     * 规则：userId % 2 控制分库；orderId % 2 控制分表
     */
    @Test
    public void t1() {
        Order order = new Order();
        order.setUserId(2);
        order.setOrderId(2);
        orderMapper.insert(order);
    }


    /**
     * Created by Joeysin on  2018/6/15  下午5:30.
     * Describe：查询db_1中的所有table
     */
    @Test
    public void t2() {
        List<Order> orders = orderMapper.findByUserId(1);
        orders.stream().forEach(order -> System.out.println(order.toString()));
    }


    /**
     * Created by Joeysin on  2018/6/15  下午5:30.
     * Describe：查询所有Db中的 table_1
     */
    @Test
    public void t3() {
        List<Order> orders = orderMapper.findByOrderId(1);
        orders.stream().forEach(order -> System.out.println(order.toString()));

    }
}
