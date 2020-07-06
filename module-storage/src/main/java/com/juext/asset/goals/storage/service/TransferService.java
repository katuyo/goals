package com.juext.shop.base.storage.service;

import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import com.juext.shop.base.storage.entity.TransferEntity;
import com.juext.shop.base.storage.query.TransferCriteria;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:57
 */
public interface TransferService {

    void save(TransferEntity TransferEntity);

    void update(TransferEntity TransferEntity);

    void delete(String code);

    TransferEntity findOne(String code);

    List<TransferEntity> listByCodes(List<String> codes);

    QuerySection<TransferEntity> page(TransferCriteria criteria, PageRequest pageRequest);
}
