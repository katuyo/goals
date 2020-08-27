package com.juext.asset.goals.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Excepts
 * @since 2020/6/21 11:15
 */

@Data
public class TransferCriteria implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Integer type;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amountMax;

    private Double amountMin;

    private String matter;

    private String reference;

    private String referenceCode;

    private String comment;
}
