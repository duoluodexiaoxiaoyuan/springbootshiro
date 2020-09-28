package com.southwind.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountMapperTest {
    @Autowired
    private AccountMapper mapper;

    @Test
    void test(){
        //输出findAll的所有内容
        mapper.selectList(null).forEach(System.out::println);
    }

}