package com.wdd;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdd.xyy.XyyApplication;
import com.wdd.xyy.mapper.AdminMapper;
import com.wdd.xyy.mapper.MenuMapper;
import com.wdd.xyy.mapper.RoleMapper;
import com.wdd.xyy.mapper.RoomMapper;
import com.wdd.xyy.pojo.*;
import com.wdd.xyy.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;

@SpringBootTest(classes = XyyApplication.class)
@Slf4j
public class AppTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataService dataService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test2() {
        Admin admin = adminMapper.getAdminByUserName("10085");

        log.info(JSON.toJSONString(admin));
    }

    @Test
    public void test() {
        Admin admin = adminService.getAdminByUserName("admin");
        log.info("admin:{}", JSON.toJSONString(admin));
    }

    @Test
    public void test3() {
        List<Menu> menus = menuMapper.getMenusByAdminId(1);
        for (Menu menu : menus) {
            log.info("menu:{}", JSON.toJSONString(menu));
        }
    }

    @Test
    public void test4() {
        List<Menu> menuWithRole = menuMapper.getMenusByAdminId(3);
        for (Menu menu : menuWithRole) {
            log.info("menu:{}", JSON.toJSONString(menu));
        }
    }

    @Test
    public void test5() {
        List<Role> roles = adminMapper.getRoles(1);
        for (Role role : roles) {
            log.info("role:{}", JSON.toJSONString(role));
        }
    }
    @Test
    public void test6() {
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Test
    public void test7(){
        String userName = adminMapper.getUserNameByUserName("10085");
        log.info("userName:"+userName);
    }

    @Test
    public void test8(){
        Data one = dataService.getOne(new QueryWrapper<Data>().allEq(null));
        log.info("one:"+one);
    }

    @Test
    public void test9(){
        System.out.println(dataService.getById(1));
    }

    @Test
    public void test10(){
        List<Menu> menus = menuMapper.getMenusWithRoles();
        for (Menu menu : menus) {
            log.info("menu:"+menu);
        }
    }

    @Test
    public void test11(){
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("announcement", "1212121212");
//        JSONObject jsonObject = new JSONObject(map);
//        log.info("{}",jsonObject);


        JSONObject object = JSON.parseObject("{\"announcement\":\"自习室规定：\n" +
                "\n" +
                "<br>1、注意保持室内安静，不相互耳语，进馆时请将手机调为振动或关机，离馆时放好桌椅。\n" +
                "\n" +
                "<br>2、禁止任何占座行为，自觉做到“一人一座”，不要为他人占座。当天暂时离开自习室，请在桌面醒目位置留下返回时间，以方便其他同学择座自习。\n" +
                "\n" +
                "<br>3、自习座位应随来随用，个人物品请随身带走，如有丢失， 责任自负。\n" +
                "\n" +
                "<br>4、为加强自习室管理，特设管理员专座，请读者不要占用。\n" +
                "\n" +
                "<br>5、因占座问题发生纠纷，责任由原座位使用者承担。屡次占座要给予停止借书权的处理。\"}");
        object.put("announcement", "abcd");
        log.info("{}", object);
    }

    @Test
    public void test12(){
        List<?> rooms = roomService.getRooms();
        System.out.println(JSON.toJSONString(rooms));
    }

    @Test
    public void test13(){
        List<Object> roomTeacher = roomService.getRoomTeacher();
        System.out.println(JSON.toJSONString(roomTeacher));
    }

    @Test
    public void test14(){
        Room dataInfo = roomService.getDataInfo(15);
        System.out.println(JSON.toJSONString(dataInfo));
    }

    @Test
    public void test15(){
        List<Object> teacherConfig = roomService.getTeacherConfig(3);
        System.out.println(JSON.toJSONString(teacherConfig));

    }

    @Test
    public void test16(){
        List<Room> studentData = roomMapper.getStudentData(9, 12);
        System.out.println(JSON.toJSONString(studentData));

    }

    @Test
    public  void test17(){
        Admin admin = new Admin();
        admin.setRoleId(1);
        // 开启分页
        Page<Admin> page = new Page<>(0, 10);

        IPage<Admin> allAdmins = adminMapper.getAllAdmins(page, admin);

    }

    @Test
    public void test18(){
        List<Room> dataInfoTeacher = roomMapper.getDataInfoTeacher(3);
        System.out.println(JSON.toJSONString(dataInfoTeacher));
    }

    @Test
    public void test19(){
        List<Room> teacherByRoomId = roomService.getTeacherByRoomId(16);
    }

    @Test
    public void test20(){
        List<Room> childrenId = roomMapper.getChildrenId(6);
        for (Room room : childrenId) {
            System.out.println(room);
        }
    }

    @Test
    public void test21(){
        List<Admin> allTeachers = adminMapper.getAllTeachers();
        for (Admin allTeacher : allTeachers) {
            System.out.println(JSON.toJSONString(allTeacher));
        }
    }
}




