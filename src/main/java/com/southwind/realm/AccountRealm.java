package com.southwind.realm;

import com.southwind.entity.Account;
import com.southwind.service.AccoutService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


public class AccountRealm  extends AuthorizingRealm {
    @Autowired
    private AccoutService accoutService;


    //角色的权限信息集合(授权的判断全部写在这里，登录之后是否具有某些权限交给它来搞)
    //用来设置角色和权限的
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的用户信息
        Subject subject= SecurityUtils.getSubject();
        Account account=(Account) subject.getPrincipal();
        //设置角色(这里用set集合，因为set集合可以防止重复)
//        Set<String> roles=new HashSet<>();
        Set<String> roles=new HashSet<>();
        roles.add(account.getRole());
        //用来存放角色的集合
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(roles);

        //设置权限
        info.addStringPermission(account.getPerms());
        return info;
    }
    //用户的角色信息集合(做认证的，登录这里来搞)
    //用来设置用户的
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将前端传过来的token进行类型强转
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        //从数据库查传过来的Username的数据
        Account account=accoutService.findByUsername(token.getUsername());
        if(account !=null){
            //这样就获取到了数据库的密码，它会自动和token的密码进行验证
            return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
        }
        return null;
    }
}
