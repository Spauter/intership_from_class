/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : res

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 11/11/2023 09:44:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
use res;
-- ----------------------------
-- Table structure for resadmin
-- ----------------------------
DROP TABLE IF EXISTS `resadmin`;
CREATE TABLE `resadmin`  (
  `raid` int NOT NULL AUTO_INCREMENT,
  `raname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rapwd` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`raid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resadmin
-- ----------------------------
INSERT INTO `resadmin` VALUES (1, 'a', '0cc175b9c0f1b6a831c399e269772661');

-- ----------------------------
-- Table structure for resfood
-- ----------------------------
DROP TABLE IF EXISTS `resfood`;
CREATE TABLE `resfood`  (
  `fid` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `normprice` decimal(8, 2) NULL DEFAULT NULL,
  `realprice` decimal(8, 2) NULL DEFAULT NULL,
  `detail` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fphoto` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resfood
-- ----------------------------
INSERT INTO `resfood` VALUES (1, '素炒莴笋丝', 22.00, 20.00, '营养丰富', '500008.jpg');
INSERT INTO `resfood` VALUES (2, '蛋炒饭', 22.00, 20.00, '营养丰富', '500022.jpg');
INSERT INTO `resfood` VALUES (3, '酸辣鱼', 42.00, 40.00, '营养丰富', '500023.jpg');
INSERT INTO `resfood` VALUES (4, '鲁粉', 12.00, 10.00, '营养丰富', '500024.jpg');
INSERT INTO `resfood` VALUES (5, '西红柿蛋汤', 12.00, 10.00, '营养丰富', '500025.jpg');
INSERT INTO `resfood` VALUES (6, '炖鸡', 102.00, 100.00, '营养丰富', '500026.jpg');
INSERT INTO `resfood` VALUES (7, '炒鸡', 12.00, 10.00, '营养丰富', '500033.jpg');
INSERT INTO `resfood` VALUES (8, '炒饭', 12.00, 10.00, '营养丰富', '500034.jpg');
INSERT INTO `resfood` VALUES (9, '手撕前女友', 12.00, 10.00, '营养丰富', '500035.jpg');
INSERT INTO `resfood` VALUES (10, '面条', 12.00, 10.00, '营养丰富', '500036.jpg');
INSERT INTO `resfood` VALUES (11, '端菜', 12.00, 10.00, '营养丰富', '500038.jpg');
INSERT INTO `resfood` VALUES (12, '酸豆角', 12.00, 10.00, '营养丰富', '500041.jpg');

-- ----------------------------
-- Table structure for resorder
-- ----------------------------
DROP TABLE IF EXISTS `resorder`;
CREATE TABLE `resorder`  (
  `roid` int NOT NULL AUTO_INCREMENT,
  `userid` int NULL DEFAULT NULL,
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tel` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ordertime` date NULL DEFAULT NULL,
  `deliverytime` date NULL DEFAULT NULL,
  `ps` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`roid`) USING BTREE,
  INDEX `fk_resorder`(`userid` ASC) USING BTREE,
  CONSTRAINT `fk_resorder` FOREIGN KEY (`userid`) REFERENCES `resuser` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resorder
-- ----------------------------

-- ----------------------------
-- Table structure for resorderitem
-- ----------------------------
DROP TABLE IF EXISTS `resorderitem`;
CREATE TABLE `resorderitem`  (
  `roiid` int NOT NULL AUTO_INCREMENT,
  `roid` int NULL DEFAULT NULL,
  `fid` int NULL DEFAULT NULL,
  `dealprice` decimal(8, 2) NULL DEFAULT NULL,
  `num` int NULL DEFAULT NULL,
  PRIMARY KEY (`roiid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resorderitem
-- ----------------------------
INSERT INTO `resorderitem` VALUES (1, NULL, 11, 10.00, 1);
INSERT INTO `resorderitem` VALUES (2, NULL, 12, 10.00, 1);
INSERT INTO `resorderitem` VALUES (3, NULL, 5, 10.00, 1);
INSERT INTO `resorderitem` VALUES (4, NULL, 3, 40.00, 1);
INSERT INTO `resorderitem` VALUES (5, NULL, 10, 10.00, 1);

-- ----------------------------
-- Table structure for resuser
-- ----------------------------
DROP TABLE IF EXISTS `resuser`;
CREATE TABLE `resuser`  (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resuser
-- ----------------------------
INSERT INTO `resuser` VALUES (1, 'a', '0cc175b9c0f1b6a831c399e269772661', 'a@163.com');
INSERT INTO `resuser` VALUES (2, 'b', '0cc175b9c0f1b6a831c399e269772661', 'b@163.com');

SET FOREIGN_KEY_CHECKS = 1;
