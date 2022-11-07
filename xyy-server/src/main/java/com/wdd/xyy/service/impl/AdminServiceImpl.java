package com.wdd.xyy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.xyy.mapper.AdminMapper;
import com.wdd.xyy.pojo.Admin;
import com.wdd.xyy.pojo.RespBean;
import com.wdd.xyy.pojo.RespPageBean;
import com.wdd.xyy.pojo.Role;
import com.wdd.xyy.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: wdd
 * @date: 2022/10/22 12:30
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 注册账号
     * @param admin
     * @return
     */
    @Override
    public RespBean registerAdmin(Admin admin) {

        String username = admin.getUsername();
        // 判断用户名是否独一
        String columnUsername = adminMapper.getUserNameByUserName(username);
        if (columnUsername ==null){
            // 加密密码
            String password = admin.getPassword();
            String encode = passwordEncoder.encode(password);
            admin.setPassword(encode);
            if ((1==adminMapper.insert(admin))) {
                return RespBean.success("注册成功");
            }
            return RespBean.error("注册失败");
        }
        return RespBean.error("用户名已存在,请重新注册");

    }


    /**
     * 通过用户名获得用户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        Admin admin = adminMapper.getAdminByUserName(username);
        return admin;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        // 获得生成的验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        log.info("captcha:"+captcha);
        // 校验验证码
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证码输入错误，请重新输入");
        }
        // 拿到用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 校验密码
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return RespBean.success("登录成功");

    }

    /**
     * 通过用户id查询角色
     * @param id
     * @return
     */
    @Override
    public List<Role> getRoles(Integer id) {
        return adminMapper.getRoles(id);
    }

    /**
     * 查询所有人的信息
     * @return
     */
    @Override
    public RespPageBean getAllAdmins(Integer currentPage, Integer size, Admin admin) {
        // 开启分页
        Page<Admin> page = new Page<>(currentPage, size);

        IPage<Admin> allAdmins = adminMapper.getAllAdmins(page, admin);
        return new RespPageBean(allAdmins.getTotal(), allAdmins.getRecords());
    }

    /**
     * 获得所有老师
     * @return
     */
    @Override
    public List<Admin> getAllTeachers() {
        return adminMapper.getAllTeachers();
    }


}
