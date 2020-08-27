package com.juext.asset.goals.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Excepts
 * @since 2020/6/21 11:15
 */
@Data
public class AccountCriteria implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Integer type;

    private String comment;

    private Double inventoryMax;

    private Double inventoryMin;

}
