package com.juext.asset.goals.controller;

import com.alibaba.fastjson.JSON;
import com.juext.asset.goals.SpringWebTestSuit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Excepts
 * @since 2020/7/7 0:46
 */
@DisplayName("Controller: Account")
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class AccountControllerTest extends SpringWebTestSuit {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testPostAccount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content("{\"accountCode\": \"ACT0001\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(new Object(), JSON.parse(content));
    }
}
