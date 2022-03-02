package com.dtm.mallproject.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtm.mallproject.mapper.BookMapper;
import com.dtm.mallproject.mapper.DealDetailMapper;
import com.dtm.mallproject.mapper.DealMapper;
import com.dtm.mallproject.mapper.UserMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.entity.DealDO;
import com.dtm.mallproject.pojo.entity.DealDetailDO;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.DealInfoPageDisplayVO;
import com.dtm.mallproject.pojo.vo.DealInfoVO;
import com.dtm.mallproject.service.DealService;
import com.dtm.mallproject.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 17:32
 * @description : 订单接口实现类
 */
@Service
public class DealServiceImpl implements DealService {
    @Resource
    private DealMapper dealMapper;
    @Resource
    private DealDetailMapper dealDetailMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserMapper userMapper;
    @Resource
    private BookMapper bookMapper;

    @Override
    public List<DealInfoVO> selectDealInfo(String userId) {
        ArrayList<DealInfoVO> dealInfos = new ArrayList<>();

        List<DealDO> deals = dealMapper.selectList(new QueryWrapper<DealDO>().eq("user_id", userId));
        // 遍历订单，查询订单详情，并封装
        deals.forEach(deal -> {
            DealInfoVO orderInfo = new DealInfoVO();
            orderInfo.setDeal(deal);
            List<DealDetailDO> dealDetails = dealDetailMapper.selectList(new QueryWrapper<DealDetailDO>().eq("deal_id", deal.getId()));
            orderInfo.setDealDetails(dealDetails);
            dealInfos.add(orderInfo);
        });

        return dealInfos;
    }

    @Override
    public Integer check(DealDO deal, List<DealDetailDO> dealDetails) {
        // 更新订单表
        int insertDeal = dealMapper.insert(deal);
        if (insertDeal <= 0) {
            return 0;
        }

        String dealId = deal.getId();
        String redisCartKey = "cart_" + deal.getUserId();
        for (DealDetailDO dealDetail : dealDetails) {
            // 更新订单详情表
            dealDetail.setDealId(dealId);
            int insertDealDetail = dealDetailMapper.insert(dealDetail);
            if (insertDealDetail <= 0) {return 0;}

            // 从 Redis 的购物车中删除该条记录
            int del = redisUtil.hDel(redisCartKey, dealDetail.getBookId());
            if (del <= 0) {return 0;}
        }

        // 如果这笔订单是正常支付
        if (deal.getState() == 2) {
            // 更新用户累计消费额
            int updateConsumptionResult = updateConsumption(deal);
            if (updateConsumptionResult == 0) {
                return 0;
            }

            // 更新图书累计销售量
            int updateSalesResult = updateSales(dealDetails);
            if (updateSalesResult == 0) {return 0;}
        }

        return 1;
    }

    @Override
    public Integer buyNow(DealDO deal, List<DealDetailDO> dealDetails) {
        int shortage = 0;
        int success = 1;
        int fail = 2;

        // 判断库存
        String inventory = redisUtil.hGet("inventory", dealDetails.get(0).getBookId());
        if (Integer.parseInt(inventory) <= 0) {
            return shortage;
        }

        // 库存减一
        Long hDecrBy = redisUtil.hDecrBy("inventory", dealDetails.get(0).getBookId(), 1L);
        if (hDecrBy < 0) {
            return fail;
        }

        // 同步库存信息到数据库
        /*
        同步操作由定时任务完成
        int nowInventory = Integer.parseInt(redisUtil.hGet("inventory", dealDetails.get(0).getBookId()));
        BookDO book = new BookDO();
        book.setId(dealDetails.get(0).getBookId());
        book.setInventory(nowInventory);
        int updateResult = bookMapper.updateById(book);
        if (updateResult <= 0) {return fail;}
        */

        // 更新订单表
        int insertDeal = dealMapper.insert(deal);
        if (insertDeal <= 0) {
            return fail;
        }

        // 更新订单详情表
        String dealId = deal.getId();
        for (DealDetailDO dealDetail : dealDetails) {
            dealDetail.setDealId(dealId);
            int insertDealDetail = dealDetailMapper.insert(dealDetail);
            if (insertDealDetail <= 0) {
                return fail;
            }
        }

        // 如果这笔订单是正常支付
        if (deal.getState() == 2) {
            // 更新用户累计消费额
            int updateConsumptionResult = updateConsumption(deal);
            if (updateConsumptionResult == 0) {
                return fail;
            }

            // 更新图书累计销售量
            int updateSalesResult = updateSales(dealDetails);
            if (updateSalesResult == 0) {return fail;}
        }

        return success;
    }

