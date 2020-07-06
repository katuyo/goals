package com.juext.asset.goals.facade;

import org.featx.spec.feature.ModelConvert;
import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
import com.juext.asset.goals.model.TransferInfo;
import com.juext.asset.goals.model.TransferItem;
import com.juext.asset.goals.model.TransferPageQueryRequest;
import com.juext.asset.goals.model.TransferSaveRequest;
import com.juext.asset.goals.storage.entity.TransferEntity;
import com.juext.asset.goals.storage.query.TransferCriteria;
import com.juext.asset.goals.storage.service.TransferService;
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
public class TransferFacadeImpl implements TransferFacade {

    @Resource
    private TransferService TransferService;

    @Resource
    private ModelConvert modelConvert;

    @Override
    public Coded save(TransferSaveRequest saveRequest) {
        final TransferEntity TransferEntity = modelConvert.convert(saveRequest, TransferEntity.class);
        TransferService.save(TransferEntity);
        return TransferEntity::getCode;
    }

    @Override
    public Coded update(TransferSaveRequest saveRequest) {
        final TransferEntity TransferEntity = modelConvert.convert(saveRequest, TransferEntity.class);
        TransferService.update(TransferEntity);
        return TransferEntity::getCode;
    }

    @Override
    public void delete(String TransferCode) {
        TransferService.delete(TransferCode);
    }

    @Override
    public TransferInfo getByCode(String code) {
        return Optional.of(TransferService.findOne(code))
                .map(entity -> modelConvert.convert(entity, TransferInfo.class))
                .orElse(null);
    }

    @Override
    public List<TransferInfo> listByCodes(List<String> codes) {
        return Optional.of(TransferService.listByCodes(codes))
                .filter(CollectionUtil::isNotEmpty)
                .map(l -> l.stream().map(e->modelConvert.convert(e, TransferInfo.class))
                                    .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public QuerySection<TransferItem> page(TransferPageQueryRequest pageQueryRequest) {
        return TransferService
                .page(modelConvert.convert(pageQueryRequest, TransferCriteria.class), pageQueryRequest)
                .convertAsList(e->modelConvert.convert(e, TransferItem.class));
    }
}
