create datebase xyy;
use xyy;

create table t_admin
(
    id       int auto_increment comment 'id'
        primary key,
    name     varchar(20)  null comment '姓名',
    username varchar(20)  null comment '用户名',
    password varchar(250) null comment '密码',
    role_id  int          null comment '角色id',
    email    varchar(20)  null,
    constraint t_admin_username_uindex
        unique (username)
);

create table t_admin_room
(
    id       int auto_increment comment 'id'
        primary key,
    admin_id int null comment '管理员id',
    room_id  int null comment '自习室id'
);

create table t_data
(
    id      int auto_increment comment 'id'
        primary key,
    content text     null comment '公告内容',
    time    datetime null comment '发布公告时间'
);

create table t_menu
(
    id        int auto_increment comment 'id'
        primary key,
    url       varchar(64) charset utf8mb3 null comment 'url',
    path      varchar(64) charset utf8mb3 null comment 'path',
    component varchar(64) charset utf8mb3 null comment '组件',
    name      varchar(64) charset utf8mb3 null comment '菜单名',
    icon_cls  varchar(64) charset utf8mb3 null comment '图标',
    parent_id int                         null comment '父id',
    constraint menu_ibfk_1
        foreign key (parent_id) references xyy.t_menu (id)
)
    collate = utf8mb4_bin;


create table t_role
(
    id   int auto_increment comment 'id'
        primary key,
    name varchar(20) null comment '角色名称'
);

create table t_role_menu
(
    id      int auto_increment comment 'id'
        primary key,
    role_id int null comment '角色id',
    menu_id int null comment '菜单id'
);

create table t_room
(
    id          int auto_increment comment 'id'
        primary key,
    name        varchar(20)          null comment '自习室名称',
    date_time   datetime             null comment '起始时间',
    expire_time datetime             null comment '过期时间',
    parent_id   int                  null,
    disabled    tinyint(1) default 1 null comment '座位预约'
);

insert into t_admin (id, name, username, password, role_id, email)
values  (1, '系统管理员', 'admin', '$2a$10$X4bBHAZTxcMfWS3XGXdHfObMtfgikhirbNGNNk1VhmADq7pxU1QwS', 3, '2979554858@qq.com'),
        (2, '测试', '1008519070320', '$2a$10$InW6LDnKgyUwylHl6VQOlOR5KMmVwZsmvauLVEYPZtBJr.vwOpqJe', 1, 'afasfafcaesfeaf'),
        (3, '王老师', '1008509', '$2a$10$MSjL0n49W6J6ykdSqCVSyOY/6uAafBni9dNfKaWz1v86dhg1Qc1lu', 2, '2979554858@.com'),
        (6, '李老师', '10085', '$2a$10$MSjL0n49W6J6ykdSqCVSyOY/6uAafBni9dNfKaWz1v86dhg1Qc1lu', 2, '123456@qq.com'),
        (7, '李老师', '100851907', '$2a$10$MSjL0n49W6J6ykdSqCVSyOY/6uAafBni9dNfKaWz1v86dhg1Qc1lu', 2, '2979554858@qq.com'),
        (12, '王同学测试', '15094458358', '$2a$10$MSjL0n49W6J6ykdSqCVSyOY/6uAafBni9dNfKaWz1v86dhg1Qc1lu', 1, '2979554858@qq.com'),
        (13, 'wsdd', '123456', '$2a$10$/uH0nYFC9FKLaVIptwv9Y.kxHcH5IafYhZodcGOE7AwXBjLH2jI9W', 1, '2979554858@qq.com'),
        (14, 'wsddd1', '1008511', '$2a$10$nMWYQyT2NvJiBCiM/jMxaOBFPlFsVbqAaRbJ/P80KIoQ4r7PoOuCC', 1, '2979554858@qq.com'),
        (15, '10001', '10001', '$2a$10$G8faRjvEqNsjda8kuG4XTefwz0YeN9ecuhyuqR835B7m03drebXAS', 1, '2979554858@qq.com'),
        (16, 'wdd', '1008533', '$2a$10$U7ygWGPUBEzSWfQRzGdv2.6FcGX0bxUnrR0p3a5XJ2Ph5g1ZP9CZG', 1, '2979554858@qq.com'),
        (17, '王小', '123456789', '$2a$10$C9H4zKLZIo/AKpLWDZKmsOGtlA3uFSq0S56Cl2Jnnh5q2k.rahCw2', 1, '2979554858@qq.com'),
        (19, '测试老师', '2008', '$2a$10$1UyA1SNkzp9x9OKMazRc8.cWWzV08RiCNTy9L7nUUkX0zw3g5gj5O', 2, '2979554858@qq.com'),
        (21, 'ads测是', 'sadsa', '$2a$10$hp6.H1poBXFJE7uKBRR10erNOhzqjKV0TFULZTteSNnVT/vuGwBpa', 1, 'sadsa'),
        (22, 'asdsadsa测', 'asdasdsa', '$2a$10$PZ7EcZlZLLiCVOw3/3zkG.D6kX/nW5lvM2B5DGnUYcJVfWXXgoX86', 1, 'asdsada2979'),
        (24, '123456', '10058', '$2a$10$fGDMjvyP4jTe9SkYfNBz.eQxDbBBQneWau4wphdB/7tT6LkTfrYgu', 1, '2979554858@qq.com');


