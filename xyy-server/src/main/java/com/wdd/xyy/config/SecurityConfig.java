package com.wdd.xyy.config;

import com.alibaba.fastjson.JSON;
import com.wdd.xyy.config.component.*;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: wdd
 * @date: 2022/10/22 18:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminService adminService;


    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //        auth.userDetailsService( adminService::getAdminByUserName);
        //        auth.userDetailsService(username -> adminService.getAdminByUserName(username));
        // 上面两种格式的写法
        // 获得用户信息 封装成UserDetails
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

    }


    /**
     * 忽略接口
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
//                "/login",
                "/register",
                "/css/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/swagger-ui.html",
                "/captcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf() //不适用csrf
                .disable();
        http.formLogin()
                .loginProcessingUrl("/defaultLogin")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    RespBean ok = RespBean.success("登录成功");
                    response.getWriter().write(JSON.toJSONString(ok));
                })
                .failureHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("登录失败");
                });
        http.headers()
                .cacheControl();


        http.authorizeRequests()
                .antMatchers("/login").anonymous();
        http.logout().logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                                .logoutSuccessHandler((request, response, authentication) -> {
                                })
                // 使该用户的HttpSession失效
                .invalidateHttpSession(true);


//        http.sessionManagement()    //基于cookie 不使用session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .headers()
//                .cacheControl();
        // 动态权限认证
        http.authorizeRequests()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                });



        // 增加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return (username) -> {
            Admin admin = adminService.getAdminByUserName(username);
            // 判断
            if (admin != null) {
                // 设置角色
                admin.setRoles(adminService.getRoles(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("用户名不存在");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}

