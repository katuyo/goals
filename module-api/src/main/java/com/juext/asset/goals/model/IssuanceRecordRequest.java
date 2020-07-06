package com.juext.shop.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.featx.spec.model.Record;


/**
 * @author Excepts
 * @since 2020/4/11 22:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IssuanceRecordRequest extends Record {

    private static final long serialVersionUID = 3542473394788128677L;

    private ${property.type} ${property.name};
}
