package com.juext.asset.goals.service;

import com.juext.asset.goals.entity.TransferEntity;
import com.juext.asset.goals.query.TransferCriteria;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:57
 */
public interface TransferService {

    void save(TransferEntity TransferEntity);

    void delete(String code);

    TransferEntity findOne(String code);

    List<TransferEntity> listByCodes(List<String> codes);

    QuerySection<TransferEntity> page(TransferCriteria criteria, PageRequest pageRequest);
}
