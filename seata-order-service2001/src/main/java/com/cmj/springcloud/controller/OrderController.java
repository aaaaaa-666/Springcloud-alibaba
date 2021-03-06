package com.cmj.springcloud.controller;

import com.cmj.springcloud.domain.CommonResult;
import com.cmj.springcloud.domain.Order;
import com.cmj.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult(200, "创建订单成功！！！");
    }
}
