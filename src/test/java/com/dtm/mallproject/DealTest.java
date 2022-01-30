package com.dtm.mallproject;

import com.dtm.mallproject.pojo.vo.DealInfoVO;
import com.dtm.mallproject.service.DealService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class DealTest {
    @Resource
    DealService dealService;

    @Test
    void testSelectDealInfo(){
        List<DealInfoVO> dealInfos = dealService.selectDealInfo("4ceed418a03791752931314772f15ee3");
        System.out.println(dealInfos);
    }

    @Test
    void testDeleteDeal(){
        int i = dealService.deleteDeal("153be06bc49e51c2741b81c0fe7d1c33");
        System.out.println(i);
    }
}
