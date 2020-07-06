package com.juext.shop.base.model;

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
public class IssuancePageQueryRequest extends PageRequest {

    private static final long serialVersionUID = 564823245124563273L;

    private ${property.type} ${property.name};
}
