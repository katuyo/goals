package com.juext.asset.goals.storage.service;

import com.juext.asset.goals.storage.mapper.AccountMapper;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author Excepts
 * @since 2020/7/7 0:34
 */
@DisplayName("Service: Account")
public class AccountServiceTest {
    @Mock
    private AccountService accountService;
    @Spy
    private AccountMapper accountMapper;
}
