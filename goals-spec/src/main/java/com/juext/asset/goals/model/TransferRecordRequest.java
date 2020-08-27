package com.juext.asset.goals.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author Excepts
 * @since 2020/4/11 22:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class TransferRecordRequest {

    private static final long serialVersionUID = 3542473394788128677L;

    private Integer type;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amount;

    private Integer matter;

    private String reference;

    private String referenceCode;

    private String comment;

}
