package com.wdd.xyy.config.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author: wdd
 * @date: 2022/10/24 11:28
 */
@Component
@Slf4j
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            // 当前请求需要的角色
            String needRole = configAttribute.getAttribute();
            // 判断角色是否登录即可访问的角色，这里我们把角色设置为ROLE_LOGIN
//            if ("ROLE_LOGIN".equals(needRole)) {
//                // 判断是否登录
//                if (authentication instanceof AnonymousAuthenticationToken) {
//                    throw new AccessDeniedException("尚未登录，请登录！");
//                } else {
//                    return;
//                }
//            }
            // 判断角色是否匹配
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        // 没有匹配上的角色
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
