package com.juext.shop.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.featx.spec.model.BaseUnified;

/**
 * @author Excepts
 * @since 2020/4/11 23:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountItem extends BaseUnified {

    private static final long serialVersionUID = 1668164802765732399L;

    private String accountCode;

    private Double inventory;

}
