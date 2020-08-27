package com.juext.asset.goals.service;

import com.juext.asset.goals.entity.IssuanceEntity;
import com.juext.asset.goals.query.IssuanceCriteria;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:57
 */
public interface IssuanceService {

    void save(IssuanceEntity IssuanceEntity);

    void update(IssuanceEntity IssuanceEntity);

    void delete(String code);

    IssuanceEntity findOne(String code);

    List<IssuanceEntity> listByCodes(List<String> codes);

    QuerySection<IssuanceEntity> page(IssuanceCriteria criteria, PageRequest pageRequest);
}
