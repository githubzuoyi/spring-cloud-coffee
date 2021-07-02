package org.springcloud.coffee.provider.controller;

import org.springcloud.coffee.provider.dao.UserRepository;
import org.springcloud.coffee.provider.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 用户微服务controller
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance/{name}")
    public Integer getBalance(@PathVariable String name) {
        User user = userRepository.findOne(name);
        System.out.println("查询余额，余额为：" + user.getBalance());
        return user.getBalance();
    }

    @GetMapping("/reducebalance/{name}/{price}")
    public Boolean reduceBalance(@PathVariable("name") String name, @PathVariable("price") Integer price) {
        User user = new User();
        user.setName(name);
        user.setBalance(price);
        userRepository.save(user);
        return true;
    }

}
