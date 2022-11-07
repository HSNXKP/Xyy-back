package com.wdd.xyy.config.component;

import com.wdd.xyy.pojo.Menu;
import com.wdd.xyy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.http.FilterInvocationSecurityMetadataSourceParser;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author: wdd
 * @date: 2022/10/24 11:34
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的url
        String requestUrl = ((FilterInvocation)object).getRequestUrl();

        // 获得 角色菜单
        List<Menu> menus = menuService.getMenusWithRoles();
        // 遍历菜单
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                String[] str = menu.getRoles()
                        .stream().map(role -> role.getName()).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        // 没有匹配上的资源，都是登录访问
        return null;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class
                .isAssignableFrom(clazz);
    }
}
