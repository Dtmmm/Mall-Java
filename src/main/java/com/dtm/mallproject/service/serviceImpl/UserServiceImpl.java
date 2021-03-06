package com.dtm.mallproject.service.serviceImpl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtm.mallproject.enums.LoginEnum;
import com.dtm.mallproject.enums.ResultEnum;
import com.dtm.mallproject.exception.NoSuchUserException;
import com.dtm.mallproject.mapper.BookMapper;
import com.dtm.mallproject.mapper.UserMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.*;
import com.dtm.mallproject.service.UserService;
import com.dtm.mallproject.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 16:44
 * @description : 用户接口实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BookMapper bookMapper;

    @Override
    public UserInfoVO selectUserById(String id) {
        UserDO userDO = userMapper.selectById(id);
        return new UserInfoVO(userDO.getUserId(), userDO.getUserName(), userDO.getPhoneNumber(),
                userDO.getEmail(), userDO.getShippingAddress(), userDO.getIcon());
    }

    @Override
    public UserLoginVO login(String userId, String userPwd) {
        UserLoginVO userLoginVO = new UserLoginVO();
        // 首先是登录失败的状态，如果密码正确后面再覆盖 result 的值
        userLoginVO.setResult(LoginEnum.FAIL.getCode());

        QueryWrapper<UserDO> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        UserDO userDO = userMapper.selectOne(qw);
        try {
            if (userDO == null) {
                throw new NoSuchUserException("该用户不存在");
            }
            // 加密密码
            String md5HexPwd = DigestUtil.md5Hex(userPwd);
            if (userDO.getUserPwd().equals(md5HexPwd)) {
                // 组装数据
                userLoginVO.setId(userDO.getId());
                userLoginVO.setUserId(userId);
                userLoginVO.setUserName(userDO.getUserName());
                // 判断是否是管理员登录
                int result = "admin000".equals(userId)?LoginEnum.MANAGER.getCode():LoginEnum.SUCCESS.getCode();
                userLoginVO.setResult(result);

                // 更新用户的最近登录时间
                updateLastLoginDate(userDO.getId());
            }
        } catch (NoSuchUserException e) {
            log.error("*****该用户不存在*****");
            log.error(e.getMessage());
        }

        return userLoginVO;
    }

    /**
     * 更新用户的最近登录时间
     * @param id 用户编号
     */
    private void updateLastLoginDate(String id){
        // 获取当前时间
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        UserDO user = new UserDO();
        user.setId(id);
        user.setLastLoginDate(date);
        userMapper.updateById(user);
    }

    @Override
    public Integer register(UserDO user) {
        String userPwd = user.getUserPwd();
        user.setUserPwd(DigestUtil.md5Hex(userPwd));
        return userMapper.insert(user);
    }

    @Override
    public Integer updateUser(UserDO user) {
        return userMapper.updateById(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer updateCartInfo(String userId, String bookId, Integer quantity) {
        // 1.判断 Redis 中图书的库存
        int inventory = Integer.parseInt(redisUtil.hGet("inventory", bookId));
        if (inventory < quantity) {
            return ResultEnum.SHORTAGE.getCode();
        }

        // 2.开启事务执行增加操作
        List<Object> execute = (List<Object>)redisUtil.getRedisTemplate().execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch("cart_"+userId);
                operations.multi();
                operations.opsForHash().increment("cart_"+userId, bookId, quantity.longValue());
                operations.opsForHash().increment("inventory",bookId,-quantity.longValue());
                return operations.exec();
            }
        });

        // 3.判断事务执行结果
        if (execute == null || execute.size() == 0) {
            log.error("*****事务执行失败*****");
            return ResultEnum.FAIL.getCode();
        }

        // operations.exec()的返回值是一个集合，对应所有的事务操作
        // execute.get(0)对应增加到购物车的操作
        if (Integer.parseInt(execute.get(0).toString()) >= 0){
            // execute.get(1)对应减少库存的操作
            if (Integer.parseInt(execute.get(1).toString()) >= 0) {
                // 事务都执行成功了
                /*
                同步操作由定时任务完成
                int nowInventory = Integer.parseInt(redisUtil.hGet("inventory", bookId));
                BookDO book = new BookDO();
                book.setId(bookId);
                book.setInventory(nowInventory);
                int updateResult = bookMapper.updateById(book);
                if (updateResult == 1) {return success;}
                */
                return ResultEnum.SUCCESS.getCode();
            }
            // 库存没有成功减少(高并发状态下才有可能出现)，恢复库存
            log.error("*****减少库存失败*****");
            redisUtil.hIncrBy("inventory", bookId, quantity.longValue());
            return ResultEnum.SHORTAGE.getCode();
        } else {
            log.error("*****增加图书到购物车失败*****");
            return ResultEnum.FAIL.getCode();
        }
    }

    @Override
    public List<CartInfoVO> cartInfo(String id) {
        List<CartInfoVO> cart = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        Map<String, String> entries = redisUtil.hEntries("cart_"+id);
        entries.forEach((bookId,quantity) -> {
            BookDO book = bookMapper.selectOne(new QueryWrapper<BookDO>().eq("id", bookId));
            double discountPrice = Double.parseDouble(df.format(book.getPrice() * book.getDiscount()));
            cart.add(new CartInfoVO(book.getId(),book.getBookName(),discountPrice,
                    book.getAuthor(), book.getPress(),Integer.parseInt(quantity),book.getImg()));
        });
        return cart;
    }

    @Override
    public Integer deleteCart(String id, String bookId, Integer quantity) {
        // 返还图书库存
        Long returnInventory = redisUtil.hIncrBy("inventory",bookId,quantity.longValue());
        if (returnInventory <= 0) {
            log.error("*****返还图书库存失败*****");
            return ResultEnum.FAIL.getCode();
        }
        // 同步库存信息到数据库
        /*
        同步操作由定时任务完成
        int nowInventory = Integer.parseInt(redisUtil.hGet("inventory", bookId));
        BookDO book = new BookDO();
        book.setId(bookId);
        book.setInventory(nowInventory);
        int updateResult = bookMapper.updateById(book);
        if (updateResult <= 0) {return 0;}
        */
        return redisUtil.hDel("cart_"+id, bookId);
    }

    @Override
    public Integer addToCollection(String id, String bookId) {
        return redisUtil.sAdd("col_"+id, bookId);
    }

    @Override
    public List<CollectionInfoVO> collectionInfo(String id) {
        List<CollectionInfoVO> collection = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        Set<String> members = redisUtil.sMembers("col_" + id);
        members.forEach(bookId -> {
            BookDO book = bookMapper.selectOne(new QueryWrapper<BookDO>().eq("id", bookId));
            double discountPrice = Double.parseDouble(df.format(book.getPrice() * book.getDiscount()));
            collection.add(new CollectionInfoVO(book.getId(),book.getBookName(),discountPrice,
                    book.getDiscount(), book.getPrice(),book.getAuthor(),book.getPress(),book.getImg()));
        });
        return collection;
    }

    @Override
    public Integer deleteCollection(String id, String bookId) {
        return redisUtil.sRemove("col_"+id,bookId);
    }

    @Override
    public UserInfoPageDisplayVO selectAllUserInfo(Integer currentPage, Integer pageSize) {
        Page<UserDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<UserDO> userPage = userMapper.selectPage(page, new QueryWrapper<>());

        return new UserInfoPageDisplayVO(userPage.getRecords(),userPage.getTotal(),userPage.getPages());
    }

    @Override
    public List<UserDO> searchUserInfo(String keyWord, String select) {
        QueryWrapper<UserDO> qw = new QueryWrapper<>();
        qw.like(select,keyWord);
        return userMapper.selectList(qw);
    }

    @Override
    public Integer deleteUserById(String id) {
        return userMapper.deleteById(id);
    }

    @Override
    public UserDO selectUserByCondition(String condition) {
        QueryWrapper<UserDO> qw = new QueryWrapper<>();
        qw.eq("id",condition).or().eq("user_name",condition);
        return userMapper.selectOne(qw);
    }

    @Override
    public Integer clearVisitorCart(String visitorId) {
        // 1.获取游客的购物车信息
        Map<String, String> cart = redisUtil.hEntries("cart_"+visitorId);
        // 2.返还库存
        cart.forEach((bookId,quantity) ->
            redisUtil.hIncrBy("inventory",bookId,Long.parseLong(quantity))
        );
        // 3.删除游客购物车
        return redisUtil.del("cart_"+visitorId);
    }

    @Override
    public Integer updateVisitorToUser(String visitorId, String userId) {
        // 1.获取游客的购物车信息
        Map<String, String> cart = redisUtil.hEntries("cart_"+visitorId);
        cart.forEach((bookId,quantity) -> {
            // 2.添加到登陆后账户的购物车中
            redisUtil.hIncrBy("cart_"+userId,bookId,Long.parseLong(quantity));
        });
        // 3.删除游客购物车
        redisUtil.del("cart_"+visitorId);

        return ResultEnum.SUCCESS.getCode();
    }
}
