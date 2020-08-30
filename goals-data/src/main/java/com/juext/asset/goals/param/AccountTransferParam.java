package com.juext.asset.goals.param;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Excepts
 * @since 2020/8/30 13:16
 */
public class AccountTransferParam implements Serializable {

    @Getter
    private String fromAccountCode;
    @Getter
    private String toAccountCode;
    @Getter
    private Double amount;

    public static AccountTransferParam newInstance() {
        return new AccountTransferParam();
    }

    public AccountTransferParam fromAccountCode(String fromAccountCode) {
        this.fromAccountCode = fromAccountCode;
        return this;
    }

    public AccountTransferParam toAccountCode(String toAccountCode) {
        this.toAccountCode = toAccountCode;
        return this;
    }

    public AccountTransferParam amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public List<String> accountCodes() {
        return Lists.newArrayList(fromAccountCode, toAccountCode);
    }
}
