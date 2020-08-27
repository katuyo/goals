package com.juext.asset.goals.model;

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
public class IssuanceItem extends BaseUnified {

    private static final long serialVersionUID = 1668164802765732399L;

//    private ${property.type} ${property.name};
}