    /**
     * 更新用户累计消费额
     *
     * @param deal 订单对象
     * @return 操作结果
     */
    private Integer updateConsumption(DealDO deal) {
        // 获取当前用户累计消费额
        QueryWrapper<UserDO> qw = new QueryWrapper<>();
        qw.select("consumption").eq("id", deal.getUserId());
        List<Map<String, Object>> maps = userMapper.selectMaps(qw);

        UserDO user = new UserDO();
        user.setId(deal.getUserId());
        user.setConsumption(deal.getTotal() + (Float) maps.get(0).get("consumption"));
        int updateConsumptionResult = userMapper.updateById(user);

        if (updateConsumptionResult <= 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 更新图书累计销售量
     *
     * @param dealDetails 订单详情集合
     * @return 操作结果
     */
    private Integer updateSales(List<DealDetailDO> dealDetails){
        for (DealDetailDO dealDetail : dealDetails) {
            // 获取当前图书累计销售量
            QueryWrapper<BookDO> qw = new QueryWrapper<>();
            qw.select("sales").eq("id",dealDetail.getBookId());
            List<Map<String, Object>> maps = bookMapper.selectMaps(qw);

            BookDO book = new BookDO();
            book.setId(dealDetail.getBookId());
            book.setSales(dealDetail.getBookQuantity()+(int)maps.get(0).get("sales"));
            int updateQuantityResult = bookMapper.updateById(book);
            if (updateQuantityResult <= 0) {return 0;}
        }
        return 1;
    }

    @Override
    public Integer pay(String dealId, String payWay) {
        // 更新订单信息
        DealDO deal = new DealDO();
        deal.setId(dealId);
        deal.setPayWay(payWay);
        deal.setState(2);
        int updateResult = dealMapper.updateById(deal);
        if (updateResult != 1) {return 0;}

        // 更新用户累计消费额
        DealDO dealDO = dealMapper.selectOne(new QueryWrapper<DealDO>().eq("id", dealId));
        int updateConsumptionResult = updateConsumption(dealDO);
        if (updateConsumptionResult == 0) {
            return 0;
        }

        // 更新图书累计销售量
        List<DealDetailDO> dealDetails = dealDetailMapper.selectList(new QueryWrapper<DealDetailDO>().eq("deal_id", dealId));
        int updateSalesResult = updateSales(dealDetails);
        if (updateSalesResult == 0) {return 0;}

        return 1;
    }

    @Override
    public Integer deleteDeal(String dealId) {
        // 返还库存
        QueryWrapper<DealDetailDO> qw = new QueryWrapper<>();
        qw.eq("deal_id", dealId);
        List<DealDetailDO> dealDetails = dealDetailMapper.selectList(qw);
        for (DealDetailDO dealDetail : dealDetails) {
            redisUtil.hIncrBy("inventory",dealDetail.getBookId(),dealDetail.getBookQuantity().longValue());
            // 同步库存信息到数据库
            /*
            同步操作由定时任务完成
            int nowInventory = Integer.parseInt(redisUtil.hGet("inventory", dealDetail.getBookId()));
            BookDO book = new BookDO();
            book.setId(dealDetail.getBookId());
            book.setInventory(nowInventory);
            int updateResult = bookMapper.updateById(book);
            if (updateResult <= 0) {return 0;}
            */
        }

        int deleteDealResult = dealMapper.deleteById(dealId);
        int dealDealDetailResult = dealDetailMapper.delete(
                new QueryWrapper<DealDetailDO>().eq("deal_id", dealId));
        if (deleteDealResult == 1 && dealDealDetailResult > 0) {return 1;}
        return 0;
    }

    @Override
    public DealInfoPageDisplayVO selectAllDeal(Integer currentPage, Integer pageSize) {
        ArrayList<DealInfoVO> dealInfos = new ArrayList<>();
        Page<DealDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<DealDO> result = dealMapper.selectPage(page, new QueryWrapper<>());

        List<DealDO> deals = result.getRecords();
        // 遍历订单，查询订单详情，并封装
        deals.forEach(deal -> {
            DealInfoVO orderInfo = new DealInfoVO();
            orderInfo.setDeal(deal);
            List<DealDetailDO> dealDetails = dealDetailMapper.selectList(new QueryWrapper<DealDetailDO>().eq("deal_id", deal.getId()));
            orderInfo.setDealDetails(dealDetails);
            dealInfos.add(orderInfo);
        });

        return new DealInfoPageDisplayVO(dealInfos,result.getTotal(), result.getPages());
    }

    @Override
    public List<DealInfoVO> searchDeal(String keyWord, String select) {
        ArrayList<DealInfoVO> dealInfos = new ArrayList<>();

        QueryWrapper<DealDO> qw = new QueryWrapper<>();
        qw.like(select, keyWord);
        List<DealDO> deals = dealMapper.selectList(qw);
        // 遍历订单，查询订单详情，并封装
        deals.forEach(deal -> {
            DealInfoVO orderInfo = new DealInfoVO();
            orderInfo.setDeal(deal);
            List<DealDetailDO> dealDetails = dealDetailMapper.selectList(new QueryWrapper<DealDetailDO>().eq("deal_id", deal.getId()));
            orderInfo.setDealDetails(dealDetails);
            dealInfos.add(orderInfo);
        });

        return dealInfos;
    }

    @Override
    public Integer deleteDealById(String id) {
        // 删除订单记录
        int result = dealMapper.deleteById(id);
        if (result == 1) {
            // 删除订单详情
            QueryWrapper<DealDetailDO> qw = new QueryWrapper<>();
            qw.eq("deal_id",id);
            int delete = dealDetailMapper.delete(qw);
            // 都成功则返回1
            if (delete >0) {return 1;}
        }
        // 订单记录删除失败
        return 0;
    }

    @Override
    public Integer updateDealState(String id, Integer state) {
        DealDO deal = new DealDO();
        deal.setId(id);
        deal.setState(state);
        return dealMapper.updateById(deal);
    }
}
