package com.dtm.mallproject.service;

import com.dtm.mallproject.pojo.entity.DealDO;
import com.dtm.mallproject.pojo.entity.DealDetailDO;
import com.dtm.mallproject.pojo.vo.DealInfoPageDisplayVO;
import com.dtm.mallproject.pojo.vo.DealInfoVO;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 17:31
 * @description : 订单的相关操作对应的接口
 */
public interface DealService {
    /**
     * 查询用户订单信息
     * @param userId 用户编号
     * @return DealInfoVO 对象，包含订单信息和订单详细信息
     */
    List<DealInfoVO> selectDealInfo(String userId);

    /**
     * 结算功能
     * @param deal 订单
     * @param dealDetails 订单详情
     * @return 操作结果，1:成功 0:失败
     */
    Integer check(DealDO deal, List<DealDetailDO> dealDetails);

    /**
     * 立即购买功能
     * @param deal 订单
     * @param dealDetails 订单详情
     * @return 操作结果，0:库存不足 1:成功 2:失败
     */
    Integer buyNow(DealDO deal, List<DealDetailDO> dealDetails);


    /**
     * 未支付的订单重新支付
     * @param dealId 订单号
     * @param payWay 支付方式
     * @return 操作结果
     */
    Integer pay(String dealId, String payWay);

    /**
     * 取消订单
     * @param dealId 订单号
     * @return 操作结果
     */
    Integer deleteDeal(String dealId);

    /**
     * 分页查询所有订单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的订单
     */
    DealInfoPageDisplayVO selectAllDeal(Integer currentPage, Integer pageSize);

    /**
     * 模糊搜索订单
     * @param keyWord 关键字
     * @param select 搜索范围
     * @return 订单
     */
    List<DealInfoVO> searchDeal(String keyWord, String select);

    /**
     * 根据编号删除订单
     * @param id 订单编号
     * @return 操作结果
     */
    Integer deleteDealById(String id);

    /**
     * 修改订单状态码
     * @param id 订单编号
     * @param state 状态码
     * @return 操作结果
     */
    Integer updateDealState(String id, Integer state);
}
