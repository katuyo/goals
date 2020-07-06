package com.juext.shop.base.model;

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
public class IssuanceInfo extends BaseUnified {

    private static final long serialVersionUID = -3141043832603954339L;

    private ${property.type} ${property.name};
}
