package com.dtm.mallproject;

import com.dtm.mallproject.mapper.UserMapper;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.CartInfoVO;
import com.dtm.mallproject.pojo.vo.CollectionInfoVO;
import com.dtm.mallproject.pojo.vo.UserLoginVO;
import com.dtm.mallproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserTest {
    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Test
    void testCreateUer(){
        UserDO user = new UserDO();
        user.setUserId("007");
        user.setUserName("测试");
        userMapper.insert(user);
    }

    @Test
    void testDeleteUser(){
        userMapper.deleteById("9cdc7a0d297d4209c7517c74fae81508");
    }

    @Test
    void testLogin(){
        UserLoginVO result = userService.login("001", "123");
        System.out.println(result);
    }

    @Test
    void testUpdateUser(){
        UserDO user = new UserDO();
        user.setId("a135e5b5579b3915affc58bf8eed6725");
        user.setUserName("ww");
        Integer result = userService.updateUser(user);
        System.out.println(result);
    }

    @Test
    void testAdToCart(){
        Integer i = userService.updateCartInfo("4ceed418a03791752931314772f15ee3", "002", 1-3);
        System.out.println("i:"+i);
    }

    @Test
    void testGetCart(){
        List<CartInfoVO> cart = userService.cartInfo("4ceed418a03791752931314772f15ee3");
        cart.forEach(System.out::println);
    }

    @Test
    void testGetCollection(){
        List<CollectionInfoVO> collection = userService.collectionInfo("4ceed418a03791752931314772f15ee3");
        collection.forEach(System.out::println);
    }
}
