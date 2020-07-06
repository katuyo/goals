package com.juext.shop.base.storage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.featx.spec.entity.AbstractUnified;

/**
 * @author Excepts
 * @since 2020/4/12 13:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IssuanceEntity extends AbstractUnified<Long> {

    private static final long serialVersionUID = -4441039419344895608L;

    private ${property.type} ${property.name};
}
