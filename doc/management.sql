/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : 127.0.0.1:3306
 Source Schema         : management

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 14/03/2020 21:43:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `area_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区名称',
  `area_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区编码',
  `area_level` int(11) NOT NULL COMMENT '地区等级(0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村)',
  `parent_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父地区编码',
  `longitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '纬度',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `sort_num` int(11) NULL DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_sysarea_id`(`id`) USING BTREE,
  UNIQUE INDEX `inx_sysarea_areacode`(`area_code`) USING BTREE,
  INDEX `inx_sysarea_areaname`(`area_name`) USING BTREE,
  INDEX `inx_sysarea_parentcode`(`parent_code`) USING BTREE,
  INDEX `inx_sysarea_level`(`area_level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地区表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES (1, '河南省', '410000000001', 1, '41', 113.628962, 34.757272, 'admin', '2020-03-07 15:07:00', 0);
INSERT INTO `sys_area` VALUES (335383212932988928, '郑州市', '410100000000', 3, '410000000001', 113.628960, 34.757270, 'admin', '2020-03-07 15:08:10', 1);

-- ----------------------------
-- Table structure for sys_modulelist
-- ----------------------------
DROP TABLE IF EXISTS `sys_modulelist`;
CREATE TABLE `sys_modulelist`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `pid` bigint(20) NOT NULL COMMENT '父级菜单ID',
  `module_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `module_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `module_icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标（样式）',
  `module_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单链接地址',
  `module_target` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）',
  `module_type` int(11) NOT NULL COMMENT '菜单还是功能点，1菜单，0功能点',
  `module_status` int(11) NOT NULL COMMENT '菜单状态，1启用，0禁用',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sysmodullist_id`(`id`) USING BTREE,
  UNIQUE INDEX `idx_sysmodullist_code`(`module_code`) USING BTREE,
  INDEX `idx_sysmodullist_pid`(`pid`) USING BTREE,
  INDEX `idx_sysmodullist_name`(`module_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_operatetype
-- ----------------------------
DROP TABLE IF EXISTS `sys_operatetype`;
CREATE TABLE `sys_operatetype`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `operate_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `operate_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `operate_describe` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_operate_id`(`id`) USING BTREE,
  UNIQUE INDEX `inx_operate_code`(`operate_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限表\r\n例如：ADMIN_AUTH（管理员权限），INSERT_AUTH（数据添加权限）\r\n' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_operatetype
