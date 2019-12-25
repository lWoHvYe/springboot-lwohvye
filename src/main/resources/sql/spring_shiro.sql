/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL127
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : spring_shiro

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 25/12/2019 16:09:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (10);
INSERT INTO `hibernate_sequence` VALUES (10);
INSERT INTO `hibernate_sequence` VALUES (10);

-- ----------------------------
-- Table structure for mp_custom
-- ----------------------------
DROP TABLE IF EXISTS `mp_custom`;
CREATE TABLE `mp_custom`  (
  `custom_id` int(40) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `custom_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业名',
  `full_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `owner_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有人',
  `owner_tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有人电话',
  `status` int(11) NULL DEFAULT NULL COMMENT '企业状态',
  `note` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`custom_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mp_custom
-- ----------------------------
INSERT INTO `mp_custom` VALUES (1, '阿里', '阿里巴巴网络技术有限公司', '中国杭州', 'alibaba@126.com', '马云', '1851561561', 1, '阿里巴巴 网络技术有限公司（简称：阿里巴巴集团）是以曾担任英语教师的马云为首的18人，于1999年在杭州创立，他们相信互联网能够创造公平的竞争环境，让小企业通过创新与科技扩展业务，并在参与国内或全球市场竞争时处于更有利的位置。<br />\r\n阿里巴巴集团经营多项业务，另外也从关联公司的业务和服务中取得经营商业生态系统上的支援。业务和关联公司的业务包括：淘宝网、天猫、聚划算、全球速卖通、阿里巴巴国际交易市场、1688、阿里妈妈、阿里云、蚂蚁金服、菜鸟网络等。<br />\r\n2014年9月19日，阿里巴巴集团在纽约证券交易所正式挂牌上市，股票代码\"BABA\"，创始人和董事局主席为马云。<br />\r\n2015年全年，阿里巴巴总营收943.84亿元人民币，净利润688.44亿元人民币。<br />\r\n2016年4月6日，阿里巴巴正式宣布已经成为全球最大的零售交易平台。<br />\r\n2016年7月5日，第三方应用商店\\\"豌豆荚\\\"宣布，其应用分发业务并入阿里巴巴移动事业群，双方已正式签订并购协议。<br />\r\n2016年8月，阿里巴巴集团在\"2016中国企业500强\"中排名第148位。<br />');
INSERT INTO `mp_custom` VALUES (2, '网易', '杭州网易养猪公司', '杭州滨江区', 'wangyi@163.com', '丁磊', '32143214', 1, '网易 (NASDAQ: NTES)是中国领先的互联网公司，利用最先进的互联网技术，加强人与人之间信息的交流和共享，实现\"网聚人的力量\"。创始人兼CEO是丁磊。<br />\r\n在开发互联网应用、服务及其它技术方面，网易始终保持业界的领先地位，并在中国互联网行业内率先推出了包括中文全文检索、全中文大容量免费邮件系统、无限容量免费网络相册、免费电子贺卡站、网上虚拟社区、网上拍卖平台、24小时客户服务中心在内的业内领先产品或服务，还通过自主研发推出了一款率先取得白金地位的国产网络游戏。[1]&nbsp;<br />\r\n网易公司推出了门户网站、在线游戏、电子邮箱、在线教育、电子商务、在线音乐、网易bobo等多种服务。<br />');
INSERT INTO `mp_custom` VALUES (3, '京东', '北京京东世纪贸易有限公司', '中国北京市朝阳区北辰西路8号北辰世纪中心A座', 'jd@126.com', '章泽天', '1565156456', 1, '<span style=\\\"color:#333333;font-family:Helvetica, Arial, Tahoma, &quot;font-size:15px;line-height:22px;background-color:#F2F2F2;\\\">京东JD.COM-专业的综合网上购物商城，销售超数万品牌、4020万种商品，囊括家电、手机、电脑、母婴、服装等13大品类。秉承客户为先的理念，京东所售商品为<span style=\\\"color:#E53333;\\\">正品行货、全国联保、机打发票</span>。1</span><span></span>');
INSERT INTO `mp_custom` VALUES (4, '新浪', '新浪网络技术股份有限公司', '北京市北四环西路58号理想国际大厦20层', '15611561@163.com', '曹国伟', '15614145656', 1, '新浪（NASDAQ：SINA），是一家网络公司的名称，成立于1998年12月，服务大中华地区与海外华人，新浪拥有多家地区性网站，通过旗下五大业务主线为用户提供网络服务，网下的北京新浪、香港新浪、台北新浪、北美新浪等覆盖全球华人社区的全球最大中文门户网站，2012年11月新浪注册用户已突破4亿。<br />\r\n新浪公司是一家服务于中国及全球华人社群的网络媒体公司。新浪通过门户网站新浪网、移动门户手机新浪网和社交网络服务及微博客服务微博组成的数字媒体网络，帮助广大用户通过互联网和移动设备获得专业媒体和用户自生成的多媒体内容（UGC）并与友人进行兴趣分享。<br />');
INSERT INTO `mp_custom` VALUES (5, '美团', '北京三快在线科技有限公司', '北京市朝阳区望京东路6号 望京国际研发园三期', '156115115@126.com', '王兴', '1561511151', 2, '<div class=\\\"para\\\" style=\\\"font-size:14px;color:#333333;font-family:arial, 宋体, sans-serif;background-color:#FFFFFF;\\\">\r\n	新浪（NASDAQ：SINA），是一家网络公司的名称，成立于1998年12月，服务大中华地区与海外华人，新浪拥有多家地区性网站，通过旗下五大业务主线为用户提供网络服务，网下的北京新浪、香港新浪、台北新浪、北美新浪等覆盖全球华人社区的全球最大中文门户网站，2012年11月新浪注册用户已突破4亿。<br />\r\n新浪公司是一家服务于中国及全球华人社群的网络媒体公司。新浪通过门户网站新浪网、移动门户手机新浪网和社交网络服务及微博客服务微博组成的数字媒体网络，帮助广大用户通过互联网和移动设备获得专业媒体和用户自生成的多媒体内容（UGC）并与友人进行兴《美团网》是2010年3月4日成立的团购网站。美团网有着“美团一次，美一次”的宣传口号。为消费者发现最值得信赖的商家，让消费者享受超低折扣的优质服务；为商家找到最合适的消费者，给商家提供最大收益的互联网推广。<br />\r\n2014年美团全年交易额突破460亿元，较去年增长180%以上，市场份额占比超过60%，比2013年的53%增长了7个百分点。<br />\r\n2015年1月18日，美团网CEO王兴表示，美团已经完成7亿美元融资，美团估值达到70亿美元，最近两年不考虑上市。<br />\r\n2015年10月8日，大众点评与美团网宣布合并，美团CEO王兴和大众点评CEO张涛将会同时担任联席CEO和联席董事长。11月，阿里确认退出美团，阿里腾讯O2O正式开战。<br />\r\n2015年11月10日，美团CEO王兴发内部邮件表示，将不再担任联席董事长。[1]&nbsp;<br />\r\n2016年8月，北京市食药监局利用高科技手段对互联网违法行为进行搜索监测，为监管部门提供了一批违法线索，查处了一大批违法案件。8月10日，北京市食药监局对美团进行立案调查。<br />\r\n<br />\r\n</div>');
INSERT INTO `mp_custom` VALUES (6, '蒙牛', '内蒙古蒙牛乳业集团', '呼和浩特', '3213321@mengniu.com', '3132', '3213', 1, '一家放牧的公司');
INSERT INTO `mp_custom` VALUES (7, '百度', '北京百度有限公司', '北京海淀区中关村软件园二期百度科技园', '561455@baidu.com', '李彦宏', '1561561511', 1, '全球最大的中文搜索引擎');
INSERT INTO `mp_custom` VALUES (9, 'cusotomNmae', 'fuu', 'addressInof', '111@1234.COM', 'ownerName', '001', 1, 'nnnnnnn');
INSERT INTO `mp_custom` VALUES (12, 'adfad', '31234', NULL, '1412', 'gadfa', '123', 1, '42');
INSERT INTO `mp_custom` VALUES (13, 'adfad', '31234', NULL, '1412', 'gadfa', '123', 1, '42');
INSERT INTO `mp_custom` VALUES (16, '测试企业', NULL, '一个地址', '111', '法人', NULL, 1, NULL);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `parent_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_type` enum('menu','button') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, b'0', '用户列表', 0, '0/', 'user:view', 'menu', 'user/getUser');
INSERT INTO `permission` VALUES (2, b'0', '用户添加', 1, '0/1', 'user:add', 'button', 'user/addUser');
INSERT INTO `permission` VALUES (3, b'0', '用户删除', 1, '0/1', 'user:del', 'button', 'user/deleteUser');
INSERT INTO `permission` VALUES (4, b'0', '角色列表', 0, '0/', 'role:view', 'menu', 'role/getRole');
INSERT INTO `permission` VALUES (5, b'0', '权限列表', 0, '0/', 'permission:view', 'menu', 'permis/getPermis');
INSERT INTO `permission` VALUES (6, b'0', '客户列表', 2, '0/2', 'custom:view', 'menu', 'custom/list');
INSERT INTO `permission` VALUES (7, b'0', '客户添加', 2, '0/2', 'custom:add', 'button', 'custom/add');
INSERT INTO `permission` VALUES (8, b'0', '客户删除', 2, '0/2', 'custom:del', 'button', 'custom/del');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, b'0', '管理员', 'admin');
INSERT INTO `role` VALUES (2, b'0', 'VIP会员', 'vip');
INSERT INTO `role` VALUES (3, b'1', 'test', 'test');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  INDEX `FKf8yllw1ecvwqy3ehyxawqa1qp`(`permission_id`) USING BTREE,
  INDEX `FKa6jx8n8xkesmjmv6jqug6bg68`(`role_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 6);
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (2, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (2, 5);
INSERT INTO `role_permission` VALUES (1, 7);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` bigint(32) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx`(`username`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '系统管理人员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', 0, 'admin', 1);
INSERT INTO `user` VALUES (2, '测试人员', '7dd277260be4b1f18de12e671b308d1a', '484fafbb5ff54090aa498d0c9b7d33a4', 0, 'test', NULL);
INSERT INTO `user` VALUES (3, '测试人员2', NULL, NULL, 0, 'test2', NULL);
INSERT INTO `user` VALUES (4, '测试管理员', NULL, NULL, 0, 'testadmin', NULL);
INSERT INTO `user` VALUES (5, '测试用户', 'c00a6f499bcc996a4cbdc9818c3ad526', NULL, 0, 'testuser', NULL);
INSERT INTO `user` VALUES (8, 'ins', 'ert', NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `act_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `act_time` timestamp(4) NOT NULL DEFAULT CURRENT_TIMESTAMP(4) ON UPDATE CURRENT_TIMESTAMP(4),
  `ip_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_log
-- ----------------------------
INSERT INTO `user_log` VALUES (11, 'admin', '登陆系统', '2019-12-25 15:13:51.3297', '127.0.0.1');
INSERT INTO `user_log` VALUES (12, 'admin', '获取客户列表', '2019-12-25 15:17:18.5258', '127.0.0.1');
INSERT INTO `user_log` VALUES (13, 'admin', '登陆系统', '2019-12-25 15:27:32.8975', '127.0.0.1');
INSERT INTO `user_log` VALUES (14, 'admin', '登陆系统', '2019-12-25 15:37:52.4292', '127.0.0.1');
INSERT INTO `user_log` VALUES (15, 'admin', '获取客户列表', '2019-12-25 15:40:29.7472', '127.0.0.1');
INSERT INTO `user_log` VALUES (16, 'admin', '获取客户列表', '2019-12-25 15:41:03.4662', '127.0.0.1');
INSERT INTO `user_log` VALUES (17, 'admin', '登陆系统', '2019-12-25 16:00:28.8641', '127.0.0.1');
INSERT INTO `user_log` VALUES (18, 'admin', '添加客户信息', '2019-12-25 16:03:37.0037', '127.0.0.1');

SET FOREIGN_KEY_CHECKS = 1;
