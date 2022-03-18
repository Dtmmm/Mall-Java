package com.dtm.mallproject.enums;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/18 11:39
 * @description : 订单状态枚举类
 */
public enum DealStateEnum {
    /**
     * 订单状态枚举类
     */
    DEAL_FINISH(1,"交易完成"),
    NOT_SHIPPED(2,"尚未发货"),
    SHIPPED(3,"已发货"),
    RECEIVED(4,"已签收"),
    COMMENTED(5,"已评论"),
    TO_BE_PAID(6,"待支付"),
    DEFAULT(-1,"默认");

    private final int code;
    private final String description;

    DealStateEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code获取相应的枚举对象
     * @param code 代码
     * @return 相应的枚举对象
     */
    public static DealStateEnum getType(int code){
        for (DealStateEnum dealStateEnum : DealStateEnum.values()){
            if (dealStateEnum.code == code){
                return dealStateEnum;
            }
        }
        return DEFAULT;
    }
}