-- ----------------------------
INSERT INTO `sys_operatetype` VALUES (1, 'INSERT_AUTH', '数据添加权限', '数据添加权限', 'admin', '2020-03-07 15:34:23');
INSERT INTO `sys_operatetype` VALUES (2, 'ADMIN_AUTH', '管理员权限', '管理员权限', 'admin', '2020-03-07 21:53:40');
INSERT INTO `sys_operatetype` VALUES (3, 'UPDATE_AUTH', '数据修改权限', '数据修改权限', 'admin', '2020-03-14 15:56:50');
INSERT INTO `sys_operatetype` VALUES (4, 'DELETE_AUTH', '数据删除权限', '数据删除权限', 'admin', '2020-03-14 15:57:48');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `area_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联地区表',
  `org_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构名称',
  `org_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构编码',
  `parent_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父级机构编码',
  `industryid` bigint(20) NULL DEFAULT NULL COMMENT '关联行业表（备用）',
  `org_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构地址',
  `org_logo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构logo',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `sort_num` int(11) NULL DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_sysorg_orgid`(`id`) USING BTREE,
  UNIQUE INDEX `inx_sysorg_orgcode`(`org_code`) USING BTREE,
  INDEX `inx_sysorg_parentcode`(`parent_code`) USING BTREE,
  INDEX `inx_sysorg_orgname`(`org_name`) USING BTREE,
  INDEX `FK_Reference_area_org`(`area_code`) USING BTREE,
  CONSTRAINT `FK_Reference_area_org` FOREIGN KEY (`area_code`) REFERENCES `sys_area` (`area_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '机构表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (1, '410000000001', '河南省气象局', '41000041600000', '0', 416, NULL, NULL, 'admin', '2020-03-07 15:09:41', 0);
INSERT INTO `sys_org` VALUES (335386239865716736, '410000000001', '服务中心', '41000041601000', '41000041600000', 416, NULL, NULL, 'admin', '2020-03-07 15:11:46', 1);

-- ----------------------------
-- Table structure for sys_org_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_role`;
CREATE TABLE `sys_org_role`  (
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`org_id`, `role_id`) USING BTREE,
  INDEX `FK_Reference_role_org`(`role_id`) USING BTREE,
  CONSTRAINT `FK_Reference_org_role` FOREIGN KEY (`org_id`) REFERENCES `sys_org` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_role_org` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '机构角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `role_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_attr` int(11) NOT NULL COMMENT '角色属性(0 共享，1 私有)',
  `org_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构编码（role_attr为1时该字段不能为空）',
  `area_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联地区表地区编码',
  `role_status` int(11) NOT NULL COMMENT '状态，1启用，0禁用',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `sort_num` int(11) NULL DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_sysrole_id`(`id`) USING BTREE,
  UNIQUE INDEX `inx_sysrole_code`(`role_code`) USING BTREE,
  INDEX `inx_sysrole_status`(`role_status`) USING BTREE,
  INDEX `inx_sysrole_name`(`role_name`) USING BTREE,
  INDEX `inx_sysrole_areacode`(`area_code`) USING BTREE,
  CONSTRAINT `FK_Reference_area_role` FOREIGN KEY (`area_code`) REFERENCES `sys_area` (`area_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '管理员', 0, NULL, '410000000001', 1, 'admin', '2020-03-07 15:36:42', 0);

-- ----------------------------
-- Table structure for sys_role_modulelist
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_modulelist`;
CREATE TABLE `sys_role_modulelist`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `modulelist_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `modulelist_id`) USING BTREE,
  INDEX `FK_Reference_modulelist_role`(`modulelist_id`) USING BTREE,
  CONSTRAINT `FK_Reference_modulelist_role` FOREIGN KEY (`modulelist_id`) REFERENCES `sys_modulelist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_role_modulelist` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_operatetype
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_operatetype`;
CREATE TABLE `sys_role_operatetype`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `operateType_id` bigint(20) NOT NULL COMMENT '功能操作表ID',
  PRIMARY KEY (`role_id`, `operateType_id`) USING BTREE,
  INDEX `FK_Reference_operateType_role`(`operateType_id`) USING BTREE,
  CONSTRAINT `FK_Reference_operateType_role` FOREIGN KEY (`operateType_id`) REFERENCES `sys_operatetype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_role_operateType` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和系统权限关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_operatetype
-- ----------------------------
INSERT INTO `sys_role_operatetype` VALUES (1, 1);
INSERT INTO `sys_role_operatetype` VALUES (1, 2);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `login_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `user_gender` int(11) NOT NULL COMMENT '性别(0 男，1 女)',
  `phone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话号码',
  `user_email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `org_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联机构表机构编码',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `user_status` int(11) NOT NULL DEFAULT 0 COMMENT '用户状态(0 正常，2 禁用， 3 过期， 4 锁定)',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_sysuser_id`(`id`) USING BTREE,
  UNIQUE INDEX `inx_sysuser_loginname`(`login_name`) USING BTREE,
  UNIQUE INDEX `inx_sysuser_phonenumber`(`phone_number`) USING BTREE,
  INDEX `inx_sysuser_orgcode`(`org_code`) USING BTREE,
  INDEX `inx_sysuser_password`(`password`) USING BTREE,
  CONSTRAINT `FK_Reference_org_user` FOREIGN KEY (`org_code`) REFERENCES `sys_org` (`org_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$Jw923tUCmRkkvZ/tv2YdYO3UKLN934VHz1ssADyxyPSM43sUWeAR6', '管理员', 1, '18037570119', '1540247870@qq.com', '41000041601000', '2020-03-07 15:13:43', '192.168.0.135', 0, 'admin', '2020-03-07 15:13:59');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户表id',
  `role_id` bigint(20) NOT NULL COMMENT '角色表id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FK_Reference_role_user`(`role_id`) USING BTREE,
  CONSTRAINT `FK_Reference_role_user` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_user_role` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
