package com.dtm.mallproject.enums;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/18 10:43
 * @description : 操作结果枚举类
 */
public enum ResultEnum {
    /**
     * 操作结果枚举类
     */
    SUCCESS(1,"操作成功"),
    FAIL(0,"操作失败"),
    SHORTAGE(2,"库存不足"),
    DEFAULT(-1,"默认");

    private final int code;
    private final String description;

    ResultEnum(int code, String description) {
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
    public static ResultEnum getType(int code){
        for (ResultEnum resultEnum : ResultEnum.values()){
            if (resultEnum.code == code){
                return resultEnum;
            }
        }
        return DEFAULT;
    }
}
