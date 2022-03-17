package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.*;
import com.dtm.mallproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "UserHandler-用户的相关操作对应的处理器")
@Controller
@RequestMapping("/user")
public class UserHandler {
    @Resource
    private UserService userService;

    /**
     * 根据用户编号查询用户信息
     * @param id 用户编号
     * @return 用户信息
     */
    @ApiOperation("根据用户编号查询用户信息")
    @GetMapping("/selectUserById/{id}")
    @ResponseBody
    public UserInfoVO selectUserById(@ApiParam("用户编号") @PathVariable String id){
        return userService.selectUserById(id);
    }

    /**
     * 登录
     * @param userId 账号
     * @param userPwd 密码
     * @return UserLoginVO 对象，包含用户信息和登录结果
     */
    @ApiOperation("登录(使用表单传递参数)")
    @PostMapping("/login")
    @ResponseBody
    public UserLoginVO login(
            @ApiParam("账号") @RequestParam String userId,
            @ApiParam("密码") @RequestParam String userPwd){
        return userService.login(userId,userPwd);
    }

    /**
     * 注册
     * @param user 用户信息
     * @return 操作结果
     */
    @ApiOperation("注册")
    @PutMapping("/register")
    @ResponseBody
    public Integer register(@ApiParam("用户信息") @RequestBody UserDO user){
        return userService.register(user);
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改结果，1表示修改成功，0表示修改失败
     */
    @ApiOperation("修改用户信息")
    @PutMapping("/updateUser")
    @ResponseBody
    public Integer updateUser(@ApiParam("用户信息") @RequestBody UserDO user){
        return userService.updateUser(user);
    }

    /**
     * 添加至购物车
     * @param updateCartVO 参数对象，包含用户编号、图书编号、数量
     * @return 操作结果，0:库存不足 1:成功 2:失败
     */
    @ApiOperation("添加至购物车")
    @PostMapping("/addToCart")
    @ResponseBody
    public Integer addToCart(
            @ApiParam("参数对象，包含用户编号、图书编号、数量") @RequestBody UpdateCartVO updateCartVO){
        return userService.updateCartInfo(updateCartVO.getId(),updateCartVO.getBookId(),updateCartVO.getQuantity());
    }

    /**
     * 获取用户购物车信息
     * @param id 用户编号
     * @return 购物车信息
     */
    @ApiOperation("获取用户购物车信息")
    @GetMapping("/cartInfo/{id}")
    @ResponseBody
    public List<CartInfoVO> cartInfo(@ApiParam("用户编号") @PathVariable String id){
        return userService.cartInfo(id);
    }

    /**
     * 从购物车中删除书
     * @param updateCartVO 参数对象，包含用户编号、图书编号、数量
     * @return 操作结果，1：删除成功 0：删除失败
     */
    @ApiOperation("从购物车中删除书")
    @PostMapping("/deleteCart")
    @ResponseBody
    public Integer deleteCart(
            @ApiParam("参数对象，包含用户编号、图书编号、数量") @RequestBody UpdateCartVO updateCartVO){
        return userService.deleteCart(updateCartVO.getId(),updateCartVO.getBookId(),updateCartVO.getQuantity());
    }

    /**
     * 改变购物车中图书的数量
     * @param updateCartVO 参数对象，包含用户编号、图书编号、数量
     * @return 操作结果，0:库存不足 1:成功 2:失败
     */
    @ApiOperation("改变购物车中图书的数量")
    @PutMapping("/changeQuantity")
    @ResponseBody
    public Integer changeQuantity(
            @ApiParam("参数对象，包含用户编号、图书编号、数量") @RequestBody UpdateCartVO updateCartVO){
        return userService.updateCartInfo(updateCartVO.getId(),updateCartVO.getBookId(),updateCartVO.getQuantity());
    }

    /**
     * 添加到收藏夹
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1:成功 0:失败
     */
    @ApiOperation("添加到收藏夹")
    @PostMapping("/addToCollection/{id}/{bookId}")
    @ResponseBody
    public Integer addToCollection(
            @ApiParam("用户编号") @PathVariable String id,
            @ApiParam("图书编号") @PathVariable String bookId){
        return userService.addToCollection(id,bookId);
    }

    /**
     * 获取用户收藏夹信息
     * @param id 用户编号
     * @return 收藏夹信息
     */
    @ApiOperation("获取用户收藏夹信息")
    @GetMapping("/collectionInfo/{id}")
    @ResponseBody
    public List<CollectionInfoVO> collectionInfo(@ApiParam("用户编号") @PathVariable String id){
        return userService.collectionInfo(id);
    }

    /**
     * 从收藏夹中删除图书
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1：删除成功 0：删除失败
     */
    @ApiOperation("从收藏夹中删除图书")
    @DeleteMapping("/deleteCollection/{id}/{bookId}")
    @ResponseBody
    public Integer deleteCollection(
            @ApiParam("用户编号") @PathVariable String id,
            @ApiParam("图书编号") @PathVariable String bookId){
        return userService.deleteCollection(id,bookId);
    }

    /**
     * 清空游客购物车
     * @param visitorId 游客编号
     * @return 操作结果
     */
    @ApiOperation("清空游客购物车")
    @GetMapping("/clearVisitorCart/{visitorId}")
    @ResponseBody
    public Integer clearVisitorCart(@ApiParam("游客编号") @PathVariable String visitorId){
        return userService.clearVisitorCart(visitorId);
    }

    /**
     * 游客登录后，更新合并购物车信息，并删除之前的信息
     * @param visitorId 游客编号
     * @param userId 用户编号
     * @return 操作结果
     */
    @ApiOperation("游客登录后，更新合并购物车信息，并删除之前的信息")
    @GetMapping("/updateVisitorToUser/{visitorId}/{userId}")
    @ResponseBody
    public Integer updateVisitorToUser(
            @ApiParam("游客编号") @PathVariable String visitorId,
            @ApiParam("用户编号") @PathVariable String userId){
        return userService.updateVisitorToUser(visitorId, userId);
    }
}
