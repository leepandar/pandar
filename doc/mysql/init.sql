/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : pandar

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 19/10/2025 11:10:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `config_id` bigint NOT NULL COMMENT '参数ID',
                              `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '参数名称',
                              `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '参数键名',
                              `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '参数键值',
                              `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
                              `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                              `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                              `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                              `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                              `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                              `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                              `version` int DEFAULT '0' COMMENT '版本号',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES (1, 1833741434936000512, '系统参数-多租户开关', 'system.config.tenant', 'true', 'true开启多租户，false关闭多租户', 1, 0, '2024-09-11 13:36:42.050638', 0, '2024-10-07 18:42:57.798908', b'0', 7);
INSERT INTO `sys_config` VALUES (3, 1833773478936207360, '系统参数-用户头像大小限制(kb)', 'system.config.user.avatar.size', '102400', '用户头像图片的大小，单位是kb', 1, 0, '2024-09-11 15:44:01.932583', 0, '2024-10-07 18:45:32.477747', b'0', 2);
INSERT INTO `sys_config` VALUES (4, 1833773744087523328, '系统参数-登录验证码是否开启', 'system.config.captcha.enable', 'true', '系统登录时的验证码，true打开，false关闭', 1, 0, '2024-09-11 15:45:05.149415', 0, '2024-10-21 21:36:23.000000', b'0', 5);
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `dept_id` bigint NOT NULL COMMENT '部门id',
                            `parent_dept_id` bigint DEFAULT '0' COMMENT '父部门id',
                            `ancestors` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '祖级列表',
                            `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
                            `dept_leader` bigint DEFAULT NULL COMMENT '部门负责人',
                            `dept_sort` int DEFAULT '0' COMMENT '显示顺序',
                            `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
                            `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
                            `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                            `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
                            `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                            `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                            `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                            `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                            `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                            `version` int DEFAULT '0' COMMENT '版本号',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (5, 1677964029214371840, 0, NULL, '胖达', 0, 0, '10086', '123456@qq.com', 1, 0, 0, '2023-07-09 16:52:54.285322', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (6, 1677964231673425920, 1677964029214371840, '1677964029214371840', '北京总部', 0, 0, '10010', '123456@qq.com', 1, 0, 0, '2023-07-09 16:53:42.555301', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (7, 1677964435873116160, 1677964231673425920, '1677964029214371840,1677964231673425920', '基础架构部', 0, 20, '123456', '12121212', 1, 0, 0, '2023-07-09 16:54:31.240378', 0, '2025-10-18 15:29:30.000000', b'0', 5);
INSERT INTO `sys_dept` VALUES (8, 1677964531410972672, 1677964231673425920, '1677964029214371840,1677964231673425920', '产品设计部', 0, 10, '123456', '233223', 1, 0, 0, '2023-07-09 16:54:54.018152', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (9, 1677964682431082496, 1677964231673425920, '1677964029214371840,1677964231673425920', '人力资源部', 0, 0, '456789', '4565346', 1, 0, 0, '2023-07-09 16:55:30.024647', 0, '2025-10-18 15:29:30.000000', b'0', 6);
INSERT INTO `sys_dept` VALUES (10, 1686015040692744192, 1677964231673425920, '1677964029214371840,1677964231673425920', '市场营销部', 0, 30, '123456', '123456@qq.com', 1, 0, 0, '2023-07-31 22:04:45.032834', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (11, 1686019822887170048, 1677964231673425920, '1677964029214371840,1677964231673425920', '销售部', 0, 5, '1234567', '123456@qq.com', 1, 0, 0, '2023-07-31 22:23:45.167050', 0, '2025-10-18 15:29:30.000000', b'0', 5);
INSERT INTO `sys_dept` VALUES (12, 1686021322711560192, 1677964231673425920, '1677964029214371840,1677964231673425920', '财务部', 0, 40, '123456', '123456@qq.com', 1, 0, 0, '2023-07-31 22:29:42.752069', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (13, 1686021452114227200, 1677964231673425920, '1677964029214371840,1677964231673425920', '客户服务部', 0, 50, '123456', '123456@qq.com', 1, 0, 0, '2023-07-31 22:30:13.605403', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (14, 1686021621496999936, 1677964231673425920, '1677964029214371840,1677964231673425920', '运营部', 0, 60, '123456', '123456@qq.com', 1, 0, 0, '2023-07-31 22:30:53.989690', 0, '2025-10-18 15:29:30.000000', b'0', 4);
INSERT INTO `sys_dept` VALUES (15, 1686021776396840960, 1677964029214371840, '1677964029214371840', '河北分部', 0, 10, '123456', '123456@qq.com', 1, 0, 0, '2023-07-31 22:31:30.920687', 0, '2025-10-18 15:29:30.000000', b'0', 3);
INSERT INTO `sys_dept` VALUES (16, 1686349179535036416, 1686021776396840960, '1677964029214371840,1686021776396840960', '销售部', 0, 10, '123456', '123456@qq.com', 1, 0, 0, '2023-08-01 20:12:29.912108', 0, '2025-10-18 15:29:30.000000', b'0', 3);
INSERT INTO `sys_dept` VALUES (17, 1686349332929122304, 1686021776396840960, '1677964029214371840,1686021776396840960', '财务部', 0, 20, '123456', '123456@qq.com', 1, 0, 0, '2023-08-01 20:13:06.483608', 0, '2025-10-18 15:29:30.000000', b'0', 3);
INSERT INTO `sys_dept` VALUES (18, 1688102484086906880, 1686021776396840960, '1677964029214371840,1686021776396840960', '运营部', 0, 30, '13888888888', '13888888888@phone.com', 1, 0, 0, '2023-08-06 16:19:30.292609', 0, '2025-10-18 15:29:30.000000', b'0', 3);
INSERT INTO `sys_dept` VALUES (24, 1834773502369406976, 0, NULL, '东东胡辣汤', 1834763884364705792, 0, '11111', '11111', 1, 1834763883194494976, 1834763884364705792, '2024-09-14 09:57:46.111124', 1834763884364705792, '2024-09-14 10:01:30.581557', b'0', 1);
INSERT INTO `sys_dept` VALUES (25, 1834774513448329216, 1834773502369406976, '1834773502369406976', '东东胡辣汤一部', 1834763884364705792, 0, '111', '111', 1, 1834763883194494976, 1834763884364705792, '2024-09-14 10:01:48.475618', 1834763884364705792, '2024-09-14 10:01:48.475618', b'0', 0);
INSERT INTO `sys_dept` VALUES (26, 1834774974033240064, 1834773502369406976, '1834773502369406976', '东东胡辣汤二部', 1834763884364705792, 0, '222', '222', 1, 1834763883194494976, 1834763884364705792, '2024-09-14 10:03:37.111552', 1834763884364705792, '2024-09-14 10:03:37.111552', b'0', 0);
INSERT INTO `sys_dept` VALUES (27, 1836060155556687872, 0, NULL, '测试租户111', 1836055978990497792, 100, '1111111', '1111111', 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:10:28.128713', 1836055978990497792, '2024-09-17 23:10:28.128713', b'0', 0);
INSERT INTO `sys_dept` VALUES (28, 1836060210875363328, 1836060155556687872, '1836060155556687872', '一部', 1836059921829097472, 11, '11', '11', 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:10:41.436305', 1836055978990497792, '2024-09-17 23:10:41.436305', b'0', 0);
INSERT INTO `sys_dept` VALUES (29, 1836060257859956736, 1836060155556687872, '1836060155556687872', '二部', 1836060007078326272, 22, '22', '22', 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:10:52.640320', 1836055978990497792, '2024-09-17 23:10:52.640320', b'0', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `dict_id` bigint DEFAULT NULL COMMENT '字典ID',
                            `dict_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典类型',
                            `dict_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典key',
                            `dict_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典value',
                            `dict_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典名称',
                            `dict_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典描述',
                            `dict_order` int DEFAULT NULL COMMENT '字典排序值',
                            `dict_class` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典样式，对应前端Tag组件的type',
                            `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                            `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                            `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                            `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                            `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                            `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                            `version` int DEFAULT '0' COMMENT '版本号',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (63, 1666983160652914688, 'yes-no', 'true', '是', '是/否', '公共字典，是/否', 1, 'arcoblue', 1, 0, '2024-10-09 14:45:18.000000', 0, '2024-10-10 14:47:09.609727', b'0', 0);
INSERT INTO `sys_dict` VALUES (64, 1666983160652914689, 'yes-no', 'false', '否', '是/否', '公共字典，是/否', 2, 'red', 1, 0, '2024-10-09 14:45:18.000000', 0, '2024-10-10 14:47:09.732989', b'0', 0);
INSERT INTO `sys_dict` VALUES (66, 1667413525115789313, 'perm-type', 'M', '菜单', '权限类型', '权限管理中的权限类型', 5, 'arcoblue', 1, 0, '2024-10-09 14:45:18.000000', 0, '2023-06-28 10:58:51.929748', b'0', 0);
INSERT INTO `sys_dict` VALUES (67, 1667413525115789314, 'perm-type', 'B', '按钮', '权限类型', '权限管理中的权限类型', 10, 'magenta', 1, 0, '2024-10-09 14:45:18.000000', 0, '2023-06-28 10:58:51.929748', b'0', 0);
INSERT INTO `sys_dict` VALUES (68, 1669515928599478272, 'dict-class', 'default', '默认', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 0, 'default', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (69, 1669515928603672576, 'dict-class', 'orange', '橙', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 10, 'orange', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (70, 1669515928603672577, 'dict-class', 'gold', '金', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 20, 'gold', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (71, 1669515928603672578, 'dict-class', 'lime', '绿黄', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 40, 'lime', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (72, 1669515928603672579, 'dict-class', 'green', '绿', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 50, 'green', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (73, 1669515928603672580, 'dict-class', 'cyan', '青', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 60, 'cyan', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (74, 1669515928603672581, 'dict-class', 'blue', '蓝', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 70, 'blue', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (75, 1669515928603672582, 'dict-class', 'arcoblue', '湖蓝', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 80, 'arcoblue', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (76, 1669515928603672583, 'dict-class', 'purple', '紫', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 90, 'purple', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (77, 1669515928603672584, 'dict-class', 'pinkpurple', '紫粉', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 100, 'pinkpurple', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (78, 1669515928603672585, 'dict-class', 'magenta', '洋红', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 110, 'magenta', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (79, 1669515928603672586, 'dict-class', 'gray', '灰', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 120, 'gray', 1, 0, '2023-06-16 09:23:10.142052', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (80, 1669518820391731200, 'dict-class', 'red', '红', '字典样式', '字典管理中的字典样式，对应Tag标签的样式', 30, 'red', 1, 0, '2024-10-09 14:45:18.000000', 0, '2023-06-17 11:25:04.092472', b'0', 0);
INSERT INTO `sys_dict` VALUES (98, 1677961643871744000, 'user-sex', '1', '男', '用户性别', '用户管理中的用户性别', 1, 'arcoblue', 1, 0, '2023-07-09 16:43:25.575566', 0, '2023-07-09 16:43:25.575566', b'0', 0);
INSERT INTO `sys_dict` VALUES (99, 1677961643871744001, 'user-sex', '2', '女', '用户性别', '用户管理中的用户性别', 2, 'orange', 1, 0, '2023-07-09 16:43:25.575566', 0, '2023-07-09 16:43:25.575566', b'0', 0);
INSERT INTO `sys_dict` VALUES (104, 1681499349985316864, 'notice-type', '1', '公告', '系统公告类型', '系统公告中的公告类型', 0, 'arcoblue', 1, 0, '2023-07-19 11:01:00.440762', 0, '2023-07-23 22:51:26.805770', b'0', 0);
INSERT INTO `sys_dict` VALUES (105, 1683127629625241600, 'notice-type', '2', '新闻', '系统公告类型', '系统公告中的公告类型', 5, 'orange', 1, 0, '2024-10-09 14:45:18.000000', 0, '2023-07-23 22:51:26.805770', b'0', 0);
INSERT INTO `sys_dict` VALUES (108, 1683308098322038784, 'file-source', '1', '公告封面', '文件来源', '标识文件的用途，配合\"文件来源-存储位置\"一起使用', 1, 'orange', 1, 0, '2024-10-14 10:48:15.649000', 0, '2024-10-15 10:21:45.183768', b'0', 0);
INSERT INTO `sys_dict` VALUES (109, 1683308098322038785, 'file-source', '2', '公告内容', '文件来源', '标识文件的用途，配合\"文件来源-存储位置\"一起使用', 2, 'lime', 1, 0, '2024-10-14 10:48:19.649000', 0, '2024-10-15 10:21:45.306306', b'0', 0);
INSERT INTO `sys_dict` VALUES (126, 1843908761895776256, 'common-number-status', '0', '禁用', '通用状态-数字', '0禁用 1正常', 1, 'red', 1, 0, '2024-10-09 14:45:18.000000', 0, '2024-10-10 16:59:41.651688', b'0', 0);
INSERT INTO `sys_dict` VALUES (127, 1843908761895776257, 'common-number-status', '1', '正常', '通用状态-数字', '0禁用 1正常', 2, 'green', 1, 0, '2024-10-11 14:45:26.000000', 0, '2024-10-10 16:59:41.770223', b'0', 0);
INSERT INTO `sys_dict` VALUES (128, 1843932948186218496, 'storage-type', 'local', '本地', '存储类型', '存储平台，如阿里云oss、腾讯云oss、七牛云oss等', 1, 'orange', 1, 0, '2024-10-09 14:45:18.000000', 0, '2025-02-18 20:29:33.000000', b'0', 4);
INSERT INTO `sys_dict` VALUES (129, 1843932948186218497, 'storage-type', 'minio', 'MinIO', '存储类型', '存储平台，如阿里云oss、腾讯云oss、七牛云oss等', 2, 'blue', 1, 0, '2024-10-09 14:45:18.000000', 0, '2025-02-18 20:29:33.000000', b'0', 4);
INSERT INTO `sys_dict` VALUES (130, 1845016876603019264, 'file-source-path', '1', 'notice/cover/', '文件来源-存储位置', '文件来源对应的存储位置，配合\"文件来源\"一起使用', 1, NULL, 1, 0, '2024-10-15 10:13:34.000000', 0, '2025-03-12 19:11:03.000000', b'0', 4);
INSERT INTO `sys_dict` VALUES (131, 1845016876603019265, 'file-source-path', '2', 'notice/content/', '文件来源-存储位置', '文件来源对应的存储位置，配合\"文件来源\"一起使用', 2, NULL, 1, 0, '2024-10-15 10:13:38.000000', 0, '2025-03-12 19:11:03.000000', b'0', 4);
INSERT INTO `sys_dict` VALUES (133, 1890393477224509440, 'tenant-data-isolation', 'column', '字段隔离', '租户数据隔离方式', '多租户隔离方式：字段隔离 或 数据库隔离', 1, 'green', 1, 0, '2025-02-14 21:31:41.000000', 0, '2025-02-14 21:31:41.000000', b'0', 0);
INSERT INTO `sys_dict` VALUES (134, 1890393477329367040, 'tenant-data-isolation', 'db', '数据库隔离', '租户数据隔离方式', '多租户隔离方式：字段隔离 或 数据库隔离', 2, 'arcoblue', 1, 0, '2025-02-14 21:31:41.000000', 0, '2025-02-14 21:31:41.000000', b'0', 0);
INSERT INTO `sys_dict` VALUES (136, 1891825581577605120, 'storage-type', 'qiniu', '七牛云', '存储类型', '存储平台，如阿里云oss、腾讯云oss、七牛云oss等', 3, 'gold', 1, 0, '2025-02-18 20:22:21.000000', 0, '2025-02-18 20:29:34.000000', b'0', 2);
INSERT INTO `sys_dict` VALUES (137, 1899098996038688768, 'file-type', 'image', '图片', '文件类型', '文件类型，与file表中file_type字段对应', 0, NULL, 1, 0, '2025-03-10 22:04:18.000000', 0, '2025-03-10 22:04:18.000000', b'0', 0);
INSERT INTO `sys_dict` VALUES (138, 1899098996160323584, 'file-type', 'video', '视频', '文件类型', '文件类型，与file表中file_type字段对应', 0, NULL, 1, 0, '2025-03-10 22:04:18.000000', 0, '2025-03-10 22:04:18.000000', b'0', 0);
INSERT INTO `sys_dict` VALUES (139, 1899780153331810304, 'file-source-path', '-1', 'common/', '文件来源-存储位置', '文件来源对应的存储位置，配合\"文件来源\"一起使用', 0, NULL, 1, 0, '2025-03-12 19:11:03.000000', 0, '2025-03-12 19:11:03.000000', b'0', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `file_id` bigint NOT NULL COMMENT '文件ID',
                            `file_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原文件名',
                            `new_file_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '现文件名',
                            `file_size` bigint NOT NULL DEFAULT '0' COMMENT '文件大小',
                            `file_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件类型',
                            `file_base_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件基础路径',
                            `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件相对路径',
                            `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件url',
                            `file_source` int DEFAULT NULL COMMENT '文件来源',
                            `file_th_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件缩略图url',
                            `storage_id` bigint DEFAULT NULL COMMENT '存储ID',
                            `file_th_filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件缩略图文件名',
                            `file_th_size` bigint DEFAULT NULL COMMENT '缩略图文件大小',
                            `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                            `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
                            `status` tinyint DEFAULT '0' COMMENT '状态  0未使用 1已使用，默认未使用，代码中控制修改为已使用，可以定期清理未使用的文件',
                            `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                            `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                            `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                            `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                            `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                            `version` int DEFAULT '0' COMMENT '版本号',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_file_id` (`file_id`) USING BTREE COMMENT '文件ID索引',
                            KEY `idx_file_url` (`file_url`) USING BTREE COMMENT '文件URL索引'
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文件表';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `notice_id` bigint NOT NULL COMMENT '公告ID',
                              `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
                              `notice_type` tinyint NOT NULL COMMENT '公告类型（1公告）',
                              `notice_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '公告内容',
                              `notice_pic_file_id` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图文件ID，多个使用 , 分割',
                              `notice_top` bit(1) DEFAULT b'0' COMMENT '是否置顶 0否 1是',
                              `notice_time_interval` datetime(6) DEFAULT NULL COMMENT '延时发布的时间',
                              `notice_sort` int DEFAULT '0' COMMENT '排序',
                              `notice_out_chain` bit(1) DEFAULT NULL COMMENT '是否外链 0否 1是',
                              `notice_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '外部跳转链接',
                              `publish_dept_id` bigint DEFAULT NULL COMMENT '发布部门',
                              `publish_author_id` bigint DEFAULT NULL COMMENT '发布人',
                              `publish_time` datetime(6) DEFAULT NULL COMMENT '发布时间',
                              `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                              `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                              `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                              `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                              `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                              `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                              `version` int DEFAULT '0' COMMENT '版本号',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
INSERT INTO `sys_notice` VALUES (16, 1849729827784355840, 'Pandar1.0.0版本发布', 1, '基于SpringBoot3+Vue3的前后端分离的单体多租户脚手架，具备一些常用的基础功能。', '', b'1', NULL, 1, b'0', NULL, 1677964029214371840, 0, '2024-10-25 16:28:51.919183', 1, 0, '2024-10-25 16:28:52.000000', 0, '2025-10-19 10:59:09.000000', b'0', 14);
COMMIT;

-- ----------------------------
-- Table structure for sys_perms
-- ----------------------------
DROP TABLE IF EXISTS `sys_perms`;
CREATE TABLE `sys_perms` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `perm_id` bigint NOT NULL COMMENT '权限ID',
                             `perm_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
                             `perm_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
                             `parent_perm_id` bigint DEFAULT '0' COMMENT '父权限ID',
                             `perm_sort` int DEFAULT '0' COMMENT '显示顺序',
                             `perm_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由地址',
                             `perm_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限图标 菜单或目录时可传图标',
                             `perm_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'M' COMMENT '权限类型  M菜单 B按钮',
                             `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
                             `external_link` bit(1) DEFAULT b'0' COMMENT '是否为外部链接  0否 1是',
                             `visible` bit(1) DEFAULT b'1' COMMENT '是否可见 0隐藏 1显示）',
                             `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                             `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                             `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                             `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                             `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                             `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                             `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                             `version` int DEFAULT '0' COMMENT '版本号',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of sys_perms
-- ----------------------------
BEGIN;
INSERT INTO `sys_perms` VALUES (6, 1669336412647133184, NULL, '系统管理', 0, 1, NULL, 'IconSettings', 'M', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:42:11.000000', b'0', 6);
INSERT INTO `sys_perms` VALUES (7, 1669338708936298496, NULL, '用户管理', 1669336412647133184, 1, '/sys/user/mgt', 'IconUser', 'M', '/sys/user/UserMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:45:31.000000', b'0', 8);
INSERT INTO `sys_perms` VALUES (8, 1669339147886989312, NULL, '菜单权限', 1979506374983032832, 2, '/sys/perm/mgt', 'IconNav', 'M', '/sys/perm/PermMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:46:20.000000', b'0', 14);
INSERT INTO `sys_perms` VALUES (9, 1669339375922909184, NULL, '字典管理', 1669336412647133184, 4, '/sys/dict/mgt', 'IconDice', 'M', '/sys/dict/DictMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:45:50.000000', b'0', 8);
INSERT INTO `sys_perms` VALUES (15, 1673167646340120576, NULL, '角色管理', 1979506374983032832, 1, '/sys/role/mgt', 'IconUserGroup', 'M', '/sys/role/RoleMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:46:13.000000', b'0', 12);
INSERT INTO `sys_perms` VALUES (16, 1673524365792530432, NULL, '部门管理', 1669336412647133184, 2, '/sys/dept/mgt', 'IconMindMapping', 'M', '/sys/dept/DeptMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:45:37.000000', b'0', 7);
INSERT INTO `sys_perms` VALUES (17, 1673524591861321728, NULL, '岗位管理', 1669336412647133184, 3, '/sys/post/mgt', 'IconBulb', 'M', '/sys/post/PostMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:45:44.000000', b'0', 7);
INSERT INTO `sys_perms` VALUES (19, 1673863803093557248, NULL, '开发文档', 0, 10, '', 'IconBookmark', 'M', NULL, b'1', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'1', 6);
INSERT INTO `sys_perms` VALUES (20, 1676499330464649216, NULL, '租户维护', 0, 3, NULL, 'IconCloud', 'M', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:42:22.000000', b'0', 10);
INSERT INTO `sys_perms` VALUES (21, 1676499559247155200, NULL, '套餐管理', 1676499330464649216, 1, '/sys/tenantPackage/mgt', 'IconCommon', 'M', '/sys/tenantPackage/TenantPackageMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:46:30.000000', b'0', 7);
INSERT INTO `sys_perms` VALUES (22, 1676501907755405312, NULL, '租户管理', 1676499330464649216, 2, '/sys/tenant/mgt', 'IconRelation', 'M', '/sys/tenant/TenantMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:46:35.000000', b'0', 6);
INSERT INTO `sys_perms` VALUES (28, 1680749851857862656, '', '接口文档', 1979740477967945728, 1, '/sys/swagger', 'IconBook', 'M', '/sys/system/Swagger.vue', b'0', b'1', '将swagger页面嵌入到系统', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:45:22.000000', b'0', 6);
INSERT INTO `sys_perms` VALUES (29, 1681273378598850560, NULL, '系统公告', 1669336412647133184, 5, '/sys/notice/mgt', 'IconSubscribe', 'M', '/sys/notice/NoticeMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:45:57.000000', b'0', 4);
INSERT INTO `sys_perms` VALUES (30, 1683285343149096960, NULL, '文件管理', 1979740073842561024, 2, '/sys/file/mgt', 'IconFile', 'M', '/sys/file/FileMgt.vue', b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:43:43.000000', b'0', 5);
INSERT INTO `sys_perms` VALUES (31, 1685985572129398784, 'sys:user:add', '添加用户', 1669338708936298496, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (32, 1685985875515990016, 'sys:user:del', '删除用户', 1669338708936298496, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (33, 1685985977349496832, 'sys:user:edit', '修改用户', 1669338708936298496, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (34, 1685986276453703680, 'sys:user:query', '查询用户', 1669338708936298496, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (35, 1685986566443687936, 'sys:role:add', '添加角色', 1673167646340120576, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (36, 1685986702834065408, 'sys:role:del', '删除角色', 1673167646340120576, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (37, 1685986806387236864, 'sys:role:edit', '修改角色', 1673167646340120576, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (38, 1685986887102423040, 'sys:role:query', '查询角色', 1673167646340120576, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (39, 1685987612687654912, 'sys:perm:add', '添加权限', 1669339147886989312, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (40, 1685987708229705728, 'sys:perm:del', '删除权限', 1669339147886989312, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (41, 1685988419789185024, 'sys:perm:edit', '修改权限', 1669339147886989312, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 1);
INSERT INTO `sys_perms` VALUES (42, 1685988623951126528, 'sys:perm:query', '查询权限', 1669339147886989312, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 1);
INSERT INTO `sys_perms` VALUES (43, 1685996318275985408, 'sys:dept:add', '添加部门', 1673524365792530432, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (44, 1685996426530971648, 'sys:dept:del', '删除部门', 1673524365792530432, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (45, 1685996547448561664, 'sys:dept:edit', '修改部门', 1673524365792530432, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (46, 1685996623386435584, 'sys:dept:query', '查询部门', 1673524365792530432, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (47, 1685996875623489536, 'sys:post:add', '添加岗位', 1673524591861321728, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (48, 1685997536914235392, 'sys:post:del', '删除岗位', 1673524591861321728, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (49, 1685997640341577728, 'sys:post:edit', '修改岗位', 1673524591861321728, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (50, 1685997732956004352, 'sys:post:query', '查询岗位', 1673524591861321728, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (51, 1685998041879076864, 'sys:dict:add', '添加字典', 1669339375922909184, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (52, 1685998171407572992, 'sys:dict:del', '删除字典', 1669339375922909184, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (53, 1685998244715618304, 'sys:dict:edit', '修改字典', 1669339375922909184, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (54, 1685998340425441280, 'sys:dict:query', '查询字典', 1669339375922909184, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (55, 1686001693859569664, 'sys:notice:add', '添加公告', 1681273378598850560, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (56, 1686001770980237312, 'sys:notice:del', '删除公告', 1681273378598850560, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (57, 1686002193703165952, 'sys:notice:edit', '修改公告', 1681273378598850560, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (58, 1686002291338174464, 'sys:notice:query', '查询公告', 1681273378598850560, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (59, 1686002657173757952, 'sys:tenantPackage:add', '添加套餐', 1676499559247155200, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (60, 1686002750660599808, 'sys:tenantPackage:del', '删除套餐', 1676499559247155200, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (61, 1686002863185387520, 'sys:tenantPackage:edit', '修改套餐', 1676499559247155200, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (62, 1686002975089418240, 'sys:tenantPackage:query', '查询套餐', 1676499559247155200, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (63, 1686004858881368064, 'sys:tenant:add', '添加租户', 1676501907755405312, 10, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (64, 1686005292303966208, 'sys:tenant:del', '删除租户', 1676501907755405312, 20, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (65, 1686005483111243776, 'sys:tenant:edit', '修改租户', 1676501907755405312, 30, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (66, 1686005567790047232, 'sys:tenant:query', '查询租户', 1676501907755405312, 40, NULL, NULL, 'B', NULL, b'0', b'1', '', 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (73, 1833687152849211392, '', '参数配置', 1669336412647133184, 6, '/sys/config/mgt', 'IconSettings', 'M', '/sys/config/ConfigMgt.vue', b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:46:04.000000', b'0', 7);
INSERT INTO `sys_perms` VALUES (74, 1833688664656728064, 'sys:config:add', '添加参数', 1833687152849211392, 10, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (75, 1833688838682595328, 'sys:config:del', '删除参数', 1833687152849211392, 20, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (76, 1833688910472302592, 'sys:config:edit', '修改参数', 1833687152849211392, 30, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (77, 1833688972682219520, 'sys:config:query', '查询参数', 1833687152849211392, 40, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-18 15:36:43.000000', b'0', 2);
INSERT INTO `sys_perms` VALUES (78, 1843898617245937664, NULL, '存储管理', 1979740073842561024, 1, '/sys/storage/mgt', 'IconStorage', 'M', '/sys/storage/StorageMgt.vue', b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-19 10:43:58.000000', b'0', 4);
INSERT INTO `sys_perms` VALUES (79, 1843900739123359744, 'sys:storage:add', '添加存储', 1843898617245937664, 10, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (80, 1843900820899704832, 'sys:storage:del', '删除存储', 1843898617245937664, 20, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (81, 1843900873001349120, 'sys:storage:edit', '修改存储', 1843898617245937664, 30, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (82, 1843900946787545088, 'sys:storage:query', '查询存储', 1843898617245937664, 40, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 1);
INSERT INTO `sys_perms` VALUES (84, 1846813160695615488, 'sys:file:upload', '文件上传', 1683285343149096960, 10, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (85, 1846813266043949056, 'sys:file:del', '删除文件', 1683285343149096960, 20, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (86, 1846813437867806720, 'sys:file:download', '下载文件', 1683285343149096960, 30, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (87, 1846813517064654848, 'sys:file:query', '查询文件', 1683285343149096960, 40, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (88, 1846819585257852928, 'sys:swagger', '查看接口', 1680749851857862656, 10, NULL, NULL, 'B', NULL, b'0', b'1', NULL, 1, 0, '2025-10-17 14:23:05.000000', 0, '2025-10-17 14:23:05.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (89, 1979506374983032832, NULL, '权限管理', 0, 2, NULL, 'IconSafe', 'M', NULL, b'0', b'1', NULL, 1, 0, '2025-10-18 19:14:31.000000', 0, '2025-10-19 10:42:16.000000', b'0', 4);
INSERT INTO `sys_perms` VALUES (90, 1979740073842561024, NULL, '文件存储', 0, 4, NULL, 'IconFile', 'M', NULL, b'0', b'1', NULL, 1, 0, '2025-10-19 10:43:10.000000', 0, '2025-10-19 10:43:10.000000', b'0', 0);
INSERT INTO `sys_perms` VALUES (91, 1979740477967945728, NULL, '开发管理', 0, 99, NULL, 'IconCode', 'M', NULL, b'0', b'1', NULL, 1, 0, '2025-10-19 10:44:46.000000', 0, '2025-10-19 10:47:42.000000', b'0', 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `post_id` bigint NOT NULL COMMENT '岗位ID',
                            `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
                            `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
                            `post_sort` int NOT NULL COMMENT '显示顺序',
                            `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                            `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                            `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
                            `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                            `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                            `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                            `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                            `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                            `version` int DEFAULT '0' COMMENT '版本号',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post` VALUES (1, 1676044813214400512, 'super-admin', '超级管理员', 0, '超级无敌管理员', 1, 0, 0, '2023-07-04 09:46:37.534734', 0, '2023-07-27 23:23:36.287549', b'0', 3);
INSERT INTO `sys_post` VALUES (3, 1676046492030717952, 'develop-post', '开发', 20, NULL, 1, 0, 0, '2023-07-04 09:53:17.795741', 0, '2023-07-12 13:55:35.942153', b'0', 3);
INSERT INTO `sys_post` VALUES (4, 1679006310662467584, 'sell-post', '销售', 30, NULL, 1, 0, 0, '2023-07-12 13:54:33.547206', 0, '2023-07-12 13:55:38.805938', b'0', 1);
INSERT INTO `sys_post` VALUES (5, 1679006401066496000, 'finance-post', '财务', 40, NULL, 1, 0, 0, '2023-07-12 13:54:55.102228', 0, '2023-07-12 13:54:55.102228', b'0', 0);
INSERT INTO `sys_post` VALUES (6, 1685180496364257280, 'tester-post', '测试岗', 100, NULL, 1, 0, 0, '2023-07-29 14:48:37.544778', 0, '2023-07-31 22:32:33.193652', b'0', 1);
INSERT INTO `sys_post` VALUES (7, 1685181450862055424, 'hr-post', '人力资源', 50, NULL, 1, 0, 0, '2023-07-29 14:52:21.704777', 0, '2023-07-31 22:32:40.815096', b'0', 2);
INSERT INTO `sys_post` VALUES (8, 1836060404593487872, 'xiaoshou', '销售1', 1, NULL, 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:11:27.505296', 1836055978990497792, '2024-09-17 23:18:33.795853', b'0', 1);
INSERT INTO `sys_post` VALUES (9, 1836060433332858880, 'caiwu', '财务2', 2, NULL, 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:11:34.356145', 1836055978990497792, '2024-09-17 23:18:37.233269', b'0', 1);
INSERT INTO `sys_post` VALUES (10, 1836060483333156864, 'dongshizhang', '董事长', 0, NULL, 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:11:46.279929', 1836055978990497792, '2024-09-17 23:11:46.279929', b'0', 0);
INSERT INTO `sys_post` VALUES (11, 1863857681786707968, 'security-test-post', '安全测试', 60, NULL, 1, 0, 0, '2024-12-03 16:07:55.000000', 0, '2024-12-03 16:07:55.000000', b'0', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `role_id` bigint NOT NULL COMMENT '角色ID',
                            `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
                            `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
                            `role_sort` int DEFAULT '0' COMMENT '显示顺序',
                            `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                            `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                            `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
                            `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                            `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                            `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                            `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                            `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                            `version` int DEFAULT '0' COMMENT '版本号',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (101, 1671337763855073280, '系统管理员', 'systesys_admin', 0, 1, NULL, 0, 0, '2023-06-21 10:02:29.514290', 0, '2025-10-19 10:45:02.000000', b'0', 49);
INSERT INTO `sys_role` VALUES (104, 1671343260918325248, '销售人员', 'sell', 10, 1, NULL, 0, 0, '2023-06-21 10:24:20.116648', 0, '2024-10-29 16:41:07.000000', b'0', 21);
INSERT INTO `sys_role` VALUES (105, 1671389807492136960, '财务人员', 'financial', 20, 1, NULL, 0, 0, '2023-06-21 13:29:17.684715', 0, '2023-08-06 12:52:19.880964', b'0', 7);
INSERT INTO `sys_role` VALUES (106, 1677966412497596416, '开发人员', 'developer', 30, 1, NULL, 0, 0, '2023-07-09 17:02:22.504978', 0, '2023-08-06 12:52:25.197098', b'0', 1);
INSERT INTO `sys_role` VALUES (107, 1677966506420645888, '测试人员', 'tester', 40, 1, NULL, 0, 0, '2023-07-09 17:02:44.898094', 0, '2023-08-06 12:52:30.282735', b'0', 1);
INSERT INTO `sys_role` VALUES (108, 1677966630567849984, '运维人员', 'devopser', 50, 1, NULL, 0, 0, '2023-07-09 17:03:14.496421', 0, '2023-08-06 12:52:34.732570', b'0', 1);
INSERT INTO `sys_role` VALUES (109, 1834763883194494977, '管理员', 'admin', 0, 1, '添加租户系统自动创建角色', 1834763883194494976, 0, '2024-09-14 09:19:32.826724', 0, '2024-09-14 09:19:32.826724', b'0', 0);
INSERT INTO `sys_role` VALUES (110, 1834790150900002816, '普通用户', 'putongyonghu', 1, 1, NULL, 1834763883194494976, 1834763884364705792, '2024-09-14 11:03:55.423220', 0, '2024-10-04 21:44:55.734261', b'0', 2);
INSERT INTO `sys_role` VALUES (111, 1836055976973037568, '管理员', 'admin', 0, 1, '添加租户系统自动创建角色', 1836055976968843264, 0, '2024-09-17 22:53:52.221775', 1836055978990497792, '2024-09-17 23:29:36.280452', b'0', 1);
INSERT INTO `sys_role` VALUES (112, 1836056110565814273, '管理员', 'admin', 0, 1, '添加租户系统自动创建角色', 1836056110565814272, 0, '2024-09-17 22:54:23.727486', 0, '2024-09-17 22:54:23.727486', b'0', 0);
INSERT INTO `sys_role` VALUES (113, 1836057975659556864, '测试租户1普通', 'ceshi1-putong', 1, 1, NULL, 1836055976968843264, 1836055978990497792, '2024-09-17 23:01:48.394988', 1836055978990497792, '2024-09-17 23:01:48.394988', b'0', 0);
INSERT INTO `sys_role` VALUES (114, 1846431773459197953, '管理员', 'admin', 0, 1, '添加租户系统自动创建角色', 1846431773459197952, 0, '2024-10-16 14:03:34.527236', 0, '2024-10-16 14:03:34.527236', b'0', 0);
INSERT INTO `sys_role` VALUES (115, 1890404237937938432, '管理员', 'admin', 0, 1, '系统自动创建角色', 1890404237296209920, 0, '2025-02-14 22:14:26.000000', 0, '2025-02-14 22:14:26.000000', b'0', 0);
INSERT INTO `sys_role` VALUES (116, 1890405820998291456, '管理员', 'admin', 0, 1, '系统自动创建角色', 1890405820364951552, 0, '2025-02-14 22:20:44.000000', 0, '2025-02-14 22:20:44.000000', b'0', 0);
INSERT INTO `sys_role` VALUES (117, 1890406866659250176, '管理员', 'admin', 0, 1, '系统自动创建角色', 1890406866181099520, 0, '2025-02-14 22:24:53.000000', 0, '2025-02-14 22:24:53.000000', b'0', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                 `dept_id` bigint NOT NULL COMMENT '部门ID',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `dept_id_idx` (`dept_id`) USING BTREE COMMENT '部门ID索引',
                                 KEY `role_id_idx` (`role_id`) USING BTREE COMMENT '角色ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色与部门关联表 1角色-N部门';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm`;
CREATE TABLE `sys_role_perm` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                 `perm_id` bigint NOT NULL COMMENT '权限ID',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `persys_id_idx` (`perm_id`) USING BTREE COMMENT '权限ID索引',
                                 KEY `role_id_idx` (`role_id`) USING BTREE COMMENT '角色ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=2296 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色与权限关联表 1角色-N权限';

-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_perm` VALUES (747, 1671389807492136960, 1673863803093557248);
INSERT INTO `sys_role_perm` VALUES (748, 1677966412497596416, 1673863803093557248);
INSERT INTO `sys_role_perm` VALUES (749, 1677966506420645888, 1673863803093557248);
INSERT INTO `sys_role_perm` VALUES (750, 1677966630567849984, 1673863803093557248);
INSERT INTO `sys_role_perm` VALUES (880, 1836057975659556864, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (881, 1836057975659556864, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (882, 1836057975659556864, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (883, 1836057975659556864, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (884, 1836057975659556864, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (885, 1836057975659556864, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (1110, 1834790150900002816, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (1111, 1834790150900002816, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (1112, 1834790150900002816, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (1113, 1834790150900002816, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (1114, 1834790150900002816, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (1115, 1834790150900002816, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (1118, 1834790150900002816, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (1119, 1834790150900002816, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (1120, 1834790150900002816, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (1121, 1834790150900002816, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (1122, 1834790150900002816, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (1945, 1671343260918325248, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (1946, 1671343260918325248, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (1947, 1671343260918325248, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (1948, 1671343260918325248, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (1949, 1671343260918325248, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (1950, 1671343260918325248, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (1951, 1834763883194494977, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (1952, 1834763883194494977, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (1953, 1834763883194494977, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (1954, 1834763883194494977, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (1955, 1834763883194494977, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (1956, 1834763883194494977, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (1957, 1834763883194494977, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (1958, 1834763883194494977, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (1959, 1834763883194494977, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (1960, 1834763883194494977, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (1961, 1834763883194494977, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (1962, 1834763883194494977, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (1963, 1834763883194494977, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (1964, 1834763883194494977, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (1965, 1834763883194494977, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (1966, 1834763883194494977, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (1967, 1834763883194494977, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (1968, 1834763883194494977, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (1969, 1834763883194494977, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (1970, 1834763883194494977, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (1971, 1834763883194494977, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (1972, 1836055976973037568, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (1973, 1836055976973037568, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (1974, 1836055976973037568, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (1975, 1836055976973037568, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (1976, 1836055976973037568, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (1977, 1836055976973037568, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (1978, 1836055976973037568, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (1979, 1836055976973037568, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (1980, 1836055976973037568, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (1981, 1836055976973037568, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (1982, 1836055976973037568, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (1983, 1836055976973037568, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (1984, 1836055976973037568, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (1985, 1836055976973037568, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (1986, 1836055976973037568, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (1987, 1836055976973037568, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (1988, 1836055976973037568, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (1989, 1836055976973037568, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (1990, 1836055976973037568, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (1991, 1836055976973037568, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (1992, 1836055976973037568, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (1993, 1836056110565814273, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (1994, 1836056110565814273, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (1995, 1836056110565814273, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (1996, 1836056110565814273, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (1997, 1836056110565814273, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (1998, 1836056110565814273, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (1999, 1836056110565814273, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (2000, 1836056110565814273, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (2001, 1836056110565814273, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (2002, 1836056110565814273, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (2003, 1836056110565814273, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (2004, 1836056110565814273, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (2005, 1836056110565814273, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (2006, 1836056110565814273, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (2007, 1836056110565814273, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (2008, 1836056110565814273, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (2009, 1836056110565814273, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (2010, 1836056110565814273, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (2011, 1836056110565814273, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (2012, 1836056110565814273, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (2013, 1836056110565814273, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (2014, 1846431773459197953, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (2015, 1846431773459197953, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (2016, 1846431773459197953, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (2017, 1846431773459197953, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (2018, 1846431773459197953, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (2019, 1846431773459197953, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (2020, 1846431773459197953, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (2021, 1846431773459197953, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (2022, 1846431773459197953, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (2023, 1846431773459197953, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (2024, 1846431773459197953, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (2025, 1846431773459197953, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (2026, 1846431773459197953, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (2027, 1846431773459197953, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (2028, 1846431773459197953, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (2029, 1846431773459197953, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (2030, 1846431773459197953, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (2031, 1846431773459197953, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (2032, 1846431773459197953, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (2033, 1846431773459197953, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (2034, 1846431773459197953, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (2035, 1890404237937938432, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (2036, 1890404237937938432, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (2037, 1890404237937938432, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (2038, 1890404237937938432, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (2039, 1890404237937938432, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (2040, 1890404237937938432, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (2041, 1890404237937938432, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (2042, 1890404237937938432, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (2043, 1890404237937938432, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (2044, 1890404237937938432, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (2045, 1890404237937938432, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (2046, 1890404237937938432, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (2047, 1890404237937938432, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (2048, 1890404237937938432, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (2049, 1890404237937938432, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (2050, 1890404237937938432, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (2051, 1890404237937938432, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (2052, 1890404237937938432, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (2053, 1890404237937938432, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (2054, 1890404237937938432, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (2055, 1890404237937938432, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (2056, 1890405820998291456, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (2057, 1890405820998291456, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (2058, 1890405820998291456, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (2059, 1890405820998291456, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (2060, 1890405820998291456, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (2061, 1890405820998291456, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (2062, 1890405820998291456, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (2063, 1890405820998291456, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (2064, 1890405820998291456, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (2065, 1890405820998291456, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (2066, 1890405820998291456, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (2067, 1890405820998291456, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (2068, 1890405820998291456, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (2069, 1890405820998291456, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (2070, 1890405820998291456, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (2071, 1890405820998291456, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (2072, 1890405820998291456, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (2073, 1890405820998291456, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (2074, 1890405820998291456, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (2075, 1890405820998291456, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (2076, 1890405820998291456, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (2077, 1890406866659250176, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (2078, 1890406866659250176, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (2079, 1890406866659250176, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (2080, 1890406866659250176, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (2081, 1890406866659250176, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (2082, 1890406866659250176, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (2083, 1890406866659250176, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (2084, 1890406866659250176, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (2085, 1890406866659250176, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (2086, 1890406866659250176, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (2087, 1890406866659250176, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (2088, 1890406866659250176, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (2089, 1890406866659250176, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (2090, 1890406866659250176, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (2091, 1890406866659250176, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (2092, 1890406866659250176, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (2093, 1890406866659250176, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (2094, 1890406866659250176, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (2095, 1890406866659250176, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (2096, 1890406866659250176, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (2097, 1890406866659250176, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (2229, 1671337763855073280, 1685985572129398784);
INSERT INTO `sys_role_perm` VALUES (2230, 1671337763855073280, 1685985875515990016);
INSERT INTO `sys_role_perm` VALUES (2231, 1671337763855073280, 1685985977349496832);
INSERT INTO `sys_role_perm` VALUES (2232, 1671337763855073280, 1685986276453703680);
INSERT INTO `sys_role_perm` VALUES (2233, 1671337763855073280, 1669338708936298496);
INSERT INTO `sys_role_perm` VALUES (2234, 1671337763855073280, 1685986566443687936);
INSERT INTO `sys_role_perm` VALUES (2235, 1671337763855073280, 1685986702834065408);
INSERT INTO `sys_role_perm` VALUES (2236, 1671337763855073280, 1685986806387236864);
INSERT INTO `sys_role_perm` VALUES (2237, 1671337763855073280, 1685986887102423040);
INSERT INTO `sys_role_perm` VALUES (2238, 1671337763855073280, 1673167646340120576);
INSERT INTO `sys_role_perm` VALUES (2239, 1671337763855073280, 1685987612687654912);
INSERT INTO `sys_role_perm` VALUES (2240, 1671337763855073280, 1685987708229705728);
INSERT INTO `sys_role_perm` VALUES (2241, 1671337763855073280, 1685988419789185024);
INSERT INTO `sys_role_perm` VALUES (2242, 1671337763855073280, 1685988623951126528);
INSERT INTO `sys_role_perm` VALUES (2243, 1671337763855073280, 1669339147886989312);
INSERT INTO `sys_role_perm` VALUES (2244, 1671337763855073280, 1979506374983032832);
INSERT INTO `sys_role_perm` VALUES (2245, 1671337763855073280, 1685996318275985408);
INSERT INTO `sys_role_perm` VALUES (2246, 1671337763855073280, 1685996426530971648);
INSERT INTO `sys_role_perm` VALUES (2247, 1671337763855073280, 1685996547448561664);
INSERT INTO `sys_role_perm` VALUES (2248, 1671337763855073280, 1685996623386435584);
INSERT INTO `sys_role_perm` VALUES (2249, 1671337763855073280, 1673524365792530432);
INSERT INTO `sys_role_perm` VALUES (2250, 1671337763855073280, 1685996875623489536);
INSERT INTO `sys_role_perm` VALUES (2251, 1671337763855073280, 1685997536914235392);
INSERT INTO `sys_role_perm` VALUES (2252, 1671337763855073280, 1685997640341577728);
INSERT INTO `sys_role_perm` VALUES (2253, 1671337763855073280, 1685997732956004352);
INSERT INTO `sys_role_perm` VALUES (2254, 1671337763855073280, 1673524591861321728);
INSERT INTO `sys_role_perm` VALUES (2255, 1671337763855073280, 1685998041879076864);
INSERT INTO `sys_role_perm` VALUES (2256, 1671337763855073280, 1685998171407572992);
INSERT INTO `sys_role_perm` VALUES (2257, 1671337763855073280, 1685998244715618304);
INSERT INTO `sys_role_perm` VALUES (2258, 1671337763855073280, 1685998340425441280);
INSERT INTO `sys_role_perm` VALUES (2259, 1671337763855073280, 1669339375922909184);
INSERT INTO `sys_role_perm` VALUES (2260, 1671337763855073280, 1846813160695615488);
INSERT INTO `sys_role_perm` VALUES (2261, 1671337763855073280, 1846813266043949056);
INSERT INTO `sys_role_perm` VALUES (2262, 1671337763855073280, 1846813437867806720);
INSERT INTO `sys_role_perm` VALUES (2263, 1671337763855073280, 1846813517064654848);
INSERT INTO `sys_role_perm` VALUES (2264, 1671337763855073280, 1683285343149096960);
INSERT INTO `sys_role_perm` VALUES (2265, 1671337763855073280, 1843900739123359744);
INSERT INTO `sys_role_perm` VALUES (2266, 1671337763855073280, 1843900820899704832);
INSERT INTO `sys_role_perm` VALUES (2267, 1671337763855073280, 1843900873001349120);
INSERT INTO `sys_role_perm` VALUES (2268, 1671337763855073280, 1843900946787545088);
INSERT INTO `sys_role_perm` VALUES (2269, 1671337763855073280, 1843898617245937664);
INSERT INTO `sys_role_perm` VALUES (2270, 1671337763855073280, 1979740073842561024);
INSERT INTO `sys_role_perm` VALUES (2271, 1671337763855073280, 1686002657173757952);
INSERT INTO `sys_role_perm` VALUES (2272, 1671337763855073280, 1686002750660599808);
INSERT INTO `sys_role_perm` VALUES (2273, 1671337763855073280, 1686002863185387520);
INSERT INTO `sys_role_perm` VALUES (2274, 1671337763855073280, 1686002975089418240);
INSERT INTO `sys_role_perm` VALUES (2275, 1671337763855073280, 1676499559247155200);
INSERT INTO `sys_role_perm` VALUES (2276, 1671337763855073280, 1686004858881368064);
INSERT INTO `sys_role_perm` VALUES (2277, 1671337763855073280, 1686005292303966208);
INSERT INTO `sys_role_perm` VALUES (2278, 1671337763855073280, 1686005483111243776);
INSERT INTO `sys_role_perm` VALUES (2279, 1671337763855073280, 1686005567790047232);
INSERT INTO `sys_role_perm` VALUES (2280, 1671337763855073280, 1676501907755405312);
INSERT INTO `sys_role_perm` VALUES (2281, 1671337763855073280, 1676499330464649216);
INSERT INTO `sys_role_perm` VALUES (2282, 1671337763855073280, 1686001693859569664);
INSERT INTO `sys_role_perm` VALUES (2283, 1671337763855073280, 1686001770980237312);
INSERT INTO `sys_role_perm` VALUES (2284, 1671337763855073280, 1686002193703165952);
INSERT INTO `sys_role_perm` VALUES (2285, 1671337763855073280, 1686002291338174464);
INSERT INTO `sys_role_perm` VALUES (2286, 1671337763855073280, 1681273378598850560);
INSERT INTO `sys_role_perm` VALUES (2287, 1671337763855073280, 1833688664656728064);
INSERT INTO `sys_role_perm` VALUES (2288, 1671337763855073280, 1833688838682595328);
INSERT INTO `sys_role_perm` VALUES (2289, 1671337763855073280, 1833688910472302592);
INSERT INTO `sys_role_perm` VALUES (2290, 1671337763855073280, 1833688972682219520);
INSERT INTO `sys_role_perm` VALUES (2291, 1671337763855073280, 1833687152849211392);
INSERT INTO `sys_role_perm` VALUES (2292, 1671337763855073280, 1846819585257852928);
INSERT INTO `sys_role_perm` VALUES (2293, 1671337763855073280, 1680749851857862656);
INSERT INTO `sys_role_perm` VALUES (2294, 1671337763855073280, 1669336412647133184);
INSERT INTO `sys_role_perm` VALUES (2295, 1671337763855073280, 1979740477967945728);
COMMIT;

-- ----------------------------
-- Table structure for sys_storage
-- ----------------------------
DROP TABLE IF EXISTS `sys_storage`;
CREATE TABLE `sys_storage` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `storage_id` bigint NOT NULL,
                               `storage_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                               `storage_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储类型，用于标识存储平台，如本地、阿里云oss、七牛云oss等',
                               `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
                               `storage_config` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储配置，JSON数据',
                               `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                               `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                               `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                               `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                               `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                               `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                               `version` int DEFAULT '0' COMMENT '版本号',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='存储管理表';

-- ----------------------------
-- Records of sys_storage
-- ----------------------------
BEGIN;
INSERT INTO `sys_storage` VALUES (1, 1843939697907720192, '本地存储测试', 'local', '1', '{\"storagePath\":\"/Users/lida\"}', 1, 0, NULL, 0, '2025-10-18 15:32:26.000000', b'0', 4);
INSERT INTO `sys_storage` VALUES (2, 1844270972333924352, 'minio', 'minio', '2', '{\"accessKey\":\"5555\",\"secretKey\":\"5555\",\"endPoint\":\"5555\",\"bucketName\":\"5555\"}', 1, 0, NULL, 0, NULL, b'0', 0);
INSERT INTO `sys_storage` VALUES (4, 1891827317205766144, '七牛云2', 'qiniu', '3', '{\"accessKey\":\"2222\",\"secretKey\":\"2222\",\"endPoint\":\"2222\",\"bucketName\":\"2222\",\"regionId\":\"2222\"}', 1, 0, '2025-02-18 20:29:15.000000', 0, '2025-10-18 15:31:53.000000', b'0', 4);
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `tenant_id` bigint NOT NULL COMMENT '租户ID',
                              `user_id` bigint NOT NULL COMMENT '用户ID',
                              `package_id` bigint NOT NULL COMMENT '租户套餐ID',
                              `tenant_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名',
                              `expire_time` datetime(6) NOT NULL COMMENT '租户过期时间',
                              `account_count` int NOT NULL DEFAULT '0' COMMENT '可创建账号数量',
                              `data_isolation` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'column' COMMENT '数据隔离方式  column字段隔离(默认) db数据库隔离',
                              `datasource` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'master' COMMENT '数据源名称 master(默认使用主库) ',
                              `storage_id` bigint DEFAULT NULL COMMENT '存储ID 表示该租户使用哪个文件存储',
                              `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                              `status` tinyint DEFAULT '1' COMMENT '状态 0禁用 1正常',
                              `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
                              `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                              `update_id` bigint DEFAULT NULL COMMENT '更新人ID',
                              `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                              `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                              `version` int DEFAULT '0' COMMENT '版本号',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant` VALUES (1, 0, 0, 0, '系统租户', '2030-12-31 23:59:59.000000', 20, 'column', 'master', NULL, NULL, 1, 0, '2025-02-14 21:54:35.000000', 0, '2025-02-14 21:54:58.000000', b'0', 0);
INSERT INTO `sys_tenant` VALUES (3, 1834763883194494976, 1834763884364705792, 1676850220841287680, '东东胡辣汤', '2035-09-30 09:15:21.000000', 2, 'column', 'master', 1843939697907720192, NULL, 1, 0, '2025-02-14 21:54:38.000000', 0, '2025-02-18 21:56:23.000000', b'0', 2);
INSERT INTO `sys_tenant` VALUES (4, 1836055976968843264, 1836055978990497792, 1676850220841287680, '测试租户111', '2024-09-30 22:38:09.000000', 3, 'column', 'master', 1843939697907720192, NULL, 1, 0, '2025-02-14 21:54:41.000000', 0, '2025-02-18 21:56:32.000000', b'0', 2);
INSERT INTO `sys_tenant` VALUES (5, 1836056110565814272, 1836056110997827584, 1676850220841287680, '测试租户222', '2024-09-30 22:54:04.000000', 2, 'column', 'master', 1843939697907720192, NULL, 1, 0, '2025-02-14 21:54:43.000000', 0, '2025-02-18 21:56:39.000000', b'0', 2);
INSERT INTO `sys_tenant` VALUES (6, 1846431773459197952, 1846431773870239744, 1676850220841287680, '测试租户333', '2024-10-31 14:03:03.000000', 3, 'column', 'master', 1843939697907720192, NULL, 1, 0, '2025-02-14 21:54:46.000000', 0, '2025-02-18 21:56:42.000000', b'0', 2);
INSERT INTO `sys_tenant` VALUES (9, 1890406866181099520, 1890406866181099521, 1676850220841287680, '测试租户44', '2025-02-28 22:22:49.000000', 2, 'column', 'master', 1843939697907720192, NULL, 1, 0, '2025-02-14 22:24:53.000000', 0, '2025-02-19 11:01:00.000000', b'0', 6);
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_datasource
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_datasource`;
CREATE TABLE `sys_tenant_datasource` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `datasource_id` bigint NOT NULL COMMENT '数据源ID',
                                         `tenant_id` bigint NOT NULL COMMENT '租户ID',
                                         `datasource_name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据源名称',
                                         `datasource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据源连接',
                                         `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据源用户名',
                                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据源密码',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户数据源表';

-- ----------------------------
-- Records of sys_tenant_datasource
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_package`;
CREATE TABLE `sys_tenant_package` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `package_id` bigint NOT NULL COMMENT '套餐ID',
                                      `package_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '套餐名称',
                                      `status` tinyint DEFAULT '1' COMMENT '状态 0禁用 1正常',
                                      `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                                      `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
                                      `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                                      `update_id` bigint DEFAULT NULL COMMENT '更新人ID',
                                      `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                                      `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                                      `version` int DEFAULT '0' COMMENT '版本号',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE KEY `unq_package_id` (`package_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户套餐表';

-- ----------------------------
-- Records of sys_tenant_package
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant_package` VALUES (5, 1676850220841287680, '基础套餐', 1, '基础套餐', 0, '2023-07-06 15:07:01.676599', 0, '2024-12-03 15:54:37.000000', b'0', 21);
INSERT INTO `sys_tenant_package` VALUES (9, 1863859488525045760, 'CRM套餐', 1, NULL, 0, '2024-12-03 16:15:05.000000', 0, '2024-12-03 16:15:05.000000', b'1', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_package_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_package_perm`;
CREATE TABLE `sys_tenant_package_perm` (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `package_id` bigint NOT NULL COMMENT '租户套餐ID',
                                           `perm_id` bigint NOT NULL COMMENT '权限ID',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=679 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户套餐与权限关联表';

-- ----------------------------
-- Records of sys_tenant_package_perm
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant_package_perm` VALUES (609, 1676850220841287680, 1669336412647133184);
INSERT INTO `sys_tenant_package_perm` VALUES (610, 1676850220841287680, 1685986566443687936);
INSERT INTO `sys_tenant_package_perm` VALUES (611, 1676850220841287680, 1685986702834065408);
INSERT INTO `sys_tenant_package_perm` VALUES (612, 1676850220841287680, 1685986806387236864);
INSERT INTO `sys_tenant_package_perm` VALUES (613, 1676850220841287680, 1685986887102423040);
INSERT INTO `sys_tenant_package_perm` VALUES (614, 1676850220841287680, 1673167646340120576);
INSERT INTO `sys_tenant_package_perm` VALUES (615, 1676850220841287680, 1685985572129398784);
INSERT INTO `sys_tenant_package_perm` VALUES (616, 1676850220841287680, 1685985875515990016);
INSERT INTO `sys_tenant_package_perm` VALUES (617, 1676850220841287680, 1685985977349496832);
INSERT INTO `sys_tenant_package_perm` VALUES (618, 1676850220841287680, 1685986276453703680);
INSERT INTO `sys_tenant_package_perm` VALUES (619, 1676850220841287680, 1669338708936298496);
INSERT INTO `sys_tenant_package_perm` VALUES (620, 1676850220841287680, 1685996318275985408);
INSERT INTO `sys_tenant_package_perm` VALUES (621, 1676850220841287680, 1685996426530971648);
INSERT INTO `sys_tenant_package_perm` VALUES (622, 1676850220841287680, 1685996547448561664);
INSERT INTO `sys_tenant_package_perm` VALUES (623, 1676850220841287680, 1685996623386435584);
INSERT INTO `sys_tenant_package_perm` VALUES (624, 1676850220841287680, 1673524365792530432);
INSERT INTO `sys_tenant_package_perm` VALUES (625, 1676850220841287680, 1685996875623489536);
INSERT INTO `sys_tenant_package_perm` VALUES (626, 1676850220841287680, 1685997536914235392);
INSERT INTO `sys_tenant_package_perm` VALUES (627, 1676850220841287680, 1685997640341577728);
INSERT INTO `sys_tenant_package_perm` VALUES (628, 1676850220841287680, 1685997732956004352);
INSERT INTO `sys_tenant_package_perm` VALUES (629, 1676850220841287680, 1673524591861321728);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `user_id` bigint NOT NULL COMMENT '用户ID',
                            `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
                            `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
                            `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐值',
                            `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
                            `user_real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户真实姓名',
                            `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户邮箱',
                            `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
                            `user_sex` tinyint DEFAULT '1' COMMENT '用户性别  0未知 1男 2女',
                            `user_avatar` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '头像base64编码',
                            `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                            `status` tinyint DEFAULT '1' COMMENT '状态  0禁用 1正常',
                            `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
                            `create_id` bigint DEFAULT '0' COMMENT '创建人ID',
                            `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
                            `update_id` bigint DEFAULT '0' COMMENT '更新人ID',
                            `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
                            `deleted` bit(1) DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
                            `version` int DEFAULT '0' COMMENT '版本号',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `username_unq` (`username`) USING BTREE COMMENT '用户名唯一索引',
                            KEY `user_id_idx` (`user_id`) USING BTREE COMMENT '用户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 0, 'admin', 'c43fc233541ac2183e68159618167149', 'admin', '胖达', '小太阳', '438562332@qq.com', '15011384542', 1, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAA0JCgsKCA0LCgsODg0PEyAVExISEyccHhcgLikxMC4pLSwzOko+MzZGNywtQFdBRkxOUlNSMj5aYVpQYEpRUk//2wBDAQ4ODhMREyYVFSZPNS01T09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT0//wAARCAJAAkADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD06iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKCQoJYgAckntXL6n4/8ADmnXEcJuzcliAzWwEioD3Jzj8sn2oA6iimQyx3EEc0LrJFIodHU5DAjIIp5OBk0AFFY+peKdC0tmW81O3WROGjRt7j6quT3qp4f8a6R4g1CWysvtCSopZfNQKJADyVwT7dcfzoA6OiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoqG7vLWxhM17cw28Q43yuFH5muF8QfE+xtC0Giw/bJRx5r5WMH+bfp9aAO/d1RC7sFVRkknAFcT4g+JOladuh00f2hcDuhxGP+Bd/w/OvMda8T6zrhI1C9d4skiFPlQc56Drj3yax6ANvXfFes69lb67IgzkQRfJH+Xfp3zWTb+R9oT7UZBDn5/LA3Y9s8VFRQB3978UL1Ykt9G062s7eIbE35chRwABwBx25rlNS8R61qhP27UriVSMFA+1Mf7owP0rLooAK7X4Tpu8Xk8/JbOf1Uf1riq774PwFvEV5PxtjtSuO+WZcf+gmgD1+iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooqjqOsaZpeP7Qv7e3LchZJAGP0HU0AXqK4XUfijoltuWxhubxtuQwXy0J9Mnn9K5+7+LGouuLPTbWE56yO0n8ttAHrVFeG3PxF8TzvuS+SAf3Y4Ux+oJ/Ws268WeIbsjzdYvBjtHIYx/wCO4oA+hKrz31nbqWuLuCIKMkvIFwPxr50uNSv7pStzfXMyt1EkrMD37mqtAH0LL4r8PRfe1qxPOPlmDfyrMl+IvhdASt/JJ7LA/wDUCvDaKAPXLj4saUqE22nXsjY4Em1B+YJrn9R+Kes3G5bG2trRSMAkGRwfXJ4/SuDooAuanql/q1x9o1G7luJOxc8L9B0H4VToooAKKKKACiiigAooooAK9N+DUTb9WmIIXESA44J+Yn+n515lXr/wfjx4dvJP712V/JF/xoA76iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKRmVFLOwVVGSScACgBaxde8VaPoCEX10DPjIgj+aQ/h2+pxXE+MPiQ/mS2Hh4hVU7WvOufXYPT/AGvrjsa80kd5ZGkkdndyWZmOSSepJoA7LXviRrOpGSGwYWFsT8pj/wBaV927fhiuPnmluJmmuJXllc5Z3Ysx+pNR0UAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFe7fDm2a28E2AkXa0m+THsWOD+WK8Jr6R0ez/ALP0ayss5NvAkZOMZIUAnFAFyiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAZPNFbwSTzyLHFGpZ3Y4CgdSa8X8beN7jXZ2s9Pkkh01DjA+Vp/dvb0H5+1z4keLxqkzaNp5YWkEn758481x2/3QfzPPYVwNABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAbXg+y/tDxZpttngzh24zkL8xH5Ka+g6+dvDesS6DrltqEWSsbYkQH76Hhh+XT3Ar6Ft54rq3juIHDxSqHRh0IIyDQBJRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVxHxL8TnSNNGm2chW9vFOXRsGJMjJ9cnkD8fSuxvLqGxs5ru5bZDAhkdsZwAMmvnfXNWudb1ae/umYtKx2qTkRrnhR7CgChRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFWY7C9ltHu47Sd7aM4aVYyVX6mq1ABXsvwq1gX3h5tPfPm2DYycnKMSV/qMegFeNV1/ww1FrLxfDBn93eI0Lc8A43A49crj8TQB7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gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP//Z', '232332323', 1, 0, 0, '2023-05-09 22:25:26.000000', 0, '2025-10-19 10:59:49.000000', b'0', 26);
INSERT INTO `sys_user` VALUES (3, 1834763884364705792, 'dongdong', '5023047047573fd83145245f5201eeef', '8gb1qw', '东东', '东东', '11111111111', '11111111111', 1, NULL, NULL, 1, 1834763883194494976, 0, '2024-09-14 09:19:33.047847', 0, '2024-10-23 22:31:21.000000', b'0', 4);
INSERT INTO `sys_user` VALUES (4, 1834817021968625664, 'dongdong1', 'c0048f46ccbc7fd4079ef298e5e066db', 'uwgrap', '东东1号', '东东1号', '111', '111', 1, NULL, '1', 1, 1834763883194494976, 1834763884364705792, '2024-09-14 12:50:42.041340', 0, '2024-10-23 22:31:39.000000', b'0', 2);
INSERT INTO `sys_user` VALUES (5, 1836055978990497792, 'ceshi111', '41e43c75e3426adb407c05000f6f8501', 'et1t8b', '测试租户111', '测试租户111', '111', '111', 1, NULL, NULL, 1, 1836055976968843264, 0, '2024-09-17 22:53:52.392398', 1836055978990497792, '2024-09-17 23:17:41.221513', b'0', 1);
INSERT INTO `sys_user` VALUES (6, 1836056110997827584, 'ceshi222', 'c709119dd102a77fe92b972578ac226f', '5gxkja', '测试租户222', '测试租户222', '111', '111', 1, NULL, NULL, 1, 1836056110565814272, 0, '2024-09-17 22:54:23.866476', 0, '2024-09-17 22:54:23.866476', b'0', 0);
INSERT INTO `sys_user` VALUES (7, 1836059921829097472, 'ceshi111-1', '2dcc424ea713c122ae0dcf10ff90406f', 'sm74mg', 'ceshi111-1', '111', 'ceshi111-1', 'ceshi111-1', 1, NULL, NULL, 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:09:32.433005', 1836055978990497792, '2024-09-17 23:18:01.576329', b'0', 1);
INSERT INTO `sys_user` VALUES (8, 1836060007078326272, 'ceshi111-2', '8ffd827af99a14c8802e54ae1df6a5f2', '2l9538', 'ceshi111-2', 'ceshi111-2', 'ceshi111-2', 'ceshi111-2', 1, NULL, NULL, 1, 1836055976968843264, 1836055978990497792, '2024-09-17 23:09:52.749490', 1836055978990497792, '2024-09-17 23:18:09.068931', b'0', 1);
INSERT INTO `sys_user` VALUES (9, 1846431773870239744, 'test333', '32f7b6bf534219c83cfa97b2d3f53d53', 'qADvVl', '测试租户333', '测试租户333', NULL, '13888888888', 1, NULL, NULL, 1, 1846431773459197952, 0, '2024-10-16 14:03:34.624580', 0, '2024-10-16 14:03:34.624580', b'0', 0);
INSERT INTO `sys_user` VALUES (10, 1890404237300404224, 'test444', '3dc6fa0fbe21ce106f5b7bf078751558', 'B6pgBd', '测试租户444', '测试租户444', '123456@qq.com', '123456', 1, NULL, NULL, 1, 1890404237296209920, 0, '2025-02-14 22:14:26.000000', 0, '2025-02-14 22:14:26.000000', b'1', 0);
INSERT INTO `sys_user` VALUES (11, 1890405820369145856, 'test4', '6a9b52b45fbe14ec8fc34fb45f8c52df', 'mOUdML', '测试租户4', '4', '4', '4', 1, NULL, NULL, 1, 1890405820364951552, 0, '2025-02-14 22:20:43.000000', 0, '2025-02-14 22:20:43.000000', b'1', 0);
INSERT INTO `sys_user` VALUES (14, 1890406866181099521, 'test44', 'a8159b654d7cf51429c77eebca7b3afa', 'tqUSaV', '租户44', '租户44', '1', '1', 1, NULL, NULL, 1, 1890406866181099520, 0, '2025-02-14 22:24:53.000000', 0, '2025-02-14 22:24:53.000000', b'0', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `dept_id` bigint NOT NULL COMMENT '部门ID',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `idx_dept_id` (`dept_id`) USING BTREE COMMENT '部门ID索引',
                                 KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '用户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_dept` VALUES (19, 1679006018092986368, 1677964231673425920);
INSERT INTO `sys_user_dept` VALUES (20, 1679006018092986368, 1677964029214371840);
INSERT INTO `sys_user_dept` VALUES (21, 1679006018092986368, 1686021621496999936);
INSERT INTO `sys_user_dept` VALUES (22, 1679006018092986368, 1677964435873116160);
INSERT INTO `sys_user_dept` VALUES (32, 1836055978990497792, 1836060155556687872);
INSERT INTO `sys_user_dept` VALUES (33, 1836055978990497792, 1836060210875363328);
INSERT INTO `sys_user_dept` VALUES (34, 1836055978990497792, 1836060257859956736);
INSERT INTO `sys_user_dept` VALUES (35, 1836059921829097472, 1836060155556687872);
INSERT INTO `sys_user_dept` VALUES (36, 1836059921829097472, 1836060210875363328);
INSERT INTO `sys_user_dept` VALUES (37, 1836060007078326272, 1836060155556687872);
INSERT INTO `sys_user_dept` VALUES (38, 1836060007078326272, 1836060257859956736);
INSERT INTO `sys_user_dept` VALUES (60, 1834817021968625664, 1834774513448329216);
INSERT INTO `sys_user_dept` VALUES (61, 1834817021968625664, 1834773502369406976);
INSERT INTO `sys_user_dept` VALUES (62, 0, 1677964682431082496);
INSERT INTO `sys_user_dept` VALUES (63, 0, 1677964531410972672);
INSERT INTO `sys_user_dept` VALUES (64, 0, 1686349179535036416);
INSERT INTO `sys_user_dept` VALUES (65, 0, 1677964231673425920);
INSERT INTO `sys_user_dept` VALUES (66, 0, 1677964029214371840);
INSERT INTO `sys_user_dept` VALUES (67, 0, 1686019822887170048);
INSERT INTO `sys_user_dept` VALUES (68, 0, 1686349332929122304);
INSERT INTO `sys_user_dept` VALUES (69, 0, 1686021776396840960);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `post_id` bigint NOT NULL COMMENT '岗位ID',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `post_id_idx` (`post_id`) USING BTREE COMMENT '岗位ID索引',
                                 KEY `user_id_idx` (`user_id`) USING BTREE COMMENT '用户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表 1用户-N岗位';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_post` VALUES (14, 1679006018092986368, 1676046492030717952);
INSERT INTO `sys_user_post` VALUES (17, 1836055978990497792, 1836060483333156864);
INSERT INTO `sys_user_post` VALUES (18, 1836059921829097472, 1836060404593487872);
INSERT INTO `sys_user_post` VALUES (19, 1836060007078326272, 1836060433332858880);
INSERT INTO `sys_user_post` VALUES (24, 0, 1676046492030717952);
INSERT INTO `sys_user_post` VALUES (25, 0, 1676044813214400512);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `role_id_idx` (`role_id`) USING BTREE COMMENT '角色ID索引',
                                 KEY `user_id_idx` (`user_id`) USING BTREE COMMENT '用户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与角色关联表 1用户-N角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (9, 1679006018092986368, 1677966412497596416);
INSERT INTO `sys_user_role` VALUES (16, 1836056110997827584, 1836056110565814273);
INSERT INTO `sys_user_role` VALUES (19, 1836055978990497792, 1836055976973037568);
INSERT INTO `sys_user_role` VALUES (20, 1836059921829097472, 1836057975659556864);
INSERT INTO `sys_user_role` VALUES (21, 1836060007078326272, 1836057975659556864);
INSERT INTO `sys_user_role` VALUES (24, 1846431773870239744, 1846431773459197953);
INSERT INTO `sys_user_role` VALUES (25, 1834763884364705792, 1834763883194494977);
INSERT INTO `sys_user_role` VALUES (26, 1834817021968625664, 1834790150900002816);
INSERT INTO `sys_user_role` VALUES (27, 0, 1671337763855073280);
INSERT INTO `sys_user_role` VALUES (30, 1890406866181099521, 1890406866659250176);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
