package com.juext.asset.goals.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.featx.spec.model.PageRequest;

/**
 * @author Excepts
 * @since 2020/4/11 22:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransferPageQueryRequest extends PageRequest {

    private static final long serialVersionUID = 564823245124563273L;

    private Integer type;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amount;

    private Integer matter;

    private String reference;

    private String referenceCode;

    private String comment;

}
