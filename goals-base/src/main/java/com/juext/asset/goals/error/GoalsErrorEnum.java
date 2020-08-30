package com.juext.asset.goals.error;

import lombok.Getter;
import org.featx.spec.enums.BaseEnum;

/**
 * @author Excepts
 * @since 2020/8/30 4:08
 */

public enum GoalsErrorEnum implements BaseEnum {
    ACCOUNT_NOT_FOUND(40410001, "Account not found"),
    INVENTORY_NOT_ENOUGH(40010001, "Inventory not afford"),
    TRANSFER_AMOUNT_NEGATIVE(40010002, "Transfer Negative"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String message;

    GoalsErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
