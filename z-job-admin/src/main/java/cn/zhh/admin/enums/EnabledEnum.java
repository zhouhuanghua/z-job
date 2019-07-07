package cn.zhh.admin.enums;

import lombok.Getter;

public enum EnabledEnum {

    YES((byte)1, "启用"),
    NO((byte)0, "停用");

    @Getter
    private Byte code;

    @Getter
    private String description;

    private EnabledEnum(Byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescription(Byte code) {
        EnabledEnum[] values = EnabledEnum.values();
        for (EnabledEnum value : values) {
            if (value.getCode().equals(code)) {
                return value.getDescription();
            }
        }
        return null;
    }

    public static String code2desc(Byte code) {
        for (EnabledEnum addressTypeEnum : EnabledEnum.values()) {
            if (code.equals(addressTypeEnum.code)) {
                return addressTypeEnum.getDescription();
            }
        }
        throw new RuntimeException("未知枚举类型");
    }

}
