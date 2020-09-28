package com.southwind.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.southwind.realm.AccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //创建一个对象然后把它丢到IOC容器里面(这个整个Springboot体系才可以接收到它)
    @Bean
    public AccountRealm accountRealm(){
        return new AccountRealm();
    }

    //我们需要把自定义的Realm注入到安全管理器里面
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(accountRealm);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //权限设置
        Map<String,String> map=new Hashtable<>();
        //当我们访问/main的时候必须认证
        map.put("/main","authc");
        //访问/manage必须具有manage的权限，这里我们是可以赋予多个权限的，它是一个数据
        map.put("/manage","perms[manage]");
        //访问/administrator必须赋予administrator的角色
        map.put("/administrator","roles[administrator]");
        //集合创建好以后factoryBean里面
        factoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        factoryBean.setLoginUrl("/login");
        //设置未授权页面
        factoryBean.setUnauthorizedUrl("/unauth");
        return factoryBean;
    }
    @Bean
    public ShiroDialect shiroDialect(){  //shiroDialect方言
        return new ShiroDialect();
    }

}
