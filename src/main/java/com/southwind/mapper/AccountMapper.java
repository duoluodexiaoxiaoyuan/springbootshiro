package com.southwind.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.entity.Account;
import org.springframework.stereotype.Repository;

//泛型只写实体类就可以了
@Repository
public interface AccountMapper extends BaseMapper<Account> {

}
