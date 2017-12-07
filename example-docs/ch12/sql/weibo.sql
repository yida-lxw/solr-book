/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50538
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2016-10-18 22:27:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `weibo`
-- ----------------------------
DROP TABLE IF EXISTS `weibo`;
CREATE TABLE `weibo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL COMMENT '微博标题',
  `thumbs_up` bigint(20) DEFAULT NULL COMMENT '点赞人数',
  `thumbs_down` bigint(20) DEFAULT NULL COMMENT '点踩人数',
  `total_votes` bigint(20) DEFAULT NULL COMMENT '总参与人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibo
-- ----------------------------
INSERT INTO `weibo` VALUES ('1', '【一代神机说再见！iPhone 4退出历史舞台】', '100000', '28', '100028');
INSERT INTO `weibo` VALUES ('2', '梁朝伟代言小米系列', '9999999', '3', '10000002');
INSERT INTO `weibo` VALUES ('3', 'iOS 10.0.3 更新推送，貌似只针对iPhone 7和iPhone 7 Plus', '666', '9', '675');
