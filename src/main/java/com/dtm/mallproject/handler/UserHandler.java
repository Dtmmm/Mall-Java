package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.CartInfoVO;
import com.dtm.mallproject.pojo.vo.CollectionInfoVO;
import com.dtm.mallproject.pojo.vo.UserInfoVO;
import com.dtm.mallproject.pojo.vo.UserLoginVO;
import com.dtm.mallproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 17:03
 * @description : 用户的相关操作对应的处理器
 */
@Controller
@RequestMapping("/user")
public class UserHandler {
    @Resource
    UserService userService;

    /**
     * 根据用户编号查询用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    @GetMapping("/selectUserById/{id}")
    @ResponseBody
    public UserInfoVO selectUserById(@PathVariable String id){
        return userService.selectUserById(id);
    }

    /**
     * 登录
     *
     * @param userId 账号
     * @param userPwd 密码
     * @return UserLoginVO 对象，包含用户信息和登录结果
     */
    @PostMapping("/login")
    @ResponseBody
    public UserLoginVO login(@RequestParam String userId, @RequestParam String userPwd){
        return userService.login(userId,userPwd);
    }

    /**
     * 注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @PutMapping("/register")
    @ResponseBody
    public Integer register(@RequestBody UserDO user){
        return userService.register(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 新的用户信息
     * @return 修改结果，1表示修改成功，0表示修改失败
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public Integer updateUser(UserDO user){
        return userService.updateUser(user);
    }

    /**
     * 添加购物车
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @param quantity 数量
     * @return 操作结果，0:库存不足 1:成功 2:失败
     */
    @PostMapping("/addToCart")
    @ResponseBody
    public Integer addToCart(@RequestParam String id, @RequestParam String bookId, @RequestParam Integer quantity){
        return userService.updateCartInfo(id,bookId, quantity);
    }

    /**
     * 获取用户购物车信息
     *
     * @param id 用户编号
     * @return 购物车信息
     */
    @GetMapping("/cartInfo/{id}")
    @ResponseBody
    public List<CartInfoVO> cartInfo(@PathVariable String id){
        return userService.cartInfo(id);
    }

    /**
     * 删除购物车
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1：删除成功 0：删除失败
     */
    @PostMapping("/deleteCart")
    @ResponseBody
    public Integer deleteCart(@RequestParam String id,@RequestParam String bookId,@RequestParam Integer quantity){
        return userService.deleteCart(id,bookId,quantity);
    }

    /**
     * 改变购物车中图书的数量
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @param currentValue 改变后的数量
     * @param oldValue 改变前的数量
     * @return 操作结果，0:库存不足 1:成功 2:失败
     */
    @PostMapping("/changeQuantity")
    @ResponseBody
    public Integer changeQuantity(@RequestParam String id,@RequestParam String bookId,
                    @RequestParam Integer currentValue,@RequestParam Integer oldValue){
        return userService.updateCartInfo(id,bookId,currentValue-oldValue);
    }

    /**
     * 添加到收藏夹
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1:成功 0:失败
     */
    @PostMapping("/addToCollection")
    @ResponseBody
    public Integer addToCollection(@RequestParam String id,@RequestParam String bookId){
        return userService.addToCollection(id,bookId);
    }

    /**
     * 获取用户收藏夹信息
     *
     * @param id 用户编号
     * @return 收藏夹信息
     */
    @GetMapping("/collectionInfo/{id}")
    @ResponseBody
    public List<CollectionInfoVO> collectionInfo(@PathVariable String id){
        return userService.collectionInfo(id);
    }

    /**
     * 从收藏夹中删除图书
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1：删除成功 0：删除失败
     */
    @PostMapping("/deleteCollection")
    @ResponseBody
    public Integer deleteCollection(@RequestParam String id, @RequestParam String bookId){
        return userService.deleteCollection(id,bookId);
    }
}
