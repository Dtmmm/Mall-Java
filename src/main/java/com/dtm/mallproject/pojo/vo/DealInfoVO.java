package com.dtm.mallproject.pojo.vo;

import com.dtm.mallproject.pojo.entity.DealDO;
import com.dtm.mallproject.pojo.entity.DealDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 18:02
 * @description : View Object，对应用户订单信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealInfoVO {
    private DealDO deal;
    private List<DealDetailDO> dealDetails;
}
