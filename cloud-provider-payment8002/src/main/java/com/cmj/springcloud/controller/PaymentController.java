package com.cmj.springcloud.controller;

import com.cmj.springcloud.entities.CommonResult;
import com.cmj.springcloud.entities.Payment;
import com.cmj.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    // 插入接口
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果：" + result);

        if(result > 0) {
            return new CommonResult(200, "插入数据库成功！serverPost:"
                    + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败！serverPost:"
                    + serverPort, null);
        }
    }

    // 根据id查询
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("插入结果：" + payment);

        if(payment != null) {
            return new CommonResult(200, "查询成功!!！serverPort:"
                    + serverPort, payment);
        } else {
            return new CommonResult(444, "查询失败,没有对应数据，查询ID：！" + id, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> service = discoveryClient.getServices();
        for(String element : service) {
            log.info("***element:" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t"
                    + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

}
