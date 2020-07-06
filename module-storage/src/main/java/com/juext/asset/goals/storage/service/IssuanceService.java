package com.juext.asset.goals.storage.service;

import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import com.juext.asset.goals.storage.entity.IssuanceEntity;
import com.juext.asset.goals.storage.query.IssuanceCriteria;

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
