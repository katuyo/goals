package com.juext.asset.goals.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.featx.spec.model.BaseUnified;


/**
 * @author Excepts
 * @since 2020/4/11 22:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransferSaveRequest extends BaseUnified {

    private static final long serialVersionUID = 5570811073514589053L;

    private Integer type;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amount;

    private Integer matter;

    private String reference;

    private String referenceCode;

    private String comment;

}
