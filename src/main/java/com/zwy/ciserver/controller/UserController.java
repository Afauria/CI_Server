package com.zwy.ciserver.controller;

import com.zwy.ciserver.entity.UserEntity;
import com.zwy.ciserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Afauria on 2019/1/22.
 */
// @ResponseBody表示该方法的返回结果直接写入HTTP response body中，一般在异步获取数据时使用，用于构建RESTful的api。
// 在使用@RequestMapping后，返回值通常解析为跳转路径，加上@responsebody后返回结果不会被解析为跳转路径，
// 而是直接写入HTTP response body中。比如异步获取json数据，加上@responsebody后，会直接返回json数据。
// 该注解一般会配合@RequestMapping一起使用。
// @Controller用于定义控制器类，在spring 项目中由控制器负责将用户发来的URL请求转发到对应的服务接口（service层），
// 一般这个注解在类中，通常方法需要配合注解@RequestMapping。
@RestController//相当于@Controller+@ResponseBody
@RequestMapping(value = "/user")
public class UserController {
// @Autowired自动导入对象,替代new,实现解耦,@Autowired(require=false)时就算找不到也不会报错
// @Inject相当于默认的@Autowired,没有require属性
// @Qualifier：当有多个同一类型的Bean时，可以用@Qualifier(“name”)来指定。与@Autowired配合使用。
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public int addUser(UserEntity user) {
        return userService.addUser(user);
    }

    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        return userService.findAllUser(pageNum, pageSize);
    }
}

