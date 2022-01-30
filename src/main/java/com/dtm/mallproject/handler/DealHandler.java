package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.vo.DealInfoPageDisplayVO;
import com.dtm.mallproject.pojo.vo.DealInfoVO;
import com.dtm.mallproject.service.DealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 17:33
 * @description : 订单的相关操作对应的处理器
 */
@Controller
@RequestMapping("/deal")
public class DealHandler {
    @Resource
    DealService dealService;

    /**
     * 结算功能
     *
     * @param dealInfo DealInfoVO 对象，包含订单信息和订单详情信息
     * @return 操作结果，1:成功 0:失败
     */
    @PostMapping("/check")
    @ResponseBody
    public Integer check(@RequestBody DealInfoVO dealInfo){
        return dealService.check(dealInfo.getDeal(), dealInfo.getDealDetails());
    }

    /**
     * 立即购买功能
     *
     * @param dealInfo DealInfoVO 对象，包含订单信息和订单详情信息
     * @return 0:库存不足 1:成功 2:失败
     */
    @PostMapping("/buyNow")
    @ResponseBody
    public Integer buyNow(@RequestBody DealInfoVO dealInfo){
        return dealService.buyNow(dealInfo.getDeal(), dealInfo.getDealDetails());
    }

    /**
     * 查看用户订单
     *
     * @param id 用户编号
     * @return DealInfoVO 对象，包含用户订单信息
     */
    @GetMapping("/selectDealInfo/{id}")
    @ResponseBody
    public List<DealInfoVO> selectDealInfo(@PathVariable String id){
        return dealService.selectDealInfo(id);
    }

    /**
     * 未支付的订单重新支付
     *
     * @param dealId 订单号
     * @param payWay 支付方式
     * @return 操作结果
     */
    @PostMapping("/pay")
    @ResponseBody
    public Integer pay(@RequestParam String dealId, @RequestParam String payWay){
        return dealService.pay(dealId,payWay);
    }

    /**
     * 取消订单
     *
     * @param dealId 订单号
     * @return 操作结果
     */
    @PostMapping("/deleteDeal")
    @ResponseBody
    public Integer deleteDeal(@RequestParam String dealId){
        return dealService.deleteDeal(dealId);
    }

    /**
     * 分页查询所有订单
     *
     * @return 所有订单
     */
    @GetMapping("/selectAllDeal/{currentPage}/{pageSize}")
    @ResponseBody
    public DealInfoPageDisplayVO selectAllDeal(@PathVariable Integer currentPage,
                                               @PathVariable Integer pageSize){
        return dealService.selectAllDeal(currentPage,pageSize);
    }

    /**
     * 模糊搜索订单
     *
     * @param keyWord 关键字
     * @param select 搜索范围
     * @return 订单
     */
    @GetMapping("/searchDeal/{keyWord}/{select}")
    @ResponseBody
    public List<DealInfoVO> searchDeal(@PathVariable String keyWord,
                                       @PathVariable String select){
        return dealService.searchDeal(keyWord,select);
    }

    /**
     * 根据订单编号删除订单
     *
     * @param id 订单编号
     * @return 操作结果
     */
    @DeleteMapping("/deleteDealById/{id}")
    @ResponseBody
    public Integer deleteDealById(@PathVariable String id){
        return dealService.deleteDealById(id);
    }

    /**
     * 修改订单状态
     *
     * @param id 订单编号
     * @param state 状态码
     * @return 操作结果
     */
    @PostMapping("/updateDealState")
    @ResponseBody
    public Integer updateDealState(@RequestParam String id, @RequestParam String state){
        return dealService.updateDealState(id,Integer.parseInt(state));
    }

}
