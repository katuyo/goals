package com.juext.asset.goals.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.featx.spec.model.BaseUnified;

/**
 * @author Excepts
 * @since 2020/4/11 23:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransferInfo extends BaseUnified {

    private static final long serialVersionUID = -3141043832603954339L;

    private Integer type;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amount;

    private Integer matter;

    private String reference;

    private String referenceCode;

    private String comment;

}
