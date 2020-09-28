package com.southwind.service;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccoutnServiceTest {

    @Autowired
    private AccoutService accoutService;

    @Test
    void findByUsername() {
        accoutService.findByUsername("lisi");
        System.out.println(accoutService.findByUsername("lisi"));
        int i=0;
    }
}