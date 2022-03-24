package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.vo.DealInfoPageDisplayVO;
import com.dtm.mallproject.pojo.vo.DealInfoVO;
import com.dtm.mallproject.service.DealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 17:33
 * @description : 订单的相关操作对应的处理器
 */
@Api(tags = "DealHandler-订单的相关操作对应的处理器")
@RestController
@RequestMapping("/deal")
public class DealHandler {
    @Resource
    private DealService dealService;

    /**
     * 结算功能
     * @param dealInfo DealInfoVO 对象，包含订单信息和订单详情信息
     * @return 操作结果，1:成功 0:失败
     */
    @ApiOperation("结算功能")
    @PostMapping("/check")
    public Integer check(
            @ApiParam("DealInfoVO 对象，包含订单信息和订单详情信息") @RequestBody DealInfoVO dealInfo){
        return dealService.check(dealInfo.getDeal(), dealInfo.getDealDetails());
    }

    /**
     * 立即购买功能
     * @param dealInfo DealInfoVO 对象，包含订单信息和订单详情信息
     * @return 0:库存不足 1:成功 2:失败
     */
    @ApiOperation("立即购买功能")
    @PostMapping("/buyNow")
    public Integer buyNow(
            @ApiParam("DealInfoVO 对象，包含订单信息和订单详情信息") @RequestBody DealInfoVO dealInfo){
        return dealService.buyNow(dealInfo.getDeal(), dealInfo.getDealDetails());
    }

    /**
     * 查看用户订单
     * @param userId 用户编号
     * @return DealInfoVO 对象，包含用户订单信息
     */
    @ApiOperation("查看用户订单")
    @GetMapping("/selectDealInfo/{userId}")
    public List<DealInfoVO> selectDealInfo(@ApiParam("用户编号") @PathVariable String userId){
        return dealService.selectDealInfo(userId);
    }

    /**
     * 未支付的订单重新支付
     * @param dealId 订单号
     * @param payWay 支付方式
     * @return 操作结果
     */
    @ApiOperation("未支付的订单重新支付")
    @GetMapping("/pay/{dealId}/{payWay}")
    public Integer pay(
            @ApiParam("订单号") @PathVariable String dealId,
            @ApiParam("支付方式") @PathVariable String payWay){
        return dealService.pay(dealId,payWay);
    }

    /**
     * 取消订单
     * @param dealId 订单号
     * @return 操作结果
     */
    @ApiOperation("取消订单")
    @DeleteMapping("/deleteDeal/{dealId}")
    public Integer deleteDeal(
            @ApiParam("订单号") @PathVariable String dealId){
        return dealService.deleteDeal(dealId);
    }

    /**
     * 分页查询所有订单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 所有订单
     */
    @ApiOperation("分页查询所有订单")
    @GetMapping("/selectAllDeal/{currentPage}/{pageSize}")
    public DealInfoPageDisplayVO selectAllDeal(
            @ApiParam("当前页") @PathVariable Integer currentPage,
            @ApiParam("页面大小") @PathVariable Integer pageSize){
        return dealService.selectAllDeal(currentPage,pageSize);
    }

    /**
     * 模糊搜索订单
     * @param keyWord 关键字
     * @param select 搜索范围
     * @return 订单
     */
    @ApiOperation("模糊搜索订单")
    @GetMapping("/searchDeal/{keyWord}/{select}")
    public List<DealInfoVO> searchDeal(
            @ApiParam("关键字") @PathVariable String keyWord,
            @ApiParam("搜索范围") @PathVariable String select){
        return dealService.searchDeal(keyWord,select);
    }

    /**
     * 根据订单编号删除订单
     * @param id 订单编号
     * @return 操作结果
     */
    @ApiOperation("根据订单编号删除订单")
    @DeleteMapping("/deleteDealById/{id}")
    public Integer deleteDealById(@ApiParam("订单编号") @PathVariable String id){
        return dealService.deleteDealById(id);
    }

    /**
     * 修改订单状态
     * @param dealId 订单编号
     * @param state 状态码
     * @return 操作结果
     */
    @ApiOperation("修改订单状态")
    @PostMapping("/updateDealState/{dealId}/{state}")
    public Integer updateDealState(@PathVariable String dealId, @PathVariable String state){
        return dealService.updateDealState(dealId,Integer.parseInt(state));
    }

}
