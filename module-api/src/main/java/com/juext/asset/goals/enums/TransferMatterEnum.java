package com.juext.shop.base.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Excepts
 * @since 2020/4/12 15:21
 */
@Getter
public enum TransferMatterEnum {
    /**
     * Default matter from System one to System another one
     */
    Default(0, "Default"),
    /**
     * From System account to normal account
     */
    Archive(1, "Archive"),
    /**
     * From Normal account to System account
     */
    Payment(2, "Payment"),
    /**
     * From Normal account to another account
     */
    Given(3, "Given");

    private final Integer code;

    private final String value;

    TransferMatterEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public boolean codeEquals(Integer code) {
        return this.code.equals(code);
    }

    public static TransferMatterEnum of(Integer code) {
        return Arrays.stream(TransferMatterEnum.values())
                .filter(channelTypeEnum -> channelTypeEnum.codeEquals(code))
                .findFirst().orElse(null);
    }
}
