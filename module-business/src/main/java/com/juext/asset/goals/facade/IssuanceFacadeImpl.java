package com.juext.asset.goals.facade;

import org.featx.spec.feature.ModelConvert;
import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
import com.juext.asset.goals.model.IssuanceInfo;
import com.juext.asset.goals.model.IssuanceItem;
import com.juext.asset.goals.model.IssuancePageQueryRequest;
import com.juext.asset.goals.model.IssuanceSaveRequest;
import com.juext.asset.goals.storage.entity.IssuanceEntity;
import com.juext.asset.goals.storage.query.IssuanceCriteria;
import com.juext.asset.goals.storage.service.IssuanceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Excepts
 * @since 2020/4/13 0:09
 */
@Component
public class IssuanceFacadeImpl implements IssuanceFacade {

    @Resource
    private IssuanceService IssuanceService;

    @Resource
    private ModelConvert modelConvert;

    @Override
    public Coded save(IssuanceSaveRequest saveRequest) {
        final IssuanceEntity IssuanceEntity = modelConvert.convert(saveRequest, IssuanceEntity.class);
        IssuanceService.save(IssuanceEntity);
        return IssuanceEntity::getCode;
    }

    @Override
    public Coded update(IssuanceSaveRequest saveRequest) {
        final IssuanceEntity IssuanceEntity = modelConvert.convert(saveRequest, IssuanceEntity.class);
        IssuanceService.update(IssuanceEntity);
        return IssuanceEntity::getCode;
    }

    @Override
    public void delete(String IssuanceCode) {
        IssuanceService.delete(IssuanceCode);
    }

    @Override
    public IssuanceInfo getByCode(String code) {
        return Optional.of(IssuanceService.findOne(code))
                .map(entity -> modelConvert.convert(entity, IssuanceInfo.class))
                .orElse(null);
    }

    @Override
    public List<IssuanceInfo> listByCodes(List<String> codes) {
        return Optional.of(IssuanceService.listByCodes(codes))
                .filter(CollectionUtil::isNotEmpty)
                .map(l -> l.stream().map(e->modelConvert.convert(e, IssuanceInfo.class))
                                    .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public QuerySection<IssuanceItem> page(IssuancePageQueryRequest pageQueryRequest) {
        return IssuanceService
                .page(modelConvert.convert(pageQueryRequest, IssuanceCriteria.class), pageQueryRequest)
                .convertAsList(e->modelConvert.convert(e, IssuanceItem.class));
    }
}
