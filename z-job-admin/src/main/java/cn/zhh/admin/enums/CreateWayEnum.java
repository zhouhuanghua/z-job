package cn.zhh.admin.enums;

import lombok.Getter;

public enum CreateWayEnum {

    AUTO((byte)0, "自动注册"),
    MANUAL((byte)1, "手动录入");

    @Getter
    private Byte code;

    @Getter
    private String description;

    private CreateWayEnum(Byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescription(Byte code) {
        CreateWayEnum[] values = CreateWayEnum.values();
        for (CreateWayEnum value : values) {
            if (value.getCode().equals(code)) {
                return value.getDescription();
            }
        }
        return null;
    }

    public static String code2desc(Byte code) {
        for (CreateWayEnum addressTypeEnum : CreateWayEnum.values()) {
            if (code.equals(addressTypeEnum.code)) {
                return addressTypeEnum.getDescription();
            }
        }
        throw new RuntimeException("未知枚举类型");
    }

}
