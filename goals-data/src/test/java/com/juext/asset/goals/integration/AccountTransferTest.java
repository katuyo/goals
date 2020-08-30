package com.juext.asset.goals.integration;

import com.google.common.collect.Lists;
import com.juext.asset.goals.DataSourceConfiguration;
import com.juext.asset.goals.entity.AccountEntity;
import com.juext.asset.goals.param.AccountTransferParam;
import com.juext.asset.goals.service.AccountService;
import com.juext.asset.goals.service.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Excepts
 * @since 2020/8/30 16:46
 */
@DisplayName("Integration: Account Transfer")
@MybatisTest
@ContextConfiguration(classes = {DataSourceConfiguration.class, AccountServiceImpl.class})
public class AccountTransferTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(10, Thread::new);
    @Resource
    private AccountService accountService;

    @ParameterizedTest
    @ValueSource(strings = {"a|18.932, b|12.999, c|10;a-1.333-b,a-5.8-c,c-6.123-b,b-10.203-c;a|11.799, b|10.252, c|19.88"})
    void testAccountTransfer(String testString) {
        String[] tests = testString.split(";");
        String[] accounts = tests[0].split(",");
        String[] tasks = tests[1].split(",");
        String[] expects = tests[2].split(",");
        final Map<String, String> commentCodes = Arrays.stream(accounts).map(this::toAccount)
                .peek(a -> accountService.save(a))
                .collect(Collectors.toMap(AccountEntity::getComment, AccountEntity::getCode));
        Arrays.stream(tasks).map(task -> this.toTransferParam(task, commentCodes))
                .forEach(transferParam -> accountService.transfer(transferParam));
//                .map(param -> executor.submit(()->accountService.transfer(param)))
//                .forEach(this::futureGet);

        Map<String, Double> expectInventory = Arrays.stream(expects).map(this::toAccount)
                .collect(Collectors.toMap(AccountEntity::getComment, AccountEntity::getInventory));
        accountService.listByCodes(Lists.newArrayList(commentCodes.values()))
                .forEach(ae -> assertEquals(expectInventory.get(ae.getComment()), ae.getInventory()));
    }

    private AccountEntity toAccount(String s) {
        String[] props = s.split("\\|");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setComment(props[0].trim());
        accountEntity.setInventory(Double.parseDouble(props[1]));
        return accountEntity;
    }

    private AccountTransferParam toTransferParam(String s, final Map<String, String> commentCodeMap) {
        String[] params = s.split("-");
        return AccountTransferParam.newInstance()
                .fromAccountCode(commentCodeMap.get(params[0].trim()))
                .toAccountCode(commentCodeMap.get(params[2].trim()))
                .amount(Double.parseDouble(params[1].trim()));
    }

    private <T> T futureGet(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
