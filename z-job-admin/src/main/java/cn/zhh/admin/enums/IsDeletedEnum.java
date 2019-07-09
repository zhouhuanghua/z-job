package cn.zhh.admin.enums;

import lombok.Getter;

/**
 * 是否删除枚举
 *
 * @author z_hh
 */
public enum IsDeletedEnum {

    YES((byte)1, "已删除"),
    NO((byte)0, "未删除");

    @Getter
    private Byte code;

    @Getter
    private String description;

    private IsDeletedEnum(Byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescription(Byte code) {
        IsDeletedEnum[] values = IsDeletedEnum.values();
        for (IsDeletedEnum value : values) {
            if (value.getCode().equals(code)) {
                return value.getDescription();
            }
        }
        return null;
    }

    public static String code2desc(Byte code) {
        for (IsDeletedEnum addressTypeEnum : IsDeletedEnum.values()) {
            if (code.equals(addressTypeEnum.code)) {
                return addressTypeEnum.getDescription();
            }
        }
        throw new RuntimeException("未知枚举类型");
    }

}
