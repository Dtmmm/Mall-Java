package com.dtm.mallproject.enums;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/18 11:28
 * @description : 登录操作结果枚举类
 */
public enum LoginEnum {
    /**
     * 登录操作结果枚举类
     */
    SUCCESS(1,"登录成功"),
    FAIL(0,"登录失败"),
    MANAGER(2,"登录成功，且账号为管理员"),
    DEFAULT(-1,"默认");

    private final int code;
    private final String description;

    LoginEnum(int code, String description) {
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
    public static LoginEnum getType(int code){
        for (LoginEnum loginEnum : LoginEnum.values()){
            if (loginEnum.code == code){
                return loginEnum;
            }
        }
        return DEFAULT;
    }
}
