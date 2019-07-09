package cn.zhh.admin.enums;

import lombok.Getter;

/**
 * 是否启用枚举
 *
 * @author z_hh
 */
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
        for (EnabledEnum enabledEnum : EnabledEnum.values()) {
            if (code.equals(enabledEnum.code)) {
                return enabledEnum.getDescription();
            }
        }
        throw new RuntimeException("未知枚举类型");
    }

}
