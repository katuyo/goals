package com.juext.shop.base.model;

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
public class IssuanceSaveRequest extends BaseUnified {

    private static final long serialVersionUID = 5570811073514589053L;

    private ${property.type} ${property.name};
}