insert into t_admin_room (id, admin_id, room_id)
values  (5, 7, 4),
        (6, 7, 5),
        (7, 7, 6),
        (13, 13, 8),
        (17, 16, 15),
        (25, 15, 56),
        (26, 17, 34),
        (30, 12, 21),
        (39, 7, 1),
        (40, 6, 2),
        (41, 19, 3),
        (49, 24, 38);


insert into t_data (id, content, time)
values  (1, '<br>1、注意保持室内安静，不相互耳语，进馆时请将手机调为振动或关机，离馆时放好桌椅。
<br>2、禁止任何占座行为，自觉做到“一人一座”，不要为他人占座。当天暂时离开自习室，请在桌面醒目位置留下返回时间，以方便其他同学择座自习。 
<br>3、自习座位应随来随用，个人物品请随身带走，如有丢失， 责任自负。
<br>4、为加强自习室管理，特设管理员专座，请读者不要占用。
<br>5、因占座问题发生纠纷，责任由原座位使用者承担。屡次占座要给予停止借书权的处理。 
<br>6、爱护自习室的设备，不得私自使用电器，自觉维护室内卫生。自习室内禁止吃零食，不许随地吐痰。自习区及楼道内禁止吸烟。', '2022-11-02 22:46:10');

insert into t_menu (id, url, path, component, name, icon_cls, parent_id)
values  (1, '/', null, null, '所有', null, null),
        (2, '/', '/home', 'Home', '自习公告', 'el-icon-edit-outline', 1),
        (3, '/', '/home', 'Home', '预约教室', 'el-icon-office-building', 1),
        (4, '/', '/home', 'Home', '我的预约', 'el-icon-reading', 1),
        (5, '/', '/home', 'Home', '教室管理', 'el-icon-school', 1),
        (6, '/', '/home', 'Home', '系统管理', 'el-icon-setting', 1),
        (23, '/system/basic/**', '/sys/basic', 'SysBasic', '用户信息管理', null, 6),
        (24, '/system/config/**', '/sys/cfg', 'SysCfg', '教室信息管理', null, 6),
        (25, '/system/log/**', '/sys/log', 'SysLog', '操作日志管理', null, 6),
        (26, '/system/admin/**', '/sys/admin', 'SysAdmin', '操作员管理', null, 6),
        (27, '/system/data/**', '/sys/data', 'SysData', '公告管理', null, 6),
        (28, '/system/init/**', '/sys/init', 'SysInit', '初始化数据库', null, 6),
        (30, '/study/data/**', '/sdy/data', 'SdyData', '公告信息', null, 2),
        (31, '/classroom/config/**', '/com/cfg', 'ComCfg', '教室自习信息', null, 5),
        (32, '/reserve/classroom/**', '/rve/com', 'RveCom', '自习室预约', null, 3),
        (33, '/book/classroom/**', '/bok/com', 'BokCom', '我的自习室', null, 4);



insert into t_role (id, name)
values  (1, 'student'),
        (2, 'teacher'),
        (3, 'admin');



insert into t_role_menu (id, role_id, menu_id)
values  (1, 1, 2),
        (2, 1, 3),
        (3, 1, 4),
        (4, 2, 2),
        (7, 2, 5),
        (8, 3, 2),
        (9, 3, 5),
        (10, 3, 6),
        (12, 3, 23),
        (13, 3, 24),
        (14, 3, 25),
        (15, 3, 26),
        (16, 3, 27),
        (17, 3, 28),
        (18, 3, 30),
        (19, 3, 31),
        (20, 1, 32),
        (21, 1, 33),
        (24, 1, 30),
        (25, 2, 30),
        (26, 2, 31);



insert into t_room (id, name, date_time, expire_time, parent_id, disabled)
values  (1, '河北水利电力学院A栋1F103', '2022-11-07 16:26:44', '2022-11-08 00:26:44', 0, 1),
        (2, '河北水利电力学院A栋2F203', '2022-11-07 16:26:57', '2022-11-08 00:26:57', 0, 1),
        (3, '河北水利电力学院A栋3F303', '2022-11-07 16:27:04', '2022-11-08 00:27:04', 0, 1),
        (4, '河北水利电力学院B栋1F103', '2022-11-06 14:15:55', '2022-11-06 14:15:52', 0, 1),
        (5, '河北水利电力学院B栋2F203', '2022-11-06 14:15:58', '2022-11-06 14:15:56', 0, 1),
        (6, ' 河北水利电力学院B栋3F303', '2022-11-06 14:15:59', '2022-11-06 14:16:00', 0, 1),
        (7, 'A101', '2022-11-02 15:53:02', null, 1, 0),
        (8, 'A102', null, null, 1, 0),
        (9, 'A103', null, null, 1, 0),
        (10, 'A104', null, null, 1, 0),
        (11, 'A105', '2022-11-06 09:47:10', null, 1, 0),
        (12, 'A106', null, null, 1, 0),
        (13, 'A107', null, null, 1, 0),
        (14, 'A108', null, null, 1, 0),
        (15, 'A109', '2022-11-01 13:03:08', null, 1, 1),
        (16, 'A110', null, null, 1, 0),
        (17, 'A201', null, null, 2, 0),
        (18, 'A202', null, null, 2, 0),
        (19, 'A203', null, null, 2, 0),
        (20, 'A204', null, null, 2, 0),
        (21, 'A205', '2022-11-05 11:00:32', null, 2, 1),
        (22, 'A206', null, null, 2, 0),
        (23, 'A207', null, null, 2, 0),
        (24, 'A208', null, null, 2, 0),
        (25, 'A209', '2022-11-01 11:38:29', null, 2, 0),
        (26, 'A210', null, null, 2, 0),
        (27, 'A301', null, null, 3, 0),
        (28, 'A302', null, null, 3, 0),
        (29, 'A303', null, null, 3, 0),
        (30, 'A304', null, null, 3, 0),
        (31, 'A305', null, null, 3, 0),
        (32, 'A306', null, null, 3, 0),
        (33, 'A307', null, null, 3, 0),
        (34, 'A308', '2022-11-02 22:16:17', null, 3, 1),
        (35, 'A309', null, null, 3, 0),
        (36, 'A310', null, null, 3, 0),
        (37, 'B101', null, null, 4, 0),
        (38, 'B102', '2022-11-07 22:28:02', null, 4, 1),
        (39, 'B103', null, null, 4, 0),
        (40, 'B104', null, null, 4, 0),
        (41, 'B105', '2022-11-06 09:57:17', null, 4, 0),
        (42, 'B106', null, null, 4, 0),
        (43, 'B107', null, null, 4, 0),
        (44, 'B108', null, null, 4, 0),
        (45, 'B109', '2022-11-01 15:43:43', null, 4, 0),
        (46, 'B110', null, null, 4, 0),
        (47, 'B201', null, null, 5, 0),
        (48, 'B202', null, null, 5, 0),
        (49, 'B203', null, null, 5, 0),
        (50, 'B204', '2022-11-01 16:38:15', null, 5, 0),
        (51, 'B205', '2022-11-05 10:59:16', null, 5, 0),
        (52, 'B206', null, null, 5, 0),
        (53, 'B207', null, null, 5, 0),
        (54, 'B208', '2022-11-01 15:43:31', null, 5, 0),
        (55, 'B209', '2022-11-01 16:21:22', null, 5, 0),
        (56, 'B210', '2022-11-02 21:59:31', null, 5, 1),
        (57, 'B301', null, null, 6, 0),
        (58, 'B302', null, null, 6, 0),
        (59, 'B303', null, null, 6, 0),
        (60, 'B304', null, null, 6, 0),
        (61, 'B305', '2022-11-02 23:02:26', null, 6, 1),
        (62, 'B306', null, null, 6, 0),
        (63, 'B307', null, null, 6, 0),
        (64, 'B308', null, null, 6, 0),
        (65, 'B309', null, null, 6, 0),
        (66, 'B310', null, null, 6, 0),
        (67, 'A111', null, null, 1, 0),
        (68, 'A112', null, null, 1, 0);